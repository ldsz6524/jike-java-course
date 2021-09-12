import com.alibaba.fastjson.JSONObject;
import com.yb.activemq.factory.BeanFactory;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class MessageListener {

    @JmsListener(destination = BeanFactory.QUEUE_NAME)
    public void listenerQueueMessage1(JSONObject jsonObject) throws JMSException {
        System.err.println("1=queue=="+jsonObject);
    }

    @JmsListener(destination = BeanFactory.QUEUE_NAME)
    public void listenerQueueMessage2(JSONObject jsonObject) throws JMSException {
        System.err.println("2=queue=="+jsonObject);
    }

    @JmsListener(destination = BeanFactory.TOPIC_NAME)
    public void listenerTopicMessage1(JSONObject jsonObject) throws JMSException {
        System.out.println("1=topic=="+jsonObject);
    }

    @JmsListener(destination = BeanFactory.TOPIC_NAME)
    public void listenerTopicMessage2(JSONObject jsonObject) throws JMSException {
        System.out.println("2=topic=="+jsonObject);
    }

}
