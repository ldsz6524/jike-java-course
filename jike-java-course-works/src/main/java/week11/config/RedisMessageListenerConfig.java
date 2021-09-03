package week11.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.Charset;


@Configuration
public class RedisMessageListenerConfig {


    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //设置消息监听并设置监听主题
        container.addMessageListener((Message message, byte[] channel) -> {
            System.out.println("@=" + StrUtil.str(channel, Charset.defaultCharset()));
            System.out.println("@=" + StrUtil.str(message.getChannel(), Charset.defaultCharset()));

            final String jsonStr = StrUtil.str(message.getBody(), Charset.defaultCharset());
            final JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
            jsonObject.putOpt("payStatus", "已支付");

            System.err.println("@=" + jsonObject);
        }, PatternTopic.of("yb"));

        container.addMessageListener((message, channel) -> {
            final String jsonStr = StrUtil.str(message.getBody(), Charset.defaultCharset());
            final JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
            jsonObject.putOpt("payStatus", "已支付");
            System.err.println(jsonObject);
        }, ChannelTopic.of("order-channel"));
        return container;
    }

}

