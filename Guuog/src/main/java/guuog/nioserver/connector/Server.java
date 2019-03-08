package guuog.nioserver.connector;


import guuog.nioserver.http.Transfer;
import guuog.nioserver.proxy.Taskproxy;

public class Server {

    public static void main(String[] args) {
        Processor proces = null;
        Taskproxy readproxy = new Taskproxy();
        Taskproxy writeproxy = new Taskproxy();
        try {
            proces = new Processor(readproxy, writeproxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("thread starting ...........");
        Listener listener = new Listener(8888, readproxy);
        Thread proThread = new Thread(proces, "processor");
        Thread lisThread = new Thread(listener, "listener");

        proThread.start();
        lisThread.start();

        Transfer transfer = new Transfer(writeproxy);
        Thread trans = new Thread(transfer , "transfer");
        trans.start();

        //System.out.println("thread ending .............");


    }

}