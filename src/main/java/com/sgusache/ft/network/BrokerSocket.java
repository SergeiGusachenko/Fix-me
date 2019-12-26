package com.sgusache.ft.network;

import com.sgusache.ft.broker.Broker;

public class BrokerSocket extends TradeSocket implements Runnable{

    private Broker broker;
    public BrokerSocket(int port) {
        super(port);

    }

    public void setEnvironment(Broker broker) {
        if(this.broker == null)
            this.broker = broker;
    }
    protected void sendToBroker(String s){
        if(this.broker != null)
            this.broker.exectuteMessage(s);
    }

    @Override
    public void run() {
        try {
            openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}