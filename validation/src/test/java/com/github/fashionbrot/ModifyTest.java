package com.github.fashionbrot;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.annotation.CustomAnnotation;
import com.github.fashionbrot.annotation.Validated;
import org.junit.Assert;
import org.junit.Test;

public class ModifyTest {

    public class Controller1{

        @Validated(failFast = false)
        public void test(@CustomAnnotation(defaultValue = "{\"id\":1,\"name\":\"张三\"}",modify = true) TestEntity test){

        }
    }

    @Test
    public void test1(){
        String returnResult="[{\"id\":1,\"name\":\"张三\"}]";
        Object[] objects = {null};
        Object object = MethodUtil.getValue(Controller1.class, "test",objects );
        String test = JSON.toJSONString(object);
        System.out.println(JSON.toJSONString(object));
        Assert.assertEquals(test,returnResult);
    }
}
