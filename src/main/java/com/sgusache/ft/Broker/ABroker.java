package com.sgusache.ft.Broker;

import com.sgusache.ft.Production.Instrument;
import com.sgusache.ft.Production.Production;

import java.util.ArrayList;
import java.util.UUID;

public abstract class ABroker {
    private ArrayList<Production> instruments = new ArrayList<Production>();
    private final UUID id = UUID.randomUUID();

    public UUID getID() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public ABroker() {
    }
    private ArrayList<Production> setInstruments() {
        this.instruments.add(new Instrument("Hammer", 10, 3000));
        this.instruments.add(new Instrument("Water Heaters", 50, 200));
        this.instruments.add(new Instrument("Valves", 52, 330));
        this.instruments.add(new Instrument("Pumps", 12, 130));
        this.instruments.add(new Instrument("Stops", 62, 330));
        this.instruments.add(new Instrument("Toilets", 62, 330));
        this.instruments.add(new Instrument("Drain openers", 22, 220));
        this.instruments.add(new Instrument("Glacier Bay", 52, 111));
        this.instruments.add(new Instrument("Air Conditioner", 52, 111));
        this.instruments.add(new Instrument("Thermostat", 112, 121));
        return this.instruments;
    }

    public ArrayList<Production> getInstruments()
    {
        return  this.instruments;
    }

    public abstract void sell(Instrument instrument, Integer quant);

    public abstract void buy(Instrument instrument, Integer quant, String marketName);
}
