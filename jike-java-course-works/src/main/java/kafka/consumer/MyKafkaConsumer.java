import com.alibaba.fastjson.JSON;
import kafka.producer.MyKafkaProducer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;


public class MyKafkaConsumer<T> {
    private Properties properties;
    private KafkaConsumer<String, String> consumer;
    private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
    private Set<T> orderSet = new HashSet<>();

    public MyKafkaConsumer() {
        properties = new Properties();
//        properties.put("enable.auto.commit", false);
//        properties.put("isolation.level", "read_committed");
//        properties.put("auto.offset.reset", "latest");
        properties.put("group.id", "consumer-group1");
        properties.put("bootstrap.servers", "localhost:9001,localhost:9002,localhost:9003");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(properties);
    }

    public void consumer(Class<T> tClass) {
        //订阅主题
        consumer.subscribe(Collections.singletonList(MyKafkaProducer.TOPIC));
        try {
            while (true) {
                //拉取数据(每秒轮询)
                ConsumerRecords<String, String> poll = consumer.poll(Duration.ofSeconds(1));
                if (Objects.isNull(poll)) {
                    throw new KafkaException("轮询获取数据为空");
                }
                //遍历获取数据
                for (ConsumerRecord<String, String> record : poll) {
                    T t = JSON.parseObject(record.value(), tClass);
                    System.out.println(" body = " + t);
                    //使用Set集合去重(需要自己重写equals和hashCode方法)
//                    orderSet.add(t);
//                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
//                            new OffsetAndMetadata(record.offset() + 1, "no matadata"));
//                    consumer.commitAsync(currentOffsets, new OffsetCommitCallback() {
//                        @Override
//                        public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
//                            if (exception != null) {
//                                exception.printStackTrace();
//                            }
//                        }
//                    });
                }
            }
        } catch (CommitFailedException e) {
            throw new KafkaException(e.getMessage());
        } finally {
            try {
                //currentOffsets;
                consumer.commitSync();
            } catch (Exception e) {
                consumer.close();
            }
        }
    }

}
