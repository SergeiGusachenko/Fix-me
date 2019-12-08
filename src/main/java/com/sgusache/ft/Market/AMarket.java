package com.sgusache.ft.Market;

import java.util.UUID;

public abstract class AMarket {
    private final UUID id = UUID.randomUUID();
    public UUID getId()
    {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
