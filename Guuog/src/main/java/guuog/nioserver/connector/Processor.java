package guuog.nioserver.connector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import guuog.nioserver.buffer.Buffer;
import guuog.nioserver.buffer.BufferPool;
import guuog.nioserver.logging.ReadLogger;
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
            ReadLogger.getLogger().info("Register A Request");
            Channel next = readproxy.get();
            next.registerRead(readSelector);

        }
    }

    private void waitForReadAndWrite() throws IOException {
        int readyNum = readSelector.select(2000);

        if (readyNum > 0) {
            Set<SelectionKey> selectedKeys = this.readSelector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();

            while (iter.hasNext()) {
                ReadLogger.getLogger().info("Begin To Read From Request");
                SelectionKey key = iter.next();
                if (key.isReadable()) {
                    Channel channel = (Channel) key.attachment();

                    channel.read();
                    writeproxy.add(channel);

                    if(channel.isFin()) {
                        ReadLogger.getLogger().info("Request Finished");
                        key.cancel();
                        key.channel().close();
                        key.attach(null);
                    }
                }

                iter.remove();

            }

            selectedKeys.clear();

            
        }

    }
}