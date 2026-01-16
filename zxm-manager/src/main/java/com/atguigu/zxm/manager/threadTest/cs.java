package com.atguigu.zxm.manager.threadTest;


public class cs {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            long count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                count++;
                if(count % 1_00_000 == 0){
                System.out.print("\rcount:" + count);
                }
            }
        });
        thread.start();


        Thread threadStorp = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("中断count线程信息");
            thread.interrupt();
        });
        threadStorp.start();
    }
}
