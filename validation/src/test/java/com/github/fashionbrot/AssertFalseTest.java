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
        public void test(@AssertFalse(msg = "参数错误")Boolean b1,
                         @AssertFalse(msg = "参数错误")Boolean b2){

        }
    }

    @Test
    public void test1(){
        String returnResult="";
        String test = MethodUtil.getMsg(Controller1.class, "test", new Object[]{null,null});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }

    public class Controller2{
        @Validated
        public void test(@AssertFalse(msg = "参数错误1",skipEmpty = false)Boolean b1,
                         @AssertFalse(msg = "参数错误2")Boolean b2){

        }
    }

    @Test
    public void test2(){
        String returnResult="ValidatedException(fieldName=arg0, msg=参数错误1, annotationName=AssertFalse, value=null, valueIndex=0, violations=null)";
        String test = MethodUtil.getMsg(Controller2.class, "test", new Object[]{null,null});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }


    public class Controller3{
        @Validated(failFast = false)
        public void test(@AssertFalse(msg = "参数错误1",skipEmpty = false)Boolean b1,
                         @AssertFalse(msg = "参数错误2")Boolean b2){
        }
    }

    @Test
    public void test3(){
        String returnResult="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[Violation(fieldName=arg0, msg=参数错误1, annotationName=AssertFalse, value=null, valueIndex=0)])";
        String test = MethodUtil.getMsg(Controller3.class, "test", new Object[]{null,null});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }


    public class Controller4{
        @Validated(failFast = false)
        public void test(@AssertFalse(msg = "参数错误1",skipEmpty = false)Boolean b1,
                         @AssertFalse(msg = "参数错误2")Boolean b2){
        }
    }

    @Test
    public void test4(){
        String returnResult="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[Violation(fieldName=arg0, msg=参数错误1, annotationName=AssertFalse, value=true, valueIndex=0)])";
        String test = MethodUtil.getMsg(Controller4.class, "test", new Object[]{true,"false"});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }

}
