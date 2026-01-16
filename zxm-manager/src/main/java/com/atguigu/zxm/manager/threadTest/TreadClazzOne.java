package com.atguigu.zxm.manager.threadTest;

public class TreadClazzOne extends ThreadClazz implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "接口runnable呈现出来的效果");
    }
}
