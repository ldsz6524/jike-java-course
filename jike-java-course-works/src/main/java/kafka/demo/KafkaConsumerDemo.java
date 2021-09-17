import kafka.consumer.MyKafkaConsumer;


public class KafkaConsumerDemo {

    public static void main(String[] args) {
        testConsumer();
    }

    private static void testConsumer() {
        final MyKafkaConsumer<Order> consumer = new MyKafkaConsumer<>();
        consumer.consumer(Order.class);
    }

}
