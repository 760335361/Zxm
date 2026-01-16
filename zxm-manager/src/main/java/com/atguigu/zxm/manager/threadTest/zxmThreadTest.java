package com.atguigu.zxm.manager.threadTest;

public class zxmThreadTest {
    public static void main(String[] args) throws InterruptedException {


        //创建线程2
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("线程2执行完毕");
        });
        thread2.start();
        thread2.join();

        //创建线程1
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("线程1执行完毕");
        });
        thread1.start();
        thread1.join();

        //主线程
        System.out.println("主线程");
    }
}
