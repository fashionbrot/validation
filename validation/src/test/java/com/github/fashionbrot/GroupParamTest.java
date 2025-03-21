package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Digits;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.groups.DefaultGroup;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author fashionbrot
 */
public class GroupParamTest {

    public class Insert{
    }
    public class Update{
    }


    public class TestController1{
        @Validated(failFast = false,groups = {Insert.class})
        private void test1(@Digits(message =  "d1不是数字",groups = {Insert.class}) String d1,
                           @Digits(message =  "d2不是数字",groups = {}) String d2){

        }
    }

    @Test
    public void test1(){

        Object[] params = new Object[]{"","a"};
        String result="";
        String returnResult=MethodUtil.getMessage(GroupParamTest.TestController1.class,"test1",params);

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    public class TestController2{
        @Validated(failFast = false)
        private void test1(@Digits(message =  "d1不是数字",groups = {Insert.class}) String d1,
                           @Digits(message =  "d2不是数字",groups = {}) String d2){

        }
    }

    @Test
    public void test2(){

        String result="d2不是数字";
        Object[] params = new Object[]{"","a"};
        String returnResult=MethodUtil.getMessage(GroupParamTest.TestController2.class,"test1",params);

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    public class TestController3{
        @Validated(failFast = false,groups = {Insert.class,DefaultGroup.class})
        private void test1(@Digits(message =  "d1不是数字",groups = {Insert.class}) String d1,
                           @Digits(message =  "d2不是数字",groups = {}) String d2){

        }
    }

    @Test
    public void test3(){
        String result="";
        Object[] params = new Object[]{"",""};
        String returnResult=MethodUtil.getMessage(GroupParamTest.TestController3.class,"test1",params);

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }




    public class TestController4{
        @Validated(failFast = false,groups = {DefaultGroup.class})
        private void test1(@Digits(message =  "d1不是数字",groups = {Insert.class}) String d1,
                           @Digits(message =  "d2不是数字",groups = {}) String d2){

        }
    }

    @Test
    public void test4(){
        String result="";
        Object[] params = new Object[]{"","1.12"};

        String returnResult=MethodUtil.getMessage(GroupParamTest.TestController4.class,"test1",params);

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


}
