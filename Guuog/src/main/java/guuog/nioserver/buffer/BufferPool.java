package guuog.nioserver.buffer;

import guuog.nioserver.logging.WriteLogger;

public class BufferPool {
    private static int KB4 = 1024 * 4;
    private static int KB64 = 1024 * 64;

    private byte[] smallBufferPool = new byte[1024 * KB4]; // 1024个4KB区域
    private byte[] bigBufferPool = new byte[256 * KB64]; // 256个64KB区域

    private ResizeArray smallAddrArray = new ResizeArray(1024);
    private ResizeArray bigAddrArray = new ResizeArray(1024);

    public BufferPool() {
        for (int i = 0, j = 0, k = 0; k < 1024; i += KB4, j += KB64, k++) {
            smallAddrArray.put(i);
            bigAddrArray.put(j);
        }
    }

    public void closeBuffer(int length, int start) {
        if (length == KB4) {
            smallAddrArray.put(start);
            WriteLogger.getLogger().info(start + " of samllBuffer Is Recycled");
        } else if (length == KB64) {
            bigAddrArray.put(start);
            WriteLogger.getLogger().info(start + " of BigBuffer Is Recycled");
        } else {
            WriteLogger.getLogger().info("Buffer Is Not Recycled");
        }
    }

    public Buffer createBuffer() {
        int addr = smallAddrArray.take();
        if (addr == -1)
            return null;
        return new Buffer(smallBufferPool, addr, KB4, this);
    }

    /**
     * 对4kb的buffer进行相应的扩容 , 增加到64kb
     */
    public Buffer expandBuffer(Buffer buffer) {
        if (buffer.getPool() == smallBufferPool) {
            int newaddr = bigAddrArray.take();
            if (newaddr == -1)
                return null; // 无法申请到容量较大的地址
            buffer.getOffset();
            setBuffer(newaddr, buffer);
            return buffer;
        } else {
            return null; // 已经是大容量地址了
        }
    }

    private void setBuffer(int addr, Buffer buffer) {
        // 将原本的内容复制到对应的位置
        System.arraycopy(smallBufferPool, buffer.getOffset(), bigBufferPool, addr, KB4);
        // 回收之前的小内存区域
        smallAddrArray.put(buffer.getOffset());
        buffer.setLength(KB64);
        buffer.setOffset(addr);
        buffer.setPool(bigBufferPool);
    }

    /**
     * content表示各个地址的起点 在bufferpool为相应的content内容分配缓冲区
     */
    class ResizeArray {
        private int[] content = null;

        private int writePos = 0;
        private int readPos = 0;
        private int capacity = 0;

        private int numbers = 0;

        public ResizeArray(int capacity) {
            this.capacity = capacity;
            this.content = new int[capacity];
        }

        public int available() {
            return numbers;
        }

        public int remineContent() {
            return capacity - numbers;
        }

        public int take() {
            if (available() > 0) {
                numbers--;
                if (readPos >= capacity) {
                    readPos = 0;
                }
                return content[readPos++];
            }
            return -1;
        }

        public boolean put(int num) {
            if (remineContent() > 0) {
                numbers++;
                content[writePos] = num;
                writePos = (writePos + 1) % capacity;
                return true;
            }
            return false;
        }
    }
}