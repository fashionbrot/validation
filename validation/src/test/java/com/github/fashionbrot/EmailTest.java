package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Email;
import com.github.fashionbrot.annotation.Validated;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author fashionbrot
 */
public class EmailTest {

    public class Controller1{

        @Validated(failFast = false)
        public void test(@Email(message =  "邮箱格式不正确1")String b1,
                         @Email(message =  "邮箱格式不正确2",skipEmpty = false)String b2,
                         @Email(message =  "邮箱格式不正确3",skipEmpty = false)String b3){

        }
    }

    @Test
    public void test1(){
        String returnResult="邮箱格式不正确1,邮箱格式不正确3";
        String test = MethodUtil.getMessage(Controller1.class, "test", new Object[]{"","fashionbrot@163.com",null});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }



}
