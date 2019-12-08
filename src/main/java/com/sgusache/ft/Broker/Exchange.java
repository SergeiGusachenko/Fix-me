package com.sgusache.ft.Broker;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Exchange {
    private Map<UUID, String> brokers;
    private static Exchange instance;

    private Exchange() {
        brokers = new HashMap<UUID, String>();
    }

    synchronized public static Exchange getInstance()
    {
        if (instance == null)
        {
            instance = new Exchange();
        }
        return instance;
    }

    public void addNewBroker(UUID id, String name){
        if(!this.brokers.containsKey(id))
            this.brokers.put(id,name);
    }
}

