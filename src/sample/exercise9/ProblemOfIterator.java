package sample.exercise9;

import java.util.*;

@SuppressWarnings("BusyWait")
public class ProblemOfIterator {
    private static final Set<Integer> set = Collections.synchronizedSet(new LinkedHashSet<>());
    public static void main(String[] args) {
        set.add(-1);
        set.add(-2);

        new Thread(()->{//Producer
            try{
                for (int i = 0; i < 1_000; i++) {
                    set.add(i);
                    Thread.sleep(1_000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            Iterator<Integer> iterator = set.iterator();
            try{
                while (iterator.hasNext()) {
                    iterator.next();
                    Thread.sleep(1_000);
                }
                System.out.println("out");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
