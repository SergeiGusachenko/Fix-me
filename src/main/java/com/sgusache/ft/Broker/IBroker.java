package com.sgusache.ft.Broker;

import com.sgusache.ft.Production.Instrument;

import java.util.UUID;

public interface IBroker {

    UUID getID();
    void sell(Instrument instrument, Integer quant);
    void buy(Instrument instrument, Integer quant, String marketName);

}
