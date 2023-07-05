package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Contain;
import com.github.fashionbrot.annotation.Range;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.validator.Validator;
import com.github.fashionbrot.validator.ValidatorImpl;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;

public class ContainTest {


    public class TestController1{
        @Validated(failFast = false)
        private void test(@Contain(value ={"1","2"},msg = "参数不包含1,2") BigDecimal value1,
                          @Contain(value ={"1","2"},msg = "参数不包含1,2") BigDecimal value2){

        }
    }

    @Test
    public void test1(){
        Method[] methods = ContainTest.TestController1.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=参数不包含1,2, annotationName=Contain, value=0, valueIndex=0)])";
        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{BigDecimal.ZERO,BigDecimal.ONE},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

    public class TestController2{
        @Validated(failFast = false)
        private void test(@Contain(value ={"abc","cba"},msg = "参数不包含abc,cba") String value1,
                          @Contain(value ={"abc","cba"},msg = "参数不包含abc,cba") String value2){

        }
    }

    @Test
    public void test2(){
        Method[] methods = ContainTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg1, msg=参数不包含abc,cba, annotationName=Contain, value=cba11, valueIndex=1)])";
        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{"abc","cba11"},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController3{
        @Validated(failFast = false)
        private void test(@Contain(value ={"abc","cba"},msg = "参数不包含abc,cba") CharSequence value1,
                          @Contain(value ={"abc","cba"},msg = "参数不包含abc,cba") CharSequence value2){

        }
    }

    @Test
    public void test3(){
        Method[] methods = ContainTest.TestController3.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=参数不包含abc,cba, annotationName=Contain, value=好风光, valueIndex=0)])";
        String returnResult="";
        try {
            CharSequence value1 = "好风光";
            CharSequence value2 = "cba";
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{value1,value2},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

    public class TestController4{
        @Validated(failFast = false)
        private void test(@Contain(value ={"1","2"},msg = "参数不包含1,2") int value1,
                          @Contain(value ={"1","2"},msg = "参数不包含1,2") Integer value2){

        }
    }

    @Test
    public void test4(){
        Method[] methods = ContainTest.TestController4.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=参数不包含1,2, annotationName=Contain, value=3, valueIndex=0), MarsViolation(fieldName=arg1, msg=参数不包含1,2, annotationName=Contain, value=4, valueIndex=1)])";
        String returnResult="";
        try {

            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{3,4},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController5{
        @Validated(failFast = false)
        private void test(@Contain(value ={"1.123","2121.35"},msg = "参数不包含1.123,2121.35") double value1,
                          @Contain(value ={"1.123","2121.35"},msg = "参数不包含1.123,2121.35") Double value2){

        }
    }

    @Test
    public void test5(){
        double value1=1.123;
        Double value2=12323.3454;

        String returnResult = MethoUtil.getMsg(TestController5.class, "test", new Object[]{value1, value2});
        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg1, msg=参数不包含1.123,2121.35, annotationName=Contain, value=12323.3454, valueIndex=1)])";
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



}
