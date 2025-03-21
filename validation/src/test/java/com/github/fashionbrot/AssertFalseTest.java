package com.github.fashionbrot;

import com.github.fashionbrot.annotation.AssertFalse;
import com.github.fashionbrot.annotation.AssertTrue;
import com.github.fashionbrot.annotation.Validated;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author fashionbrot
 */
public class AssertFalseTest {


    public class Controller1{

        @Validated
        public void test(@AssertFalse(message =  "参数错误")Boolean b1,
                         @AssertFalse(message =  "参数错误")Boolean b2){

        }
    }

    @Test
    public void test1(){
        String returnResult="参数错误";
        String test = MethodUtil.getMessage(Controller1.class, "test", new Object[]{null,null});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }

    public class Controller2{
        @Validated
        public void test(@AssertFalse(message =  "参数错误1")Boolean b1,
                         @AssertFalse(message =  "参数错误2")Boolean b2){

        }
    }

    @Test
    public void test2(){
        String returnResult="参数错误1";
        String test = MethodUtil.getMessage(Controller2.class, "test", new Object[]{true,null});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }


    public class Controller3{
        @Validated(failFast = false)
        public void test(@AssertFalse(message =  "参数错误1")Boolean b1,
                         @AssertFalse(message =  "参数错误2")Boolean b2){
        }
    }

    @Test
    public void test3(){
        String returnResult="参数错误1,参数错误2";
        String test = MethodUtil.getMessage(Controller3.class, "test", new Object[]{null,null});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }


    public class Controller4{
        @Validated(failFast = false)
        public void test(@AssertFalse(message =  "参数错误1",skipEmpty = false)String b1,
                         @AssertFalse(message =  "参数错误2")String b2){
        }
    }

    @Test
    public void test4(){
        String returnResult="参数错误1";
        String test = MethodUtil.getMessage(Controller4.class, "test", new Object[]{"true","false"});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }

}
