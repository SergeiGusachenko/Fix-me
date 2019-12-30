package com.sgusache.ft.market;

import com.sgusache.ft.FIX.FixMessage;
import com.sgusache.ft.broker.Instrument;
import com.sgusache.ft.broker.Trader;
import com.sgusache.ft.network.MarketSocket;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Market extends Trader implements Runnable {
    private MarketSocket marketSocket;
    private ConcurrentHashMap<UUID,Instrument> instruments;

     public Market(MarketSocket marketSocket){
        super();
        this.marketSocket = marketSocket;
        marketSocket.setEnvironment(this);
        this.instruments = getInstruments();
    }

    @Override
    public void run() {
        tryToSell();
    }

    public void sendRejectMessage(String [] parts){
        this.marketSocket.addMessage(FixMessage.getInstance().prepareToSend(parts[0], parts[1] ,Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3]),"market","REJECT"),5001);
    }
    public void sendAcceptMessage(String [] parts){

        this.marketSocket.addMessage(FixMessage.getInstance().prepareToSend(parts[0], parts[1] ,Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3]),"market","ACCEPT"),5001);
    }
    @Override
    public  synchronized  void tryToSell() {
        Iterator<Map.Entry<UUID, Instrument>> itr = instruments.entrySet().iterator();
        while(itr.hasNext())
        {
            Map.Entry<UUID, Instrument> elem= itr.next();
            if(elem.getValue().getQuantity() > 100)
            {
                this.marketSocket.addMessage(FixMessage.getInstance().prepareToSend(elem.getValue().getID().toString(), elem.getValue().getName(),elem.getValue().getQuantity(),
                        elem.getValue().getPrice(),"market","buy"),5001);
            }
        }
    }

    @Override
    public void executeMessage(String s) {
        String []parts = s.split(" ");
        if(parts.length > 2)
        if(parts.length > 2 && parts[4].equals("broker"))
        {
            if(parts[5].equals("ACCEPT"))
                acceptChanges(parts);
            else if(parts[5].equals("sell"))
                sendAcceptMessage(parts);
            else if(parts[5].equals("buy"))
                sendRejectMessage(parts);
        }
    }
}
