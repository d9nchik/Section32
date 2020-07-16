package sample.exercise4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizeThreads {
    private static Integer sum = 0;

    @SuppressWarnings("BusyWait")
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 1_000; i++)
            executor.execute(SynchronizeThreads::incrementSum);

        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                //Wait until all jobs are done.
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Total sum " + sum);
    }

    //Synchronize function to avoid racing
    synchronized private static void incrementSum(){
        ++sum;
    }
}
