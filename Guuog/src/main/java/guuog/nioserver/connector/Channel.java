package guuog.nioserver.connector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Channel {
    // 自定义channel ， 用于封装nio的读写功能
    private SocketChannel socketChannel;
    private boolean readfinished;

    public Channel(SocketChannel channel) {
        this.socketChannel = channel;
    }

    public int read(ByteBuffer buffer) throws IOException {

        int byteread = 0;
        byteread = socketChannel.read(buffer);
        int totalbyte = 0;
        while (byteread > 0) {
            totalbyte += byteread;
            byteread = socketChannel.read(buffer);
        }

        if (byteread == -1) {
            readfinished = true;
        }

        return totalbyte;

    }

    public void write() throws IOException {

    }

    public boolean isFin() {
        return readfinished;
    }

    public void registerRead(Selector selector) throws IOException {
        socketChannel.register(selector, SelectionKey.OP_READ, this);
    }

    public void registerWrite(Selector selector) throws IOException {
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public void configureBlock(boolean flag) throws IOException {
        this.socketChannel.configureBlocking(flag);
    }
}