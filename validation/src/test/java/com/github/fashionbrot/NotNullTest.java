package com.github.fashionbrot;


import com.github.fashionbrot.annotation.NotNull;
import com.github.fashionbrot.annotation.Valid;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.exception.ValidatedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class NotNullTest {




    public class TestController{
        @Validated
        private void test1(@NotNull(msg = "string 不能为空") String string){

        }
    }

    @Test
    public void test1(){
        String result="ValidatedException(fieldName=arg0, msg=string 不能为空, annotationName=NotNull, value=null, valueIndex=0, violations=null)";
        String returnResult=MethodUtil.getMsg(NotNullTest.TestController.class,"test1",new Object[]{null});

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Test2Req{
        @NotNull(msg = "abc不能为null")
        private String abc;
    }
    public class TestController2{
        @Validated
        private void test2( Test2Req req){
        }
    }

    @Test
    public void test2(){

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc(null);


        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=NotNull, value=null, valueIndex=0, violations=null)";
        String returnResult=MethodUtil.getMsg(NotNullTest.TestController2.class,"test2",new Object[]{test2Req});

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
        Test2Req test2Req =new Test2Req();
        test2Req.setAbc("abc");

        Test2Req test22Req =new Test2Req();
        test22Req.setAbc(null);

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=NotNull, value=null, valueIndex=1, violations=null)";
        String returnResult=MethodUtil.getMsg(NotNullTest.TestController3.class,"test3",new Object[]{Arrays.asList(test2Req,test22Req)});
//        try {
//
//
//            Validator marsValidator = new ValidatorImpl();
//            marsValidator.validParameter(method,new Object[]{Arrays.asList(test2Req,test22Req)},null);
//        }catch (ValidatedException e){
//            returnResult = e.toString();
//        }
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

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc("abc");

        Test2Req test22Req =new Test2Req();
        test22Req.setAbc(null);

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=NotNull, value=null, valueIndex=1, violations=null)";
        String returnResult=MethodUtil.getMsg(NotNullTest.TestController4.class,"test4",new Object[]{new Object[]{test2Req,test22Req}});
//        try {
//
//
//            Validator marsValidator = new ValidatorImpl();
//            marsValidator.validParameter(method,new Object[]{new Object[]{test2Req,test22Req}},null);
//        }catch (ValidatedException e){
//            returnResult = e.toString();
//        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    @Data
    public class TestReq5{

        @Valid
        private List<Test2Req> test2Reqs;
    }
    public class TestController5{
        @Validated
        private void test5(TestReq5 testReq5){

        }
    }

    @Test
    public void test5(){

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc("abc");

        Test2Req test22Req =new Test2Req();
        test22Req.setAbc(null);
        TestReq5 testReq5=new TestReq5();
        testReq5.setTest2Reqs(Arrays.asList(test2Req,test22Req));
        Object[] params =new Object[] {testReq5};


        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=NotNull, value=null, valueIndex=1, violations=null)";
        String returnResult=MethodUtil.getMsg(NotNullTest.TestController5.class,"test5",params);

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    @Data
    public class TestReq6{

        @Valid
        private Test2Req[] test2Reqs;
    }
    public class TestController6{
        @Validated
        private void test6(TestReq6 testReq6){

        }
    }

    @Test
    public void test6(){

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc("abc");

        Test2Req test22Req =new Test2Req();
        test22Req.setAbc(null);

        TestReq6 testReq6=new TestReq6();
        testReq6.setTest2Reqs(new Test2Req[]{test2Req,test22Req});
        Object[] params =new Object[] {testReq6};

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=NotNull, value=null, valueIndex=1, violations=null)";
        String returnResult=MethodUtil.getMsg(NotNullTest.TestController6.class,"test6",params);

        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

}
