package com.sgusache.ft.network;

import com.sgusache.ft.market.Market;

import java.io.*;

public class MarketSocket extends TradeSocket implements Runnable{
    private Market market;

    public MarketSocket(int port){
        super(port);
    }

    @Override
    protected void sendToExecute(String s) {
        if(this.market != null)
            this.market.executeMessage(s);

    }

    public void setEnvironment(Market market){
        if(this.market == null)
            this.market = market;
    }
    public static void main(String[] args){
        Runnable rout = new MarketSocket(5001);
        rout.run();
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