package com.sgusache.ft.broker;

import com.sgusache.ft.FIX.FixMessage;
import com.sgusache.ft.network.BrokerSocket;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
//create trader Broker and Market will extend Terader !!! trytosell and execute methods will be abstract
public class Broker extends Trader implements Runnable {
    private BrokerSocket brokerSocket;
    private ConcurrentHashMap<UUID,Instrument> instruments;

    public Broker(BrokerSocket brokerSocket)
    {
        super();
        this.brokerSocket = brokerSocket;
        brokerSocket.setEnvironment(this);
        this.instruments = getInstruments();
    }

    @Override
    public void run() {
         tryToSell();
    }

    public synchronized void tryToSell(){

        Iterator<Map.Entry<UUID, Instrument>> itr = instruments.entrySet().iterator();
        while(itr.hasNext())
        {
            Map.Entry<UUID, Instrument> elem= itr.next();
            if(elem.getValue().getQuantity() < 200)
            {
                this.brokerSocket.addMessage(FixMessage.getInstance().prepareToSend(elem.getValue().getID().toString(), elem.getValue().getName(),elem.getValue().getQuantity() / 2,
                        elem.getValue().getPrice(),"broker","sell"),5000);
            } else
                this.brokerSocket.addMessage(FixMessage.getInstance().prepareToSend(elem.getValue().getID().toString(), elem.getValue().getName(),elem.getValue().getQuantity() /2,
                        elem.getValue().getPrice(),"broker","buy"),5000);
        }
    }

    @Override
    public void sendRejectMessage(String[] parts) {

        this.brokerSocket.addMessage(FixMessage.getInstance().prepareToSend(parts[0], parts[1] ,Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3]),"broker","REJECT"),5000);
    }

    @Override
    public void sendAcceptMessage(String[] parts) {
        this.brokerSocket.addMessage(FixMessage.getInstance().prepareToSend(parts[0], parts[1] ,Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3]),"market","ACCEPT"),5000);
    }

    @Override
    public void executeMessage(String s) {
        Iterator<Map.Entry<UUID, Instrument>> itr = instruments.entrySet().iterator();

        String []parts = s.split(" ");
        if(parts.length > 2)
        if(parts.length > 2 && parts[4].equals("market"))
        {
            if(parts[5].equals("ACCEPT"))
                acceptChanges(parts);
            else if(parts[5].equals("buy"))
                sendAcceptMessage(parts);
            else if(parts[5].equals("sell"))
                sendRejectMessage(parts);
        }
    }
}
