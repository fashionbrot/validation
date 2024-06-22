package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Min;
import com.github.fashionbrot.annotation.Validated;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author fashionbrot
 */
public class MinTest {


    public class Controller1 {

        @Validated(failFast = false)
        public void test(@Min(value = "10") BigDecimal b1,
                         @Min(value = "9") BigInteger b2,
                         @Min(value = "8") Long b3,
                         @Min(value = "7") Integer b4,
                         @Min(value = "6") Short b5,
                         @Min(value = "5") Float b6,
                         @Min(value = "4") Double b7
        ) {

        }
    }

    @Test
    public void test1() {
        String returnResult = "ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[Violation(fieldName=arg5, msg=最小不能小于5, annotationName=Min, value=4.99999, valueIndex=5)])";
        Object[] objects = {new BigDecimal("10"),
            BigInteger.valueOf(9),
            Long.valueOf(8),
            Integer.valueOf(7),
            Short.valueOf((short) 6),
            Float.valueOf("4.99999"),
            Double.valueOf(4)};
        String test = MethodUtil.getMsg(Controller1.class, "test", objects);
        System.out.println(test);
        Assert.assertEquals(test, returnResult);
    }



    public class Controller2 {

        @Validated(failFast = false)
        public void test(@Min(value = "8") long b3,
                         @Min(value = "7") int b4,
                         @Min(value = "6") short b5,
                         @Min(value = "5") float b6,
                         @Min(value = "4") double b7
        ) {
        }
    }

    @Test
    public void test2() {
        String returnResult = "";
        Object[] objects = {8L,7,(short)6,(float)5,(double)4};
        String test = MethodUtil.getMsg(Controller2.class, "test", objects);
        System.out.println(test);
        Assert.assertEquals(test, returnResult);
    }

    public class Controller3 {

        @Validated(failFast = false)
        public void test(@Min(value = "10") BigDecimal b1,
                         @Min(value = "9",skipEmpty = false) BigInteger b2
        ) {
        }
    }

    @Test
    public void test3() {
        String returnResult = "ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[Violation(fieldName=arg1, msg=最小不能小于9, annotationName=Min, value=null, valueIndex=1)])";
        Object[] objects = {null,null};
        String test = MethodUtil.getMsg(Controller3.class, "test", objects);
        System.out.println(test);
        Assert.assertEquals(test, returnResult);
    }

}
