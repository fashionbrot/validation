package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Max;
import com.github.fashionbrot.annotation.Phone;
import com.github.fashionbrot.annotation.Validated;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author fashionbrot
 */
public class MaxTest {


    public class Controller1 {

        @Validated(failFast = false)
        public void test(@Max(value = "10") BigDecimal b1,
                         @Max(value = "9") BigInteger b2,
                         @Max(value = "8") Long b3,
                         @Max(value = "7") Integer b4,
                         @Max(value = "6") Short b5,
                         @Max(value = "5") Float b6,
                         @Max(value = "4") Double b7
        ) {

        }
    }

    @Test
    public void test1() {
        String returnResult = "最大不能超过5";
        Object[] objects = {new BigDecimal("10"),
            BigInteger.valueOf(9),
            Long.valueOf(8),
            Integer.valueOf(7),
            Short.valueOf((short) 6),
            Float.valueOf("5.0001"),
            Double.valueOf(4)};
        String test = MethodUtil.getMessage(Controller1.class, "test", objects);
        System.out.println(test);
        Assert.assertEquals(test, returnResult);
    }



    public class Controller2 {

        @Validated(failFast = false)
        public void test(@Max(value = "8") long b3,
                         @Max(value = "7") int b4,
                         @Max(value = "6") short b5,
                         @Max(value = "5") float b6,
                         @Max(value = "4") double b7
        ) {
        }
    }

    @Test
    public void test2() {
        String returnResult = "";
        Object[] objects = {8L,7,(short)6,(float)5,(double)4};
        String test = MethodUtil.getMessage(Controller2.class, "test", objects);
        System.out.println(test);
        Assert.assertEquals(test, returnResult);
    }

    public class Controller3 {

        @Validated(failFast = false)
        public void test(@Max(value = "10") BigDecimal b1,
                         @Max(value = "9",skipEmpty = false) BigInteger b2
        ) {
        }
    }

    @Test
    public void test3() {
        String returnResult = "最大不能超过9";
        Object[] objects = {null,null};
        String test = MethodUtil.getMessage(Controller3.class, "test", objects);
        System.out.println(test);
        Assert.assertEquals(test, returnResult);
    }

}
