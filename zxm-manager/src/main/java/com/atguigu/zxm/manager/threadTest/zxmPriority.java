package com.atguigu.zxm.manager.threadTest;

public class zxmPriority {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"----->"+Thread.currentThread().getPriority());
        MyPriority myPriority = new MyPriority();
        Thread thread0 = new Thread(myPriority);
        Thread thread1 = new Thread(myPriority);
        Thread thread2 = new Thread(myPriority);
        Thread thread3 = new Thread(myPriority);
        Thread thread4 = new Thread(myPriority);
        Thread thread5 = new Thread(myPriority);

        thread0.setPriority(1);
        thread0.start();
        thread1.setPriority(2);
        thread1.start();
        thread2.setPriority(8);
        thread2.start();
        thread3.setPriority(7);
        thread3.start();
        thread4.setPriority(2);
        thread4.start();
        thread5.setPriority(10);
        thread5.start();
    }
}

class MyPriority implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"----->"+Thread.currentThread().getPriority());
    }
}
