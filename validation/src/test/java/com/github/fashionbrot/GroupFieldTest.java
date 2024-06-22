package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Digits;
import com.github.fashionbrot.annotation.Length;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.groups.DefaultGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author fashionbrot
 */
public class GroupFieldTest {

    public class InsertGroup{
    }

    @Data
    public class GroupRequest{
        @Length(msg = "a1不是对应的长度",min = 2,max = 2,groups = {InsertGroup.class})
        private String a1;

        @Length(msg = "a2不是对应的长度",min = 2,max = 2,groups = {})
        private String a2;
    }

    public class GroupController1{
        @Validated(failFast = false,groups = {InsertGroup.class})
        public void test(GroupRequest request){
        }
    }

    @Test
    public void test1(){
        Method[] methods = GroupFieldTest.GroupController1.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[Violation(fieldName=a1, msg=a1不是对应的长度, annotationName=Length, value=1, valueIndex=0)])";

        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setA1("1");
        groupRequest.setA2("2");
        Object[] params = new Object[]{groupRequest};
        String returnResult=MethodUtil.getMsg(GroupFieldTest.GroupController1.class,"test",params);
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
        private void test(GroupRequest request){

        }
    }

    @Test
    public void test2(){
        Method[] methods = GroupFieldTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[Violation(fieldName=a1, msg=a1不是对应的长度, annotationName=Length, value=1, valueIndex=0), Violation(fieldName=a2, msg=a2不是对应的长度, annotationName=Length, value=2, valueIndex=0)])";
//        String returnResult="";
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setA1("1");
        groupRequest.setA2("2");
        Object[] params = new Object[]{groupRequest};
        String returnResult=MethodUtil.getMsg(GroupFieldTest.TestController2.class,"test",params);

//        try {
//            Validator marsValidator = new ValidatorImpl();
//            marsValidator.validParameter(method,params,null);
//        }catch (ValidatedException e){
//            returnResult = e.toString();
//        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    public class TestController3{
        @Validated(failFast = false,groups = {InsertGroup.class, DefaultGroup.class})
        private void test(GroupRequest request){

        }
    }
    @Test
    public void test3(){
        Method[] methods = GroupFieldTest.TestController3.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[Violation(fieldName=a1, msg=a1不是对应的长度, annotationName=Length, value=1, valueIndex=0), Violation(fieldName=a2, msg=a2不是对应的长度, annotationName=Length, value=2, valueIndex=0)])";
//        String returnResult="";
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setA1("1");
        groupRequest.setA2("2");
        Object[] params = new Object[]{groupRequest};
        String returnResult=MethodUtil.getMsg(GroupFieldTest.TestController3.class,"test",params);
//        try {
//            Validator marsValidator = new ValidatorImpl();
//            marsValidator.validParameter(method,params,null);
//        }catch (ValidatedException e){
//            returnResult = e.toString();
//        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController4{
        @Validated(failFast = false,groups = { DefaultGroup.class})
        private void test(GroupRequest request){

        }
    }
    @Test
    public void test4(){
        Method[] methods = GroupFieldTest.TestController4.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[Violation(fieldName=a2, msg=a2不是对应的长度, annotationName=Length, value=2, valueIndex=0)])";
//        String returnResult="";
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setA1("1");
        groupRequest.setA2("2");
        Object[] params = new Object[]{groupRequest};

        String returnResult=MethodUtil.getMsg(GroupFieldTest.TestController4.class,"test",params);

//        try {
//            Validator marsValidator = new ValidatorImpl();
//            marsValidator.validParameter(method,params,null);
//        }catch (ValidatedException e){
//            returnResult = e.toString();
//        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

}

