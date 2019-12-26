package com.sgusache.ft.broker;

import java.util.UUID;

public interface Production {
    UUID getID();
    String getName();
    Integer getQuantity();
    Integer getPrice();
    void    increaseQuant(Integer count);
    void    decreaseQuant(Integer count);

}