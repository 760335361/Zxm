package com.atguigu.zxm.manager.threadTest;
//练习守护线程
public class zxmDaemon {
    public static void main(String[] args) {
        God god = new God();
        my my = new my();

        Thread thread1 = new Thread(god);
        thread1.setDaemon(true);
        thread1.start();

        Thread thread2 = new Thread(my);
        thread2.start();
    }


}

class God implements Runnable{

    @Override
    public void run() {
        while (true){
            System.out.println("上帝守护着我");
        }

    }
}

class my implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<3650;i++){
            System.out.println("你活着");
        }
        System.out.println("生命结束-----------------------------------------------------");
    }
}
