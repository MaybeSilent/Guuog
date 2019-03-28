package guuog.nioserver.connector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import guuog.nioserver.buffer.Buffer;

public class Channel {
    // 自定义channel ， 用于封装nio的读写功能
    private SocketChannel socketChannel;
    private Selector selector;

    private boolean readfinished; //读操作结束的标志位
    private boolean writefinished; //写操作结束的标志位

    private ByteBuffer bytebuffer;
    private Buffer buffer;

    public Channel(SocketChannel channel, Buffer buffer) {
        this.socketChannel = channel;
        this.buffer = buffer;
        this.bytebuffer = ByteBuffer.wrap(buffer.getPool(),buffer.getOffset(),buffer.getLength());
    }

    public int read() throws IOException {
        return read(this.bytebuffer);
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

    public void write(ByteBuffer buffer) throws IOException {

        int byteRead = socketChannel.write(buffer);
        while (byteRead > 0 && buffer.hasRemaining()) {
            byteRead = socketChannel.write(buffer);
        }
        if (!buffer.hasRemaining()) {
            writefinished = true;
        }

    }

    public boolean containsBuffer() {
        return this.buffer != null && this.bytebuffer != null;
    }

    public boolean isFin() {
        return readfinished;
    }

    public boolean isFinW() {
        return writefinished;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void registerRead(Selector selector) throws IOException {
        this.selector = selector;
        socketChannel.register(selector, SelectionKey.OP_READ, this);
    }

    public void registerWrite(Selector selector) throws IOException {
        socketChannel.register(selector, SelectionKey.OP_WRITE, this);
    }

    public void configureBlock(boolean flag) throws IOException {
        this.socketChannel.configureBlocking(flag);
    }


    public  void close() {
        this.bytebuffer = null;
        buffer.close();
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the socketChannel
     */
    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    /**
     * @param socketChannel the socketChannel to set
     */
    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    /**
     * @return the readfinished
     */
    public boolean isReadfinished() {
        return readfinished;
    }

    /**
     * @param readfinished the readfinished to set
     */
    public void setReadfinished(boolean readfinished) {
        this.readfinished = readfinished;
    }

}