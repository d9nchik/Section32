package sample.exercise15;

import java.util.Random;
import java.util.stream.DoubleStream;

public class Sum {
    public static void main(String[] args) {
        double[] list = new Random().doubles(9_000_000).toArray();
        long start = System.currentTimeMillis();
        System.out.println("Sum is equal: " + parallelSum(list));
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
    }

    public static double parallelSum(double[] list) {
        return DoubleStream.of(list).parallel().sum();
    }
}
