package com.sgusache.ft.Production;

public interface Production {
    String getName();
    Integer getQuantity();
    Integer getPrice();
    void    increaseQuant(Integer count);
    void    decreaseQuant(Integer count);

}
