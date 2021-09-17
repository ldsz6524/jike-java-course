import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import java.util.Properties;


public class MyKafkaProducer<T> {
    private Properties properties;
    private KafkaProducer<String, String> producer;
    public static final String TOPIC = "My-Topic";

    public MyKafkaProducer() {
        properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9001,localhost:9002,localhost:9003");
//        properties.put("queue.enqueue.timeout.ms", -1);
//        properties.put("enable.idempotence", true);
//        properties.put("transactional.id", "transactional_1");
//        properties.put("acks", "all");
//        properties.put("retries", "3");
//        properties.put("max.in.flight.requests.per.connection", 1);
        //producer.initTransactions();
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(properties);
    }

    public void send(T t) {
        try {
            ProducerRecord record = new ProducerRecord(TOPIC, JSON.toJSONString(t));
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    producer.abortTransaction();
                    throw new KafkaException(exception.getMessage() + " , data: " + record);
                }
            });
        } catch (Throwable e) {
            producer.close();
            throw new KafkaException(e.getMessage());
        }
        System.out.println("************发送成功************");
    }

    public void transactionSend(T t) {
        try {
            producer.beginTransaction();
            ProducerRecord record = new ProducerRecord(TOPIC, JSON.toJSONString(t));
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    producer.abortTransaction();
                    throw new KafkaException(exception.getMessage() + " , data: " + record);
                }
            });
            producer.commitTransaction();
        } catch (Throwable e) {
            producer.close();
            producer.abortTransaction();
            throw new KafkaException(e.getMessage());
        }
        System.out.println("************Transaction发送成功************");
    }

}
