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
        private void test1(@Digits(msg = "d1不是数字",groups = {Insert.class}) String d1,
                           @Digits(msg = "d2不是数字",groups = {}) String d2){

        }
    }

    @Test
    public void test1(){

        Object[] params = new Object[]{"","a"};
        String result="";
        String returnResult=MethodUtil.getMsg(GroupParamTest.TestController1.class,"test1",params);


//        try {
//            Validator marsValidator = new ValidatorImpl();
//            marsValidator.validParameter(method,params,null);
//        }catch (ValidatedException e){
//            returnResult = e.toString();
//        }
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

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[Violation(fieldName=arg1, msg=d2不是数字, annotationName=Digits, value=a, valueIndex=1)])";
        Object[] params = new Object[]{"","a"};
        String returnResult=MethodUtil.getMsg(GroupParamTest.TestController2.class,"test1",params);

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

        String result="";
        Object[] params = new Object[]{"",""};
        String returnResult=MethodUtil.getMsg(GroupParamTest.TestController3.class,"test1",params);

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }




    public class TestController4{
        @Validated(failFast = false,groups = {DefaultGroup.class})
        private void test1(@Digits(msg = "d1不是数字",groups = {Insert.class}) String d1,
                           @Digits(msg = "d2不是数字",groups = {}) String d2){

        }
    }

    @Test
    public void test4(){
        String result="";
        Object[] params = new Object[]{"","1.12"};

        String returnResult=MethodUtil.getMsg(GroupParamTest.TestController4.class,"test1",params);

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


}
