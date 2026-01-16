package com.atguigu.zxm.manager.threadTest;

public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        //1，线程名称查看与修改
//        Thread thread = Thread.currentThread();
//        thread.setName("朱学敏朱线程");
//        System.out.println(thread.getName());

//        Thread thread = new Thread();
//        thread.start();

//        //进行跑线程
//        ThreadClazz threadClazz = new ThreadClazz();
//        threadClazz.setName("线程名称1:");
//        threadClazz.start();

        //runnable效果演示
        Thread thread = new Thread(new ThreadClazz(),"runnadle效果演示:");
        thread.start();

        Thread thread1 = new Thread(new TreadClazzOne(),"runnadle效果演示:");
        thread1.start();

//        Thread thread1 = new Thread(() -> {
//                System.out.println("thread 线程1");
//        });
//
//        Thread thread2 = new Thread(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("thread 线程2");
//        });
//
//        thread1.start();
//        thread2.start();
//

//        Thread threadB = new Thread(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("线程B....");
//        });
//        threadB.start();
//        System.out.println("B线程");
//        threadB.join();
//        System.out.println("主线程");


//        Thread threadB = new Thread(() -> {
//            System.out.println("开始执行线程B");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("线程B执行完成");
//        });
//
//        Thread threadA = new Thread(() -> {
//            System.out.println("开始执行线程A");
//            threadB.start();
//            System.out.println("线程B:start启动完成");
//            try {
//                threadB.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("线程A执行完成");
//        });
//        threadA.start();
    }
}
