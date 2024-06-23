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
        String result="[{\"id\":1,\"name\":\"张三\"}]";
        Object[] objects = {null};
        MethodUtil.getMsg(Controller1.class, "test",objects );
        String returnResult = JSON.toJSONString(objects);
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }
}
