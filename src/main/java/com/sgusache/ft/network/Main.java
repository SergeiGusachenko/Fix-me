package com.sgusache.ft.network;
import com.sgusache.ft.broker.Broker;

public class Main {
    public static void main(String[] args) throws Exception {
        Runnable b = new BrokerSocket(5000);
        Runnable broker = new Broker((BrokerSocket)b);
        Runnable router = new Router();
        Runnable m = new MarketSocket(5001);
        Thread b_thread = new Thread(broker);
        Thread rt = new Thread(router);
        Thread mt = new Thread(m);
        Thread bt = new Thread(b);
        rt.start();
        b_thread.start();
        mt.start();
        mt.sleep(1000);
        bt.start();
   }
}
