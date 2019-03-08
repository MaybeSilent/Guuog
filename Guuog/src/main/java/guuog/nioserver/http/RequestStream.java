package guuog.nioserver.http;

import java.io.IOException;

import javax.servlet.ServletInputStream;

public class RequestStream extends ServletInputStream {

    private byte[] bufferaddr;
    private int offset;
    private int length;
    private int count;

    public RequestStream(byte[] addr, int offset, int length) {
        this.count = 0;
        this.bufferaddr = addr;
        this.offset = offset;
        this.length = length;
    }

    @Override
    public int read() throws IOException {
        if (count > length) {
            return -1;
        }
        int ans = (int) bufferaddr[offset + count];
        count++;
        return ans;
    }

    @Override
    public int readLine(byte[] b, int off, int len) throws IOException {

        if (len <= 0) {
            return 0;
        }
        int indexcount = 0, c;

        while ((c = read()) != -1) {
            indexcount++;
            if (c == '\n' || count == len) {
                break;
            }
        }
        return indexcount > 0 ? indexcount : -1;
    }

    public String readLine() {
        int index = 0;
        try {
            index = readLine(bufferaddr, offset, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (index == -1) {
            return null; // 读取到最后，已经结束，所以return null
        }
        String str = null ;
        if(index - 2 >= 0) str = new String(bufferaddr, offset + count - index, index - 2);
        return str;
    }

}