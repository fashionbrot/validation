package com.github.fashionbrot;

/**
 * @author fashionbrot
 */
public class StringUtilsTest {
    public static void main(String[] args) {
        String testString = "ThisIsATestStringWith24Chars";

        long startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            captureNameMethod1(testString);
        }
        long endTime = System.nanoTime();
        System.out.println("Method 1 Time: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            captureNameMethod2(testString);
        }
        endTime = System.nanoTime();
        System.out.println("Method 2 Time: " + (endTime - startTime) + " ns");
    }

    public static String captureNameMethod1(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public static String captureNameMethod2(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        char[] cs = name.toCharArray();
        cs[0] = Character.toLowerCase(cs[0]);
        return new String(cs);
    }
}

