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
        public void test(@Email(msg = "邮箱格式不正确1")String b1,
                         @Email(msg = "邮箱格式不正确2",skipEmpty = false)String b2,
                         @Email(msg = "邮箱格式不正确3",skipEmpty = false)String b3,
                         @Email(msg = "邮箱格式不正确4",skipEmpty = false)String b4,
                         @Email(msg = "char邮箱格式不正确5")CharSequence b5,
                         @Email(msg = "char邮箱格式不正确6",skipEmpty = false)CharSequence b6,
                         @Email(msg = "char邮箱格式不正确7")CharSequence b7,
                         @Email(msg = "char邮箱格式不正确8")CharSequence b8){

        }
    }

    @Test
    public void test1(){
        String returnResult="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=邮箱格式不正确1, annotationName=Email, value=1a, valueIndex=0), MarsViolation(fieldName=arg1, msg=邮箱格式不正确2, annotationName=Email, value=, valueIndex=1), MarsViolation(fieldName=arg2, msg=邮箱格式不正确3, annotationName=Email, value=null, valueIndex=2), MarsViolation(fieldName=arg4, msg=char邮箱格式不正确5, annotationName=Email, value=1a, valueIndex=4), MarsViolation(fieldName=arg5, msg=char邮箱格式不正确6, annotationName=Email, value=, valueIndex=5)])";
        String test = MethodUtil.getMsg(Controller1.class, "test", new Object[]{"1a","",null,"fashionbrot@163.com","1a","",null,"fashionbrot@163.com"});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }



}
