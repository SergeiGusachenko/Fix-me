package com.sgusache.ft.FIX;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FixMessage { ;
    private static FixMessage instance;
    private FixMessage(){}

    public static synchronized FixMessage getInstance(){
        if(instance == null)
        {
            instance = new FixMessage();
        }
        return instance;
    }

    public String prepareToSend(String id, String instrument, Integer quantity, Integer price, String marketName, String operation) {
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

}
