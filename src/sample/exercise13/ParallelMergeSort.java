package sample.exercise13;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort {
    public static void main(String[] args) {
        Integer[] a = {3, 7, 5, 1, 0, 2, 4, 8, 3};
        parallelMergeSort(a);
        System.out.println(Arrays.toString(a));
    }

    public static <E extends Comparable<E>> void
    parallelMergeSort(E[] list) {
        RecursiveAction mainTask = new SortTask<>(list);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }

    private static class SortTask<E extends Comparable<E>> extends RecursiveAction {
        private final static int THRESHOLD = 500;
        private final E[] LIST;
        private final int START;
        private final int END;

        SortTask(E[] LIST) {
            this(0, LIST.length, LIST);
        }

        public SortTask(int START, int END, E[] LIST) {
            this.LIST = LIST;
            this.START = START;
            this.END = END;
        }

        @Override
        protected void compute() {
            if (END - START < THRESHOLD)
                java.util.Arrays.sort(LIST, START, END);
            else {
                int medium = (START + END) / 2;
                // Recursively sort the two halves
                new SortTask<>(START, medium, LIST).compute();
                new SortTask<>(medium, END, LIST).compute();
                // Merge firstHalf with secondHalf into list
                merge(START, medium, END);
            }
        }

        @SuppressWarnings("unchecked")
        private void merge(int startIndex, int mediumIndex, int endIndex) {
            int sizeOfFirstHalf = mediumIndex - startIndex;
            Object[] firstHalf = new Object[sizeOfFirstHalf];
            System.arraycopy(LIST, startIndex, firstHalf, 0, mediumIndex - startIndex);
            int firstHalfCounter = 0;
            int secondHalfCounter = mediumIndex;
            int counter = startIndex;
            while (firstHalfCounter != sizeOfFirstHalf && secondHalfCounter != endIndex) {
                if (LIST[secondHalfCounter].compareTo((E) firstHalf[firstHalfCounter]) > 0)
                    LIST[counter++] = (E) firstHalf[firstHalfCounter++];
                else
                    LIST[counter++] = LIST[secondHalfCounter++];
            }

            while (firstHalfCounter != sizeOfFirstHalf) {
                LIST[counter++] = (E) firstHalf[firstHalfCounter++];
            }

            while (secondHalfCounter != endIndex) {
                LIST[counter++] = LIST[secondHalfCounter++];
            }
        }
    }
}