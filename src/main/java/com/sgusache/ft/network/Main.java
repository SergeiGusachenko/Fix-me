package com.sgusache.ft.network;
import com.sgusache.ft.broker.Broker;
import com.sgusache.ft.market.Market;

public class Main {
    public static void main(String[] args) throws Exception {

        Runnable brokerSocket = new BrokerSocket(5000);
        Runnable marketSocket = new MarketSocket(5001);
        Runnable broker = new Broker((BrokerSocket)brokerSocket);
        Runnable router = new Router();
        Runnable market = new Market((MarketSocket) marketSocket);


        Thread market_thread = new Thread(market);
        Thread b_thread = new Thread(broker);
        Thread rt = new Thread(router);
        Thread mt = new Thread(marketSocket);
        Thread bt = new Thread(brokerSocket);


        rt.start();
        b_thread.start();
        mt.start();
        market_thread.start();
        mt.sleep(1000);
        bt.start();
   }
}
