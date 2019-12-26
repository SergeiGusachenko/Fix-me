package com.sgusache.ft.network;

import javafx.util.Pair;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageHendler {
    private BlockingQueue<Pair<String, Integer>> messages;

    public MessageHendler() {
        messages = new ArrayBlockingQueue<>(100);
    }

    public synchronized void  addMessage(String message, int port) {
        if(messages.size() == 99) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        messages.add(new Pair<>(message, port));
    }
    public Pair<String, Integer> getNextMessage() {
        return messages.poll();
    }
    public Pair<String, Integer> peekMessage(){
        return messages.peek();
    }
}
