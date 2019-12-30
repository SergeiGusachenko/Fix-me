package com.sgusache.ft.broker;

import com.sgusache.ft.FIX.FixMessage;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Trader {
    private ConcurrentHashMap<UUID,Instrument> instruments;

    public Trader(){
        instruments = new ConcurrentHashMap<>();
        setInstruments();
    }

    public abstract void tryToSell();

    protected ConcurrentHashMap<UUID,Instrument> getInstruments()
    {
        return this.instruments;
    }

    public abstract void sendRejectMessage(String []parts);

    public abstract void sendAcceptMessage(String []parts);

    public void setInstruments() {
        UUID id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Hammer", 10, 200, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("WaterHeaters", 50, 200, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Valves", 52, 200, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Pumps", 12, 200, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Stops", 62, 200, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Toilets", 62, 100, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("DrainOpeners", 22, 100, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("GlacierBay", 52, 100, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("AirConditioner", 52, 100, id));
        id = UUID.randomUUID();
        this.instruments.put(id, new Instrument("Thermostat", 112, 100, id));
    }
    public synchronized  void   acceptChanges(String [] message)
    {
        Iterator<Map.Entry<UUID, Instrument>> itr = instruments.entrySet().iterator();
        while (itr.hasNext())
        {
            Map.Entry<UUID, Instrument> elem= itr.next();
            System.out.println();
             if(elem.getValue().getName().equals(message[1])){
                 System.out.println("*** Message from: " + message[4] + "***");
                 System.out.println("TRANSACTION ACCEPTED Changes previous quantity = " + elem.getValue().getQuantity());
                 elem.getValue().increaseQuant(Integer.parseInt(message[2]));
                 System.out.println(" ACCEPT Changes new quantity = " + elem.getValue().getQuantity());
             }
        }
    }

    public abstract void executeMessage(String s);
}
