package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Digits;
import com.github.fashionbrot.annotation.Validated;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author fashionbrot
 */
public class DigitsTest {

    public class Controller1{

        @Validated(failFast = false)
        public void test(@Digits(message =  "参数错误1")String b1,
                         @Digits(message =  "参数错误2")CharSequence b2,
                         @Digits(message =  "参数错误3",skipEmpty = false)String b3,
                         @Digits(message =  "参数错误4",skipEmpty = false)String b4,
                         @Digits(message =  "参数错误5",skipEmpty = false)String b5,
                         @Digits(message =  "参数错误6",skipEmpty = false)String b6){

        }
    }

    @Test
    public void test1(){
        String returnResult="参数错误1,参数错误3,参数错误4";
        String test = MethodUtil.getMessage(Controller1.class, "test", new Object[]{"1a","5107512.8607082342","",null,"-12","-11212.12121"});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }



}
