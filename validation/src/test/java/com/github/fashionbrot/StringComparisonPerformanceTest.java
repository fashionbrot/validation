package com.github.fashionbrot;

public class StringComparisonPerformanceTest {

    public static void main(String[] args) {
        int iterations = 1000000;

        long startTime = System.nanoTime();
        testEquals(iterations);
        long endTime = System.nanoTime();
        long durationEquals = endTime - startTime;
        System.out.println("Duration for Class == String.class: " + durationEquals + " nanoseconds");

        startTime = System.nanoTime();
        testIsAssignableFrom(iterations);
        endTime = System.nanoTime();
        long durationIsAssignableFrom = endTime - startTime;
        System.out.println("Duration for String.class.isAssignableFrom(valueType): " + durationIsAssignableFrom + " nanoseconds");
    }

    private static void testEquals(int iterations) {
        for (int i = 0; i < iterations; i++) {
            Class<?> valueType = String.class;
            boolean result = valueType == String.class;
        }
    }

    private static void testIsAssignableFrom(int iterations) {
        for (int i = 0; i < iterations; i++) {
            Class<?> valueType = String.class;
            boolean result = String.class.isAssignableFrom(valueType);
        }
    }

}
