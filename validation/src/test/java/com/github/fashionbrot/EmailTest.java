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
                         @Email(msg = "邮箱格式不正确3",skipEmpty = false)String b3){

        }
    }

    @Test
    public void test1(){
        String returnResult="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[Violation(fieldName=arg0, msg=邮箱格式不正确1, annotationName=Email, value=, valueIndex=0), Violation(fieldName=arg2, msg=邮箱格式不正确3, annotationName=Email, value=null, valueIndex=2)])";
        String test = MethodUtil.getMsg(Controller1.class, "test", new Object[]{"","fashionbrot@163.com",null});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }



}
