package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Range;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.validator.Validator;
import com.github.fashionbrot.validator.ValidatorImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class RangeTest {

    public class TestController1{
        @Validated(failFast = false)
        private void test(@Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") BigDecimal value1,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") BigDecimal value2,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") BigDecimal value3){

        }
    }

    @Test
    public void test1(){
        Method[] methods = RangeTest.TestController1.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=应当在1~10之间, annotationName=Range, value=0, valueIndex=0)])";
        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{BigDecimal.ZERO,BigDecimal.ONE,new BigDecimal("10")},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

    public class TestController2{
        @Validated(failFast = false)
        private void test(@Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") BigInteger value1,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") BigInteger value2,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") BigInteger value3){

        }
    }

    @Test
    public void test2(){
        Method[] methods = RangeTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=应当在1~10之间, annotationName=Range, value=0, valueIndex=0), MarsViolation(fieldName=arg2, msg=应当在1~10之间, annotationName=Range, value=11, valueIndex=2)])";
        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{BigInteger.ZERO,BigInteger.ONE,new BigInteger("11")},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController3{
        @Validated(failFast = false)
        private void test(@Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") Byte value1,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") Byte value2,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") Byte value3){

        }
    }

    @Test
    public void test3(){
        Method[] methods = RangeTest.TestController3.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=应当在1~10之间, annotationName=Range, value=0, valueIndex=0), MarsViolation(fieldName=arg2, msg=应当在1~10之间, annotationName=Range, value=11, valueIndex=2)])";
        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{(byte)0,new Byte("1"),new Byte("11")},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

    public class TestController4{
        @Validated(failFast = false)
        private void test(@Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") short value1,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") Short value2,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") Short value3){

        }
    }

    @Test
    public void test4(){
        Method[] methods = RangeTest.TestController4.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=应当在1~10之间, annotationName=Range, value=0, valueIndex=0), MarsViolation(fieldName=arg2, msg=应当在1~10之间, annotationName=Range, value=11, valueIndex=2)])";
        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{(short)0,new Short("1"),new Short("11")},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    public class TestController5{
        @Validated(failFast = false)
        private void test(@Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") int value1,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") Integer value2,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") Integer value3){

        }
    }

    @Test
    public void test5(){
        Method[] methods = RangeTest.TestController5.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=应当在1~10之间, annotationName=Range, value=0, valueIndex=0), MarsViolation(fieldName=arg2, msg=应当在1~10之间, annotationName=Range, value=11, valueIndex=2)])";
        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{0,new Integer("1"),new Integer("11")},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController6{
        @Validated(failFast = false)
        private void test(@Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") float value1,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") Float value2,
                          @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间") Float value3){

        }
    }

    @Test
    public void test6(){
        Method[] methods = RangeTest.TestController6.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=应当在1~10之间, annotationName=Range, value=0.0, valueIndex=0), MarsViolation(fieldName=arg2, msg=应当在1~10之间, annotationName=Range, value=11.0, valueIndex=2)])";
        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{(float)0,new Float("1"),new Float("11")},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class RangeRequest{
        @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间")
        private int value1;

        @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间")
        private Float value2;
    }
    public class TestController7{
        @Validated(failFast = false)
        private void test(RangeRequest request){
        }
    }

    @Test
    public void test7(){

        RangeRequest build = new RangeRequest();
        build.setValue1(0);
        build.setValue2(null);

        String returnResult = MethodUtil.getMsg(RangeTest.TestController7.class, "test", new Object[]{build});
        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=value1, msg=应当在1~10之间, annotationName=Range, value=0, valueIndex=0)])";
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class RangeRequest8{
        @Range(min = 1,max = 10,msg = "应当在${min}~${max}之间")
        private int value1;

        @Range(min = 1,max = 10,skipEmpty = false,msg = "应当在${min}~${max}之间")
        private Float value2;
    }
    public class TestController8{
        @Validated(failFast = false)
        private void test(RangeRequest8 request){
        }
    }

    @Test
    public void test8(){

        RangeRequest8 build = new RangeRequest8();

        String returnResult = MethodUtil.getMsg(RangeTest.TestController8.class, "test", new Object[]{build});
        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=value1, msg=应当在1~10之间, annotationName=Range, value=0, valueIndex=0), MarsViolation(fieldName=value2, msg=应当在1~10之间, annotationName=Range, value=null, valueIndex=0)])";
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


}
