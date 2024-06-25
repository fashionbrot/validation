package com.github.fashionbrot;

public class ComparisonPerformanceTest {

    public static void main(String[] args) {
        // Number of iterations for averaging
        int iterations = 100000;
        Object value = "test"; // Simulating a String object
        Class<?> valueType = value.getClass();

        // Test valueType == String.class
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {

            if (valueType == String.class) {
                // Do something
            }
        }
        long endTime = System.nanoTime();
        long duration1 = (endTime - startTime) / iterations;
        System.out.println("valueType == String.class Average Time: " + duration1 + " nanoseconds");

        // Test value instanceof CharSequence
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            if (value instanceof CharSequence) {
                // Do something
            }
        }
        endTime = System.nanoTime();
        long duration2 = (endTime - startTime) / iterations;
        System.out.println("value instanceof CharSequence Average Time: " + duration2 + " nanoseconds");
    }



}
