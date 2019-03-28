package guuog.nioserver.buffer;

import java.nio.ByteBuffer;

public class Buffer {
    private byte[] pool;
    private int offset; // 缓冲区相对于数组的起点距离
    private int length; // 缓冲区长度
    private BufferPool bufferpool;

    public Buffer(byte[] start, int offset, int length, BufferPool bufferPool) {
        this.pool = start;
        this.offset = offset;
        this.length = length;
        this.bufferpool = bufferPool;
    }

    public ByteBuffer toByte() {
        return ByteBuffer.wrap(pool, offset, length);
    }

    // 对buffer的空间进行回收
    public void close() {
        bufferpool.closeBuffer(length, offset);
    }

    // 方便buffer直接调用 ， 而不用调用bufferpool来扩展buffer
    public Buffer expand() {
        return bufferpool.expandBuffer(this);
    }

    /**
     * 为buffer自动生成相应的getset方法
     */

    /**
     * @return the pool
     */
    public byte[] getPool() {
        return pool;
    }

    /**
     * @param pool the pool to set
     */
    public void setPool(byte[] pool) {
        this.pool = pool;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

}