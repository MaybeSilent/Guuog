package guuog.nioserver.buffer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BufferPoolTest {
    private BufferPool bufferpool;

    @BeforeEach
    public void init() {
        bufferpool = new BufferPool();
    }

    @Test
    public void testBuffer() {
        Buffer bufferfirst = bufferpool.createBuffer();
        Buffer buffersecond = bufferpool.createBuffer();
        System.out.println("===== Test For Buffer =====");
        Assertions.assertEquals(bufferfirst.getPool() , buffersecond.getPool());

        Assertions.assertEquals(bufferfirst.getOffset() - buffersecond.getOffset(), 1024*4*-1);
        bufferfirst.expand();
        System.out.println(bufferfirst.getLength());
        Assertions.assertEquals(bufferfirst.getLength(), 1024*64);
    }
}
