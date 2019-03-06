package guuog.nioserver.connector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedList;

public class Processor implements Runnable {
    private LinkedList<Channel> bufferQueue;
    private Selector readSelector;
    private Selector writeSelector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(4 * 1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(64 * 1024);

    public Processor(LinkedList<Channel> bufferQueue) throws IOException {
        this.bufferQueue = bufferQueue;
        this.readSelector = Selector.open();
        this.writeSelector = Selector.open();
    }

    @Override
    public void run() {
        while (true) {
            try {
                registerRead();
                waitForReadAndWrite();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerRead() throws IOException {
        while (!bufferQueue.isEmpty()) {
            System.out.println("register a request");
            Channel next = bufferQueue.removeFirst();
            next.registerRead(readSelector);
            System.out.println("register finished");
        }
    }

    private void waitForReadAndWrite() throws IOException {
        int readyNum = readSelector.selectNow();
        if (readyNum > 0) {
            Iterator<SelectionKey> iter = readSelector.selectedKeys().iterator();

            while (iter.hasNext()) {
                System.out.println("begin read or write");
                SelectionKey key = iter.next();
                if (key.isReadable()) {
                    System.out.println("key is readable");
                    Channel channel = (Channel) key.attachment();
                    channel.read(readBuffer);
                    readBuffer.flip();
                    String receivedString = Charset.forName("UTF-8").newDecoder().decode(readBuffer).toString();
                    System.out.println(receivedString);
                }

            }

            iter.remove();
        }

    }
}