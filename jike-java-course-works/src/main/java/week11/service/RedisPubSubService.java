package week11.service;

import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.lettuce.core.pubsub.RedisPubSubListener;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.DefaultMessage;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceSubscription;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPubSubService {
    private final StringRedisTemplate stringRedisTemplate;

    public String publishMessage(String channel) {
        for (int i = 0; i < 10; i++) {
            final JSONObject orderInfo = new JSONObject();
            orderInfo.putOpt("orderId", i);
            orderInfo.putOpt("payStatus", "未支付");
            System.out.println(orderInfo);
            stringRedisTemplate.convertAndSend(channel, JSONUtil.toJsonStr(orderInfo));
        }
        return "操作成功";
    }

}
