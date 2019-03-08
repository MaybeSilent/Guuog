package guuog.nioserver.proxy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import guuog.nioserver.connector.Channel;

public class Taskproxy{
    private BlockingQueue<Channel> list;
    public Taskproxy(){
        this.list = new ArrayBlockingQueue<>(64);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public void add(Channel channel){
        try {
            list.put(channel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    public Channel get() {
        try {
            return list.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}