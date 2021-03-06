import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class BeanFactory {
    public static final String TOPIC_NAME = "test.topic";
    public static final String QUEUE_NAME = "test.queue";

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(QUEUE_NAME);
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic(TOPIC_NAME);
    }

}
