import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
public class Router
{
    private static ArrayList<Integer> acceptList;
    public static void main(String[] args) throws Exception {
        int ports[] = new int[]{5001, 5000};
        acceptList = new ArrayList<>();
        InetAddress host = InetAddress.getByName("localhost");
        Selector selector = Selector.open();
        // loop through each port in our list and bind it to a ServerSocketChannel
        for (int port : ports) {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        while (true) {
            selector.select();

            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                SelectionKey selectedKey = selectedKeys.next();

                if (selectedKey.isAcceptable() && acceptList.indexOf(((ServerSocketChannel) selectedKey.channel()).socket().getLocalPort()) == -1) {
                    SocketChannel socketChannel = ((ServerSocketChannel) selectedKey.channel()).accept();
                    System.out.println(" configureBlocking is called ");
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    switch (socketChannel.socket().getLocalPort()) {
                        case 5000:
                            System.out.println("Connection Accepted: " + socketChannel.getLocalAddress() + "\n" );
                            acceptList.add(5000);
                            // handle connection for the first port (5000)

                            break;
                        case 5001:
                            System.out.println("Connection Accepted: " + socketChannel.getLocalAddress() + "\n" );
                            // handle connection for the secon port (5001)
                            acceptList.add(5001);
                            break;
                    }
                } else if (selectedKey.isReadable()) {
                    SocketChannel sc = (SocketChannel) selectedKey.channel();
                    ByteBuffer bb = ByteBuffer.allocate(1024);
                    sc.read(bb);
                    String result = new String(bb.array()).trim();
                    if(result.length() > 0)
                    System.out.println("Message received: " + result  + " Message length= " + result.length() +"\n" +"From : " + sc.socket().getLocalPort());
                }
            }
        }
    }
}
