
package guuog.nioserver.connector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import guuog.nioserver.proxy.Taskproxy;

public class Listener implements Runnable {

    private ServerSocketChannel socketServer;
    private int tcpPort;
    private Taskproxy proxyOfmess;

    public Listener(int tcpPort, Taskproxy proxy) {
        this.tcpPort = tcpPort;
        this.proxyOfmess = proxy;
    }

    public void run() {
        try {
            socketServer = ServerSocketChannel.open();
            socketServer.bind(new InetSocketAddress(this.tcpPort));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Channel bufferChannel = null;
            try {
                SocketChannel channel = socketServer.accept();
                bufferChannel = new Channel(channel);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (bufferChannel != null) {
                try {
                    bufferChannel.configureBlock(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.proxyOfmess.add(bufferChannel);;
            }

        }

    }

}