package guuog.nioserver.http;

import java.io.IOException;
import java.io.Writer;

public class ResponseWriter extends Writer {
    private byte[] bytes = new byte[1024*8];
    private int count ;
    @Override
    public void close() throws IOException {
        count = 0;
    }
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for(int i = 0 ; i < len ; i++){
            bytes[count++] = (byte)cbuf[off+i];
        }
    }

    public int length(){
        return count ;
    }

    public byte[] content(){
        return bytes;
    }
    /**
     * 继承相应接口
     */
    @Override
    public void flush() throws IOException {
        
    }


}