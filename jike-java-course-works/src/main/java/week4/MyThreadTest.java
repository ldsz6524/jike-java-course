package week4;

import java.util.concurrent.*;

/**
 * @description:
 * @author:
 * @create:
 * @other:
 **/

/**
 * 方法1
 */
public class MyThreadTest {

    private static ExecutorService pool = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThreadTest myThreadTest = new MyThreadTest();
        Semaphore semaphore = new Semaphore(1);
        String result = myThreadTest.test(semaphore);
        System.out.println("主线程====>" + Thread.currentThread().getName());
    }

    public String test(Semaphore semaphore) throws InterruptedException, ExecutionException {
        Future<String> submit = pool.submit(() -> System.out.println("子线程===>" + Thread.currentThread().getName() + "-method1"), "");
        semaphore.acquire();
        pool.shutdown();
        return submit.get();
    }
}

/**
 * 方法2
 */
class MyThreadTest02 {

    private static ExecutorService pool = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThreadTest02 myThreadTest = new MyThreadTest02();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String result = myThreadTest.test(countDownLatch);
        System.out.println("主线程====>" + Thread.currentThread().getName());
    }

    public String test(CountDownLatch countDownLatch) throws InterruptedException, ExecutionException {
        Future<String> submit = pool.submit(() -> System.out.println("子线程===>" + Thread.currentThread().getName() + "-method2"), "");
        countDownLatch.countDown();
        pool.shutdown();
        return submit.get();
    }
}