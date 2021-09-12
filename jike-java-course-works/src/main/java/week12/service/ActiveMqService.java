import com.alibaba.fastjson.JSONObject;
import com.yb.activemq.factory.BeanFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActiveMqService {
    private final JmsTemplate jmsTemplate;

    /**
     * 发送队列消息
     */
    public void sendQueueMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "jack");
        jsonObject.put("age", 18);
        jmsTemplate.convertAndSend(BeanFactory.QUEUE_NAME, jsonObject);
    }

    /**
     * 发送主题消息
     */
    public void sendTopicMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "rose");
        jsonObject.put("age", 17);
        jmsTemplate.convertAndSend(BeanFactory.TOPIC_NAME, jsonObject);
    }

}

