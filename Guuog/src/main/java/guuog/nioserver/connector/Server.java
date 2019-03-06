package guuog.nioserver.connector;

import java.util.LinkedList;

public class Server {

    public static void main(String[] args) {
        LinkedList<Channel> acceptList = new LinkedList<>();
        Processor proces = null;
        try {
            proces = new Processor(acceptList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Listener listener = new Listener(8888, acceptList);
        Thread proThread = new Thread(proces, "processor");
        Thread lisThread = new Thread(listener, "listener");

        proThread.start();
        lisThread.start();

    }

}