package guuog.nioserver.connector;


import guuog.nioserver.http.Transfer;
import guuog.nioserver.logging.ReadLogger;
import guuog.nioserver.logging.ServerLogger;
import guuog.nioserver.proxy.Taskproxy;

public class Server {

    public static void main(String[] args) {
        Processor proces = null;
        Taskproxy readproxy = new Taskproxy();
        Taskproxy writeproxy = new Taskproxy();
        try {
            ServerLogger.getLogger().info("Server Thread Start");
            proces = new Processor(readproxy, writeproxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Listener listener = new Listener(8888, readproxy);
        Thread proThread = new Thread(proces, "processor");
        Thread lisThread = new Thread(listener, "listener");

        proThread.start();
        lisThread.start();

        Transfer transfer = new Transfer(writeproxy);
        Thread trans = new Thread(transfer , "transfer");
        trans.start();

        ServerLogger.getLogger().info("BootStrap Of Server Finished");
    }

}