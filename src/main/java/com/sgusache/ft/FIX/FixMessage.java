package com.sgusache.ft.FIX;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FixMessage {
    public String id;
    public String instrument;
    public Integer quantity;
    public Integer price;
    public String marketName;

    public FixMessage(){}

    public FixMessage(String id, String instrument, int quantity, int price, String marketName) {
        this.id = id;
        this.instrument = instrument;
        this.quantity = quantity;
        this.price = price;
        this.marketName = marketName;
    }

    public String prepareToSend(String operation) {
        String message = new String(id + ' ' + instrument + ' ' + quantity.toString() + ' ' + price.toString() + ' ' + marketName + ' ' + operation);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("\n\n\nNot algorithm found ");
            e.printStackTrace();
        }
        md.update(message.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return message + ' ' + myHash;
    }

    public boolean getDirection(String s, String smth) {
        for (String retval : s.split(" ")) {
            if (retval.equals(smth))
                return true;
        }
        return false;
    }

}
