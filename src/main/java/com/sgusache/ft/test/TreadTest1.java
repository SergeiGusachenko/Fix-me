package com.sgusache.ft.test;

public class TreadTest1 implements Runnable{
    @Override
    public void run() {
        for(int i = 1; i < 10; i++)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Zhopa");
        }
    }
}
