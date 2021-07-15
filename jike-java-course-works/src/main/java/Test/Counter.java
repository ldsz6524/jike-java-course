package Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description:
 * @author:
 * @create:
 * @other:
 **/
public class Counter {

    public final static int A = 10;
    public static int B = 10;

    private AtomicInteger sum = new AtomicInteger(0);

    public final int loop = 100000;

    private ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public void incr() {
//        lock.writeLock().lock();
        sum.incrementAndGet();
//        lock.writeLock().unlock();
    }

    public int getSum() {return sum.get();}

    public void incr02(Counter counter) {
        for(int i=0; i < loop / 2; i++) {
            counter.incr();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int loop = new Counter().loop;

        Counter counter = new Counter();
        for(int i=0; i<loop; i++) {
            counter.incr();
        }
        System.out.println("single thread:" + counter.getSum());

        final Counter counter2 = new Counter();


        Thread t1 = new Thread(() -> {
            for(int i=0; i < loop / 2; i++) {
                counter2.incr();
            }
        });

//        Thread t1 = new Thread(() -> {
//            new Counter().incr02(counter2);
//        });


        Thread t2 = new Thread(() -> {
            for(int i=0; i < loop / 2; i++) {
                counter2.incr();
            }
        });

//        Thread t2 = new Thread(() -> {
//            new Counter().incr02(counter2);
//        });

        t1.start();
        t2.start();
        Thread.sleep(500);

        System.out.println("multiple threads:" + counter2.getSum());

    }
}
