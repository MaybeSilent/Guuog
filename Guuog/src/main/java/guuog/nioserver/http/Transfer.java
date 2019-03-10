package guuog.nioserver.http;

import java.nio.ByteBuffer;

import guuog.nioserver.buffer.Buffer;
import guuog.nioserver.connector.Channel;
import guuog.nioserver.content.FilePool;
import guuog.nioserver.proxy.Taskproxy;

public class Transfer implements Runnable {
    private Taskproxy taskproxy;
    private FilePool filePool;
    
    public static final String notfound = "<html><body>The Page Not Found in GUUOG</body></html>";
    public static final String serverErro = "<html><body>Server Error Happend in GUUOG</body></html>";

    public Transfer(Taskproxy taskproxy) {
        // writeproxy get the data
        this.filePool = new FilePool();
        this.taskproxy = taskproxy;
    }

    @Override
    public void run() {
        while (true) {

            while (!taskproxy.isEmpty()) {
                Channel channel = taskproxy.get();
                
                Buffer buffer = channel.getBuffer();

                RequestStream stream = new RequestStream(buffer.getPool(), buffer.getOffset(), buffer.getLength());
                Request request = new Request(stream);
                request.parse();
                //对请求进行解析
                byte[] body = null;
                byte[] head = null;
                if(request.getMethod() != null && request.getUri() != null){

                    Response respon = new Response();
                    String uri = request.getUri();
                    if( request.getMethod().equals("GET") && filePool.containsFile(uri)){
                        respon.setOK();
                        body = filePool.getFileByte(uri);
                        respon.addHeader(Response.CONTENTLENGTH, String.valueOf(body.length));
                        respon.addHeader(Response.CONTENTTYPE, "text/html");
                        head = respon.getbyte();
                    } else {
                        respon.set404();
                        respon.addHeader(Response.CONTENTTYPE, "text/html");
                        respon.addHeader(Response.CONTENTLENGTH, String.valueOf(notfound.length()));
                        body = notfound.getBytes();
                        head = respon.getbyte();
                    }

                }
                try {
                    channel.write(ByteBuffer.wrap(head));
                    channel.write(ByteBuffer.wrap(body));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

   
}