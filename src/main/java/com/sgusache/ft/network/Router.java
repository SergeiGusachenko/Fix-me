package com.sgusache.ft.network;
import javafx.util.Pair;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class Router implements Runnable
{
    private ArrayList<Integer> acceptList;
    private final UUID brokerID = UUID.randomUUID();
    private final UUID marketID = UUID.randomUUID();
    private MessageHendler messageHendler;
    private Selector selector;

    public Router() throws IOException {
        acceptList = new ArrayList<>();
        selector = registerPorts();
        messageHendler = new MessageHendler();
    }

    public Selector registerPorts() throws IOException {
        int ports[] = new int[]{5001, 5000};
        Selector selector = Selector.open();
        for (int port : ports) {

            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        }
        return selector;
    }

    public void waitForConnection(Selector selector) throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                SelectionKey selectedKey = selectedKeys.next();
                if (selectedKey.isAcceptable()) {
                    if(acceptList.indexOf(((ServerSocketChannel) selectedKey.channel()).socket().getLocalPort()) == -1){
                    SocketChannel socketChannel = ((ServerSocketChannel) selectedKey.channel()).accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    switch (socketChannel.socket().getLocalPort()) {
                        case 5000:
                            System.out.println("\n ***  " + this.getClass().getName() + " ***  ");
                            System.out.println("Connection Accepted: " + socketChannel.getLocalAddress() + "\n");
                            acceptList.add(5000);
                            ByteBuffer bb = ByteBuffer.wrap(brokerID.toString().getBytes());
                            socketChannel.write(bb);
                            // handle connection for the first port (5000)
                            break;
                        case 5001:
                            System.out.println("\n ***  " + this.getClass().getName() + " ***  ");
                            System.out.println("Connection Accepted: " + socketChannel.getLocalAddress() + "\n");
                            ByteBuffer cc = ByteBuffer.wrap(marketID.toString().getBytes());
                            socketChannel.write(cc);
                            // handle connection for the second port (5001)
                            acceptList.add(5001);
                            break;
                    }
                }
                }else if (selectedKey.isReadable()) {
                    SocketChannel sc = (SocketChannel) selectedKey.channel();
                    ByteBuffer bb = ByteBuffer.allocate(1024);
                    sc.read(bb);
                    String result = new String(bb.array()).trim();
                    if(result.length() > 0)
                    {
                        System.out.println("\n ***  " + this.getClass().getName() + " ***  ");
                        System.out.println("Message received: " + result  + " Message length= " + result.length() +" " +"From : " + (sc.socket().getLocalPort() == 5000 ? "Broker" : "Market" ));
                        messageHendler.addMessage(result, sc.socket().getPort());
                    }
                    Pair<String, Integer> message = messageHendler.peekMessage();
                    if( message != null  && sc.socket().getLocalPort() != (message.getValue()))
                    {
                        ByteBuffer cc = ByteBuffer.wrap(message.getKey().getBytes());
                        sc.write(cc);
                        messageHendler.getNextMessage();
                        cc.clear();
                    }
                }

            }
        }
    }

    @Override
    public void run() {
        // loop through each port in our list and bind it to a ServerSocketChannel
        try {
            waitForConnection(selector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
