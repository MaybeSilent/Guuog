package guuog.nioserver.connector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

import guuog.nioserver.buffer.Buffer;
import guuog.nioserver.buffer.BufferPool;
import guuog.nioserver.proxy.Taskproxy;

public class Processor implements Runnable {
    private Selector readSelector;
    private BufferPool bufferPool;

    private Taskproxy writeproxy;
    private Taskproxy readproxy;

    public Processor(Taskproxy readproxy , Taskproxy writeproxy ) throws IOException {
        this.readSelector = Selector.open();
        this.bufferPool = new BufferPool();
        this.readproxy = readproxy;
        this.writeproxy = writeproxy;
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
        while (!readproxy.isEmpty()) {
            System.out.println("register a request");
            Channel next = readproxy.get();
            next.registerRead(readSelector);
            System.out.println("register finished");
        }
    }

    private void waitForReadAndWrite() throws IOException {
        int readyNum = readSelector.select(2000);
        if (readyNum > 0) {
            Iterator<SelectionKey> iter = readSelector.selectedKeys().iterator();

            while (iter.hasNext()) {
                System.out.println("begin read or write");
                SelectionKey key = iter.next();
                if (key.isReadable()) {
                    System.out.println("key is readable");
                    Channel channel = (Channel) key.attachment();
                    
                    Buffer buffer = this.bufferPool.createBuffer();
                    ByteBuffer readBuffer = buffer.toByte();
                    channel.read(readBuffer);
                    channel.setBuffer(buffer);

                    writeproxy.add(channel);
                    
                    if(channel.isFin()){
                        System.out.println("finished");
                        key.cancel();
                    }
                        
                }

                iter.remove();

            }

            
        }

    }
}