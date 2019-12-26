package com.sgusache.ft.network;

import java.io.*;

public class MarketSocket extends TradeSocket implements Runnable{
    private static BufferedReader input = null;
    private int port;

    public MarketSocket(int port){
        super(port);
        this.port = port;
    }

    @Override
    protected void sendToBroker(String s) {
        System.out.println("sendTOBROKERINIDE MARKET");
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