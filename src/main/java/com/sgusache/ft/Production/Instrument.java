package com.sgusache.ft.Production;

import com.sgusache.ft.Production.Production;

public class Instrument implements Production {
    private String name;
    private Integer price;
    private Integer quant;

    public Instrument(String name, Integer price, Integer quant)
    {
        this.name = name;
        this.price = price;
        this.quant = quant;
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
