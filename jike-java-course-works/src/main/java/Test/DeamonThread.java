package Test;

import javax.annotation.processing.SupportedSourceVersion;

/**
 * @description:
 * @author:
 * @create:
 * @other:
 **/
public class DeamonThread {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread t = Thread.currentThread();
                System.out.println("当前线程:" + t.getName());
            }
        };

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("当前线程:" + this.getName());
//            }
//        };

        Thread thread = new Thread(task);
        thread.setName("test-thread-xxx");
        thread.setDaemon(true); //设置守护线程
        thread.start();
        thread.sleep(2000);
    }
}
