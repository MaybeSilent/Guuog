package guuog.nioserver.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class BufferChannel {
    // 自定义bufferchannel ， 封装向buffer写入内容的过程
    private SocketChannel socketChannel;
    private int totalreadbytes;

    public BufferChannel(SocketChannel scoketchannel) {
        this.socketChannel = socketChannel;
    }

    public void read(Buffer buffer) throws IOException {
        ByteBuffer bytebuffer = bufferToByte(buffer);
        int byteRead = 0;

        bytebuffer.clear();
        byteRead = socketChannel.read(bytebuffer);
        while (byteRead > 0) {
            byteRead += socketChannel.read(bytebuffer);
        }

        totalreadbytes = byteRead;
    }

    public void write(Buffer buffer) throws IOException {
        ByteBuffer byteBuffer = bufferToByte(buffer);
        
        byteBuffer.clear();
        while(byteBuffer.hasRemaining()){
            socketChannel.write(byteBuffer);
        }

    }

    private ByteBuffer bufferToByte(Buffer buffer) {
        return ByteBuffer.wrap(buffer.getPool(), buffer.getOffset(), buffer.getLength());
    }

}