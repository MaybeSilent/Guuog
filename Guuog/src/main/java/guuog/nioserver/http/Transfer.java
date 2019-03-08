package guuog.nioserver.http;


import guuog.nioserver.buffer.Buffer;
import guuog.nioserver.connector.Channel;
import guuog.nioserver.proxy.Taskproxy;

public class Transfer implements Runnable {
    private Taskproxy taskproxy;

    public Transfer(Taskproxy taskproxy) {
        // writeproxy get the data
        this.taskproxy = taskproxy;
    }

    @Override
    public void run() {
        while (true) {
            Channel channel = taskproxy.get();
            Buffer buffer = channel.getBuffer();
            //System.out.println(new String(buffer.getPool(), buffer.getOffset() ,buffer.getLength() ));
            RequestStream stream = new RequestStream(buffer.getPool(),buffer.getOffset(), buffer.getLength());
            Request request = new Request(stream);
            request.parse();
        }
    }

}