package sample.exercise11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DeadLock {
    private final static AtomicInteger integer1 = new AtomicInteger(0);
    private final static AtomicInteger integer2 = new AtomicInteger(0);

    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 0; i < 10_000; i++) {
                synchronized (integer1) {
                    integer1.incrementAndGet();
                    synchronized (integer2) {
                        integer2.incrementAndGet();
                    }
                }
                if (i%100==0)
                System.out.println("Job 1 work has been done");
            }
        }).start();

        //Deadlock is easily avoided by using a simple technique known as <b>resource ordering</b>.
        new Thread(()->{
            for (int i = 0; i < 10_000; i++) {
                synchronized (integer2){
                    integer2.incrementAndGet();
                    synchronized (integer1){
                        integer1.incrementAndGet();
                    }
                }
                if (i%100==0)
                System.out.println("Job 2 work has been done");
            }
        }).start();
    }
}
