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
        @Length(message =  "a1不是对应的长度",min = 2,max = 2,groups = {InsertGroup.class})
        private String a1;

        @Length(message =  "a2不是对应的长度",min = 2,max = 2,groups = {})
        private String a2;
    }

    public class GroupController1{
        @Validated(failFast = false,groups = {InsertGroup.class})
        public void test(GroupRequest request){
        }
    }

    @Test
    public void test1(){
        String result="a1不是对应的长度";

        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setA1("1");
        groupRequest.setA2("2");
        Object[] params = new Object[]{groupRequest};
        String returnResult=MethodUtil.getMessage(GroupFieldTest.GroupController1.class,"test",params);
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

        String result="a1不是对应的长度,a2不是对应的长度";
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setA1("1");
        groupRequest.setA2("2");
        Object[] params = new Object[]{groupRequest};
        String returnResult=MethodUtil.getMessage(GroupFieldTest.TestController2.class,"test",params);
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
        String result="a1不是对应的长度,a2不是对应的长度";
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setA1("1");
        groupRequest.setA2("2");
        Object[] params = new Object[]{groupRequest};
        String returnResult=MethodUtil.getMessage(GroupFieldTest.TestController3.class,"test",params);

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

        String result="a2不是对应的长度";
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setA1("1");
        groupRequest.setA2("2");
        Object[] params = new Object[]{groupRequest};

        String returnResult=MethodUtil.getMessage(GroupFieldTest.TestController4.class,"test",params);
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

}

