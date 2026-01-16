package com.atguigu.zxm.manager.threadTest;

public class ThreadClazz extends Thread{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "朱学敏线程执行中");
    }


}
