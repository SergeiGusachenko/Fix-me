package com.sgusache.ft.broker;


import java.util.UUID;

public class Instrument implements Production {
    private String name;
    private Integer price;
    private Integer quant;
    private UUID id;
    public Instrument(String name, Integer price, Integer quant,UUID id)
    {
        this.name = name;
        this.price = price;
        this.quant = quant;
        this.id = id;
    }

    @Override
    public UUID getID() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getQuantity() {
        return this.quant;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void increaseQuant(Integer count) {
        this.quant += count;
    }

    public void decreaseQuant(Integer count) {
        this.quant -= count;
    }
}