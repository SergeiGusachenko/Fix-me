package com.sgusache.ft.network;
import javafx.util.Pair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public abstract class TradeSocket {
    private static BufferedReader input = null;
    private int port;
    private MessageHendler messageHendler;
    public TradeSocket(int port){
            this.port = port;
            messageHendler = new MessageHendler();
    }

    public void addMessage(String message, int port){
        messageHendler.addMessage(message, port);
    }

    public synchronized Boolean processConnect(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        try {
            while (sc.isConnectionPending()) {
                sc.finishConnect();
            }
        } catch (IOException e) {
            key.cancel();
            e.printStackTrace();
            return false;
        }
        return true;
    }
    protected abstract void sendToExecute(String s);

    public synchronized Boolean processReadySet(Set readySet)
            throws Exception {
        SelectionKey key = null;
        Iterator iterator;
        iterator = readySet.iterator();
        while (iterator.hasNext()) {
            key = (SelectionKey) iterator.next();
            iterator.remove();
        }

        if (key.isConnectable()) {
            Boolean connected = processConnect(key);
            if (!connected) {
                return true;
            }
        }

        if (key.isReadable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer bb = ByteBuffer.allocate(512);
            sc.read(bb);
            String result = new String(bb.array()).trim();
            System.out.println("\n ***  " + this.getClass().getName() + " ***  " + "Message received from Server: " + result + " Message length= "+ result.length());
            sendToExecute(result);
        }

        if (key.isWritable()) {
            Pair<String, Integer> message = messageHendler.getNextMessage();
            if ( message != null)
            {
                Thread.currentThread().sleep(10000);
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer bb = ByteBuffer.wrap(message.getKey().getBytes());
                sc.write(bb);
            }
        }
        return false;
    }

    public synchronized void  openConnection() throws Exception {
        InetSocketAddress addr = new InetSocketAddress(
                InetAddress.getByName("localhost"), this.port);
        Selector selector = Selector.open();
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(addr);
        sc.register(selector, SelectionKey.OP_CONNECT |
                SelectionKey.OP_READ | SelectionKey.
                OP_WRITE);
        input = new BufferedReader(new
                InputStreamReader(System.in));
        while (true) {
            if (selector.select() > 0) {
                Boolean doneStatus = processReadySet(selector.selectedKeys());
                if (doneStatus) {
                    break;
                }
            }
        }
        sc.close();
    }
}
