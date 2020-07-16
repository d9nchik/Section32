package sample.exercise10;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("BusyWait")
public class ProblemOfIteratorFixed {
    private static final Set<Integer> set = Collections.synchronizedSet(new LinkedHashSet<>());

    public static void main(String[] args) {
        set.add(-1);
        set.add(-2);

        new Thread(() -> {//Producer
            try {
                for (int i = 0; i < 1_000; i++) {
                    set.add(i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            synchronized (set) {
                Iterator<Integer> iterator = set.iterator();
                try {
                    while (iterator.hasNext()) {
                            iterator.next();
                        Thread.sleep(1_000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
