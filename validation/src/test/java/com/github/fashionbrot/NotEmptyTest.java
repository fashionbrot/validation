package com.github.fashionbrot;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.Valid;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.constraint.MarsViolation;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.validator.Validator;
import com.github.fashionbrot.validator.ValidatorImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;

public class NotEmptyTest {


    public class TestController{
        @Validated
        private void test1(@NotEmpty(msg = "string 不能为空") String string){

        }
    }

    @Test
    public void test1(){
        Method[] methods = NotEmptyTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        String result="ValidatedException(fieldName=arg0, msg=string 不能为空, annotationName=NotEmpty, value=null, valueIndex=0, violations=null)";
        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{null},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Test2Req{
        @NotEmpty(msg = "abc不能为null")
        private String abc;
    }
    public class TestController2{
        @Validated
        private void test2( Test2Req req){
        }
    }

    @Test
    public void test2(){
        Method[] methods = NotEmptyTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=NotEmpty, value=null, valueIndex=0, violations=null)";
        String returnResult="";
        try {
            Test2Req test2Req =new Test2Req();
            test2Req.setAbc(null);

            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{test2Req},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController3{
        @Validated
        private void test3(@Valid List<Test2Req> req){

        }
    }

    @Test
    public void test3(){
        Method[] methods = NotEmptyTest.TestController3.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test3")).findFirst().get();

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=NotEmpty, value=null, valueIndex=1, violations=null)";
        String returnResult="";
        try {
            Test2Req test2Req =new Test2Req();
            test2Req.setAbc("abc");

            Test2Req test22Req =new Test2Req();
            test22Req.setAbc(null);

            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{Arrays.asList(test2Req,test22Req)},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

    public class TestController4{
        @Validated
        private void test4(@Valid Test2Req[] req){

        }
    }

    @Test
    public void test4(){
        Method[] methods = NotEmptyTest.TestController4.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test4")).findFirst().get();

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=NotEmpty, value=null, valueIndex=1, violations=null)";
        String returnResult="";
        try {
            Test2Req test2Req =new Test2Req();
            test2Req.setAbc("abc");

            Test2Req test22Req =new Test2Req();
            test22Req.setAbc(null);

            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{new Object[]{test2Req,test22Req}},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

    public class TestController5{
        @Validated(failFast = false)
        private void test(@NotEmpty Collection collection,
                          @NotEmpty HashMap map,
                          @NotEmpty String[] strArray,
                          @NotEmpty Integer[] integerArray){

        }
    }

    @Test
    public void test5(){
        List collection= new ArrayList();
        collection.add("string");
        HashMap map=new HashMap();
        map.put("test","1");
        Object[] objects = {collection,map,new String[]{},new Integer[]{}};
        ValidatedException validatedException = MethodUtil.getException(TestController5.class, "test", objects);
        if (validatedException!=null){
            List<MarsViolation> violations = validatedException.getViolations();
            long count = violations.stream().filter(m -> m.getFieldName().equals("arg2") || m.getFieldName().equals("arg3")).count();
            Assert.assertEquals(count,2);
            System.out.println(JSON.toJSONString(validatedException.getViolations()));
        }

    }

}
