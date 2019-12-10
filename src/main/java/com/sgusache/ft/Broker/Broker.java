package com.sgusache.ft.Broker;

import com.sgusache.ft.Production.Instrument;
import com.sgusache.ft.Production.Production;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class Broker extends ABroker implements IBroker {
    private Map<UUID, String> markets;
    public ArrayList<Production> instruments;
    private String name;
    public Broker(String name,Map<UUID, String> markets)
    {
        super();
        this.markets = markets;
        this.instruments = super.getInstruments();
        this.name = name;
    }

    public void sell(Instrument instrument, Integer quant) {

    }

    public void buy(Instrument instrument, Integer quant, String marketName) {

    }

    public String getName(){
        return this.name;
    }
}
