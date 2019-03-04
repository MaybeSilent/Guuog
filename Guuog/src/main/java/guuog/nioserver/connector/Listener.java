
package guuog.nioserver.connector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

import guuog.nioserver.buffer.BufferChannel;

public class Listener implements Runnable {

    private ServerSocketChannel socketServer;
    private int tcpPort;
    private LinkedList<BufferChannel> bufferQueue;

    public void Listener(int tcpPort , LinkedList bufferQueue){
        this.tcpPort = tcpPort;
        this.bufferQueue = bufferQueue;
    }

    public void run(){
        try{
            socketServer = ServerSocketChannel.open();
            socketServer.bind(new InetSocketAddress(this.tcpPort)) ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            BufferChannel bufferChannel = null ;
            try {
                bufferChannel = new BufferChannel(socketServer.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            if(bufferChannel != null) {
                bufferQueue.addFirst( bufferChannel );
            }
            
        }
        
    }
    
}