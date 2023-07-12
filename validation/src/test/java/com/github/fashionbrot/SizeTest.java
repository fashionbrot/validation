package com.github.fashionbrot;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.Size;
import com.github.fashionbrot.annotation.Valid;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.constraint.MarsViolation;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.validator.Validator;
import com.github.fashionbrot.validator.ValidatorImpl;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;

public class SizeTest {






    public class TestController2{
        @Validated(failFast = false)
        private void test2(@Valid @Size(min = 2,max = 3,msg = "abc1 必须2~3") Integer[] abc1 ,@Valid @Size(min = 2,max = 3,msg = "abc2 必须2~3")List<Integer> abc2){
        }
    }



    @Test
    public void test2(){
        Method[] methods = SizeTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();
        Integer integer1=Integer.getInteger("1");
        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=abc1 必须2~3, annotationName=Size, value=[Ljava.lang.Integer;@1c37fc4b, valueIndex=0), MarsViolation(fieldName=arg1, msg=abc2 必须2~3, annotationName=Size, value=[2], valueIndex=1)])";
        String returnResult="";
        try {

            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,new Object[]{new Integer[]{integer1},Arrays.asList(Integer.valueOf(2))},null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
//        Assert.assertEquals(result,returnResult);
    }


    public class TestController3{
        @Validated(failFast = false)
        private void test(@Size(min = 1,max = 10) Collection collection,
                          @Size(min = 1,max = 10) HashMap map,
                          @Size String[] strArray,
                          @Size(min = 1,max = 10,skipEmpty = false) Integer[] integerArray){

        }
    }

    @Test
    public void test3(){
        List collection= new ArrayList();
        collection.add("string");
        HashMap map=new HashMap();
        map.put("test","1");
        Object[] objects = {collection,map,new String[]{},new Integer[]{}};
        ValidatedException validatedException = MethodUtil.getException(TestController3.class, "test", objects);
        if (ObjectUtil.isNotEmpty(validatedException)){
            List<MarsViolation> violations = validatedException.getViolations();
            long count = violations.stream().filter(m ->  m.getFieldName().equals("arg3")).count();
            Assert.assertEquals(count,1);
            System.out.println(JSON.toJSONString(validatedException.getViolations()));
        }

    }

}
