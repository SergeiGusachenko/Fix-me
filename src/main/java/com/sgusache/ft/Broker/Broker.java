package com.sgusache.ft.broker;

import com.sgusache.ft.FIX.FixMessage;
import com.sgusache.ft.network.BrokerSocket;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Broker implements Runnable {
    private ConcurrentHashMap<UUID,Instrument> instruments;
    private BrokerSocket brokerSocket;
    public Broker(BrokerSocket brokerSocket)
    {

        this.brokerSocket = brokerSocket;
        brokerSocket.setEnvironment(this);
        instruments = new ConcurrentHashMap<>();
        setInstruments();
    }

    public void setInstruments()
    {
        UUID id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Hammer", 10, 200,id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Water Heaters", 50, 200,id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Valves", 52, 200, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Pumps", 12, 200, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Stops", 62, 200, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Toilets", 62, 100, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Drain openers", 22, 100, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Glacier Bay", 52, 100, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Air Conditioner", 52, 100, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Thermostat", 112, 100, id));
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
            if(elem.getValue().getQuantity() > 100)
            {
                this.brokerSocket.addMessage(new FixMessage(elem.getValue().getID().toString(), elem.getValue().getName(),elem.getValue().getQuantity(),
                        elem.getValue().getPrice(),"broker").prepareToSend("sell"),5000);
            }
        }
    }

    public synchronized  void exectuteMessage(String message){
        Iterator<Map.Entry<UUID, Instrument>> itr = instruments.entrySet().iterator();
        System.out.println("\n \n\n\n\n\n\nTRY TO EXECTUTE\n\n\n\n");

//        while(itr.hasNext())
//        {
//            Map.Entry<UUID, Instrument> elem= itr.next();
//        }
    }
}
