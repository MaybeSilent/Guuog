
package guuog.nioserver.connector;

import guuog.nioserver.processor.Buffer;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class Listener {

    private SocketChannel socketChannel;
    private int TcpPort;
    private LinkedList<Buffer> BufferQueue;

}