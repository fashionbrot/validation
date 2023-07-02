package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Digits;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.groups.DefaultGroup;
import com.github.fashionbrot.validator.Validator;
import com.github.fashionbrot.validator.ValidatorImpl;
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
        private void test1(@Digits(msg = "d1不是数字",groups = {Insert.class}) String d1,
                           @Digits(msg = "d2不是数字",groups = {}) String d2){

        }
    }

    @Test
    public void test1(){
        Method[] methods = GroupParamTest.TestController1.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=d1不是数字, annotationName=Digits, value=, valueIndex=0)])";
        String returnResult="";
        Object[] params = new Object[]{"",""};

        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,params,null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    public class TestController2{
        @Validated(failFast = false)
        private void test1(@Digits(msg = "d1不是数字",groups = {Insert.class}) String d1,
                           @Digits(msg = "d2不是数字",groups = {}) String d2){

        }
    }

    @Test
    public void test2(){
        Method[] methods = GroupParamTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg1, msg=d2不是数字, annotationName=Digits, value=, valueIndex=1)])";
        String returnResult="";
        Object[] params = new Object[]{"",""};

        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,params,null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    public class TestController3{
        @Validated(failFast = false,groups = {Insert.class,DefaultGroup.class})
        private void test1(@Digits(msg = "d1不是数字",groups = {Insert.class}) String d1,
                           @Digits(msg = "d2不是数字",groups = {}) String d2){

        }
    }

    @Test
    public void test3(){
        Method[] methods = GroupParamTest.TestController3.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=d1不是数字, annotationName=Digits, value=, valueIndex=0), MarsViolation(fieldName=arg1, msg=d2不是数字, annotationName=Digits, value=, valueIndex=1)])";
        String returnResult="";
        Object[] params = new Object[]{"",""};

        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,params,null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


}
