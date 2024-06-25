package com.github.fashionbrot;

public class StringConversionPerformanceTest {

    public static void main(String[] args) {
        int iterations = 10000000; // 进行一千万次转换

        // 测试对象数组，包括字符串和其他对象
        Object[] objects = new Object[iterations];
        for (int i = 0; i < iterations; i++) {
            objects[i] = (i % 2 == 0) ? "string" : i;
        }

        // 测试 (String) object
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            if (objects[i] instanceof String) {
                String str = (String) objects[i];
            }
        }
        long endTime = System.nanoTime();
        long castingTime = endTime - startTime;
        System.out.println("Casting to String: " + castingTime + " ns");

        // 测试 object.toString()
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            String str = objects[i].toString();
        }
        endTime = System.nanoTime();
        long toStringTime = endTime - startTime;
        System.out.println("Calling toString: " + toStringTime + " ns");

        // 比较结果
        System.out.println("toString() is " + (toStringTime / (double) castingTime) + " times slower than casting");
    }

}
