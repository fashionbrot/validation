package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Range;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.exception.ValidatedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class RangeTest {

    public class TestController1{
        @Validated(failFast = false)
        private void test(@Range(min = 1,max = 10,message =  "应当在${min}~${max}之间") BigDecimal value1,
                          @Range(min = 1,max = 10,message =  "应当在${min}~${max}之间") BigDecimal value2,
                          @Range(min = 1,max = 10,message =  "应当在${min}~${max}之间") BigDecimal value3){

        }
    }

    @Test
    public void test1(){
        String result="应当在1~10之间";
        String returnResult=MethodUtil.getMessage(RangeTest.TestController1.class,"test",new Object[]{BigDecimal.ZERO,BigDecimal.ONE,new BigDecimal("10")});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

    public class TestController2{
        @Validated(failFast = false)
        private void test(@Range(min = 1,max = 10,message =  "1应当在${min}~${max}之间") BigInteger value1,
                          @Range(min = 1,max = 10,message =  "2应当在${min}~${max}之间") BigInteger value2,
                          @Range(min = 1,max = 10,message =  "3应当在${min}~${max}之间") BigInteger value3){

        }
    }

    @Test
    public void test2(){
        String result="1应当在1~10之间,3应当在1~10之间";
        String returnResult=MethodUtil.getMessage(RangeTest.TestController2.class,"test",new Object[]{BigInteger.ZERO,BigInteger.ONE,new BigInteger("11")});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController3{
        @Validated(failFast = false)
        private void test(@Range(min = 1,max = 10,message =  "1应当在${min}~${max}之间") Byte value1,
                          @Range(min = 1,max = 10,message =  "应当在${min}~${max}之间") Byte value2,
                          @Range(min = 1,max = 10,message =  "3应当在${min}~${max}之间") Byte value3){

        }
    }

    @Test
    public void test3(){
        String result="1应当在1~10之间,3应当在1~10之间";
        String returnResult=MethodUtil.getMessage(RangeTest.TestController3.class,"test",new Object[]{(byte)0,new Byte("1"),new Byte("11")});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    public class TestController5{
        @Validated(failFast = false)
        private void test(@Range(min = 1,max = 10,message =  "1应当在${min}~${max}之间") int value1,
                          @Range(min = 1,max = 10,message =  "应当在${min}~${max}之间") Integer value2,
                          @Range(min = 1,max = 10,message =  "3应当在${min}~${max}之间") Integer value3){

        }
    }

    @Test
    public void test5(){
        String result="1应当在1~10之间,3应当在1~10之间";
        String returnResult=MethodUtil.getMessage(RangeTest.TestController5.class,"test",new Object[]{0,new Integer("1"),new Integer("11")});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }




}
