package sample.exercise12;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ArrayInitializer {
    public static void main(String[] args) {
        double[] list = new double[50_000_000];
        long startTime = System.currentTimeMillis();
        parallelAssignValues(list);
        long finishTime = System.currentTimeMillis();
        System.out.println("Parallel algorithm: " + (finishTime - startTime));

        Random random = new Random();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < list.length; i++) {
            list[i] = random.nextDouble();
        }
        finishTime = System.currentTimeMillis();
        System.out.println("Sequential algorithm: " + (finishTime - startTime));
    }


    public static void parallelAssignValues(double[] list) {
        RecursiveAction mainTask = new ArrayInitialize(list);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }

    private static class ArrayInitialize extends RecursiveAction {
        private final static int THRESHOLD = 500;
        private final int START_INDEX;
        private final int END_INDEX;
        private final double[] LIST;

        public ArrayInitialize(int START_INDEX, int END_INDEX, double[] LIST) {
            this.START_INDEX = START_INDEX;
            this.END_INDEX = END_INDEX;
            this.LIST = LIST;
        }

        public ArrayInitialize(double[] LIST) {
            this(0, LIST.length, LIST);
        }

        @Override
        protected void compute() {
            if (END_INDEX - START_INDEX > THRESHOLD) {
                int middle = (START_INDEX + END_INDEX) / 2;
                invokeAll(new ArrayInitialize(START_INDEX, middle, LIST),
                        new ArrayInitialize(middle, END_INDEX, LIST));
            } else {
                Random random = new Random();
                for (int i = START_INDEX; i < END_INDEX; i++)
                    LIST[i] = random.nextDouble();
            }
        }
    }
}
