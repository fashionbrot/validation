package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Email;
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
import java.util.Arrays;

/**
 * @author fashionbrot
 */
public class ValidatedFillFastTest {


    public class TestController{
        @Validated(failFast = false)
        private void test1(@Email(msg = "邮箱格式不正确1") String e1,@Email(msg = "邮箱格式不正确2") String e2){

        }
    }

    @Test
    public void test1(){
        Method[] methods = ValidatedFillFastTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=邮箱格式不正确1, annotationName=Email, value=1, valueIndex=0), MarsViolation(fieldName=arg1, msg=邮箱格式不正确2, annotationName=Email, value=2, valueIndex=1)])";
        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{"1","2"},null);
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
        @Email(msg = "邮箱格式不正确1")
        private String e1;

        @Email(msg = "邮箱格式不正确2")
        private String e2;
    }
    public class TestController2{
        @Validated(failFast = false)
        private void test2( ValidatedFillFastTest.Test2Req req){
        }
    }

    @Test
    public void test2(){
        Method[] methods = ValidatedFillFastTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=e1, msg=邮箱格式不正确1, annotationName=Email, value=e1, valueIndex=0), MarsViolation(fieldName=e2, msg=邮箱格式不正确2, annotationName=Email, value=e2, valueIndex=0)])";
        String returnResult="";
        try {
            ValidatedFillFastTest.Test2Req test2Req =new ValidatedFillFastTest.Test2Req();
            test2Req.setE1("e1");
            test2Req.setE2("e2");

            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{test2Req},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


}
