package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Email;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.exception.ValidatedException;
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
        private void test(@Email(message =  "邮箱格式不正确1") String e1,@Email(message =  "邮箱格式不正确2") String e2){

        }
    }

    @Test
    public void test1(){

        String result="邮箱格式不正确1,邮箱格式不正确2";
        String returnResult=MethodUtil.getMessage(ValidatedFillFastTest.TestController.class,"test",new Object[]{"1","2"});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }




    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Test2Req{
        @Email(message =  "邮箱格式不正确1")
        private String e1;

        @Email(message =  "邮箱格式不正确2")
        private String e2;
    }
    public class TestController2{
        @Validated(failFast = false)
        private void test( ValidatedFillFastTest.Test2Req req){
        }
    }

    @Test
    public void test2(){

        ValidatedFillFastTest.Test2Req test2Req =new ValidatedFillFastTest.Test2Req();
        test2Req.setE1("e1");
        test2Req.setE2("e2");

        String result="邮箱格式不正确1,邮箱格式不正确2";
        String returnResult=MethodUtil.getMessage(ValidatedFillFastTest.TestController2.class,"test",new Object[]{test2Req});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


}
