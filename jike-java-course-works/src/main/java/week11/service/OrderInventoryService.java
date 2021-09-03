package week11.service;

import io.reactivex.rxjava3.functions.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Collections;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderInventoryService {
    private static final String INVENTORY_COUNT_KEY = "inventory:count:";
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;


    public String saveOrder(String orderId, String orderUserId) {
        final String redisLockKey = orderUserId.concat("@Lock@").concat(orderId);
        final String redisLockValue = orderId;
        //判断该用户是否已经提交过记录(这里取参数用户ID作为登录用户ID模拟)
        final Boolean aBoolean = stringRedisTemplate.opsForValue()
                .setIfAbsent(redisLockKey, redisLockValue, Duration.ofSeconds(5));
        if (aBoolean) {
            try {
                //保存订单信息到redis
                stringRedisTemplate.opsForValue().set("DB=".concat(orderId) + System.currentTimeMillis(), orderUserId, Duration.ofDays(1));
            } finally {
                //事务也可以处理类似任务
                //MULTI 开启事务
                //EXEC 执行任务队列里所有命令，并结束事务
                //DISCARD 放弃事务，清空任务队列，全部不执行，并UNWATCH
                //WATCH key [key1] MULTI执行之前，指定监控某key，如果key发生修改，放弃整个事务执行
                //UNWATCH 手动取消监控
                //使用redis事务来保证多个redis操作的原子性(lua脚本)
                //这里没有处理因业务时间大于超时时间时的续期问题/以及集群同步丢失锁等问题
                String luaScript = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
                        "then\n" +
                        "    return redis.call(\"del\",KEYS[1])\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end";
                final Boolean execute = stringRedisTemplate.execute(new DefaultRedisScript<>(luaScript, Boolean.class), Collections.singletonList(redisLockKey), redisLockValue);
                return execute ? "操作成功" : "操作失败";
            }
        } else {
            return "请不要频繁操作";
        }
    }


    public String saveOrderRedisson(String orderId, String orderUserId) {
        final String redisLockKey = orderUserId.concat("@Lock@").concat(orderId);
        final RLock lock = redissonClient.getLock(redisLockKey);
        lock.lock();
        try {
            //保存订单信息到redis
            stringRedisTemplate.opsForValue().set("DB=".concat(orderId), orderUserId, Duration.ofDays(1));
            return "操作成功";
        } catch (Exception e) {
            log.info("saveOrderRedisson方法异常={}", e.getMessage());
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return "请不要频繁操作";
    }


    public String deductionInventory(String productId, int buyProductCount) {
        final String redisLockKey = "@Lock@".concat(productId);
        final RLock lock = redissonClient.getLock(redisLockKey);
        lock.lock();
        try {
            final String result = stringRedisTemplate.opsForValue().get(INVENTORY_COUNT_KEY);
            final int inventoryCount = StringUtils.hasText(result) ? Integer.parseInt(result) : 0;
            if (inventoryCount >= buyProductCount) {
                final Long decrement = stringRedisTemplate.opsForValue().decrement(INVENTORY_COUNT_KEY, buyProductCount);
                return "成功购买商品,库存还剩:" + decrement + "件";
            }
        } catch (Exception e) {
            log.info("deductionInventory方法异常={}", e.getMessage());
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return "商品已售罄或服务忙";
    }

    public String setProductInventory(int addProductCount) {
        stringRedisTemplate.delete(INVENTORY_COUNT_KEY);
        final Long increment = stringRedisTemplate.opsForValue().increment(INVENTORY_COUNT_KEY, addProductCount);
        stringRedisTemplate.expire(INVENTORY_COUNT_KEY, Duration.ofDays(1));
        return "操作成功:" + increment;
    }

}
