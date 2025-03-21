package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Contain;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.exception.ValidatedException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;

public class ContainTest {


    public class TestController1{
        @Validated(failFast = false)
        private void test(@Contain(value ={"1","2"},message =  "参数不包含1,2") BigDecimal value1,
                          @Contain(value ={"1","2"},message =  "参数不包含1,2") BigDecimal value2){

        }
    }

    @Test
    public void test1(){
        String result="参数不包含1,2";
        String returnResult=MethodUtil.getMessage(ContainTest.TestController1.class,"test",new Object[]{BigDecimal.ZERO,BigDecimal.ONE});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

    public class TestController2{
        @Validated(failFast = false)
        private void test(@Contain(value ={"abc","cba"},message =  "参数不包含abc,cba") String value1,
                          @Contain(value ={"abc","cba"},message =  "参数不包含abc,cba") String value2){

        }
    }

    @Test
    public void test2(){
        String result="参数不包含abc,cba";
        String returnResult=MethodUtil.getMessage(ContainTest.TestController2.class,"test",new Object[]{"abc","cba11"});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController3{
        @Validated(failFast = false)
        private void test(@Contain(value ={"abc","cba"},message =  "1参数不包含abc,cba") CharSequence value1,
                          @Contain(value ={"abc","cba"},message =  "2参数不包含abc,cba") CharSequence value2){

        }
    }

    @Test
    public void test3(){

        CharSequence value1 = "好风光";
        CharSequence value2 = "cba";

        String result="1参数不包含abc,cba";
        String returnResult= MethodUtil.getMessage(ContainTest.TestController3.class,"test",new Object[]{value1,value2});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

    public class TestController4{
        @Validated(failFast = false)
        private void test(@Contain(value ={"1","2"},message =  "1参数不包含1,2") int value1,
                          @Contain(value ={"1","2"},message =  "2参数不包含1,2") Integer value2){

        }
    }

    @Test
    public void test4(){
        String result="1参数不包含1,2,2参数不包含1,2";
        String returnResult=MethodUtil.getMessage(ContainTest.TestController4.class,"test",new Object[]{3,4});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController5{
        @Validated(failFast = false)
        private void test(@Contain(value ={"1.123","2121.35"},message =  "参数不包含1.123,2121.35") double value1,
                          @Contain(value ={"1.123","2121.35"},message = "参数不包含1.123,2121.35") Double value2){

        }
    }

    @Test
    public void test5(){
        double value1=1.123;
        Double value2=12323.3454;

        String returnResult = MethodUtil.getMessage(TestController5.class, "test", new Object[]{value1, value2});
        String result="参数不包含1.123,2121.35";
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class RangeRequest {
        @Contain(value = {"1.123", "2121.35"}, message = "参数不包含1.123,2121.35")
        double value1;
        @Contain(value = {"1.123", "2121.35"},skipEmpty = false, message = "参数不包含1.123,2121.35")
        Double value2;
    }

    public class TestController7 {
        @Validated(failFast = false)
        private void test(RangeRequest request) {
        }
    }

    @Test
    public void test7(){
        RangeRequest build = new RangeRequest();

        String returnResult = MethodUtil.getMessage(ContainTest.TestController7.class, "test", new Object[]{build});
        String result="参数不包含1.123,2121.35,参数不包含1.123,2121.35";
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }





}
