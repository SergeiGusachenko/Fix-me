package com.sgusache.ft.test;

public class TreadTest implements Runnable{
        @Override
    public void run() {
        for(int i = 1; i < 10; i++)
        {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Siski");
        }
    }
}
