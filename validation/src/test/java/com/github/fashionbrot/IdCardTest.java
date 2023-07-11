package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Email;
import com.github.fashionbrot.annotation.IdCard;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.util.ObjectUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

/**
 * @author fashionbrot
 */
public class IdCardTest {

    public class Controller1{

        @Validated(failFast = false)
        public void test(@IdCard(msg = "身份证格式不正确1")String b1,
                         @IdCard(msg = "身份证格式不正确2")String b2){

        }
    }

    @Test
    public void test1(){
        String returnResult="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg1, msg=身份证格式不正确2, annotationName=IdCard, value=513701930509101, valueIndex=1)])";
        String test = MethodUtil.getMsg(Controller1.class, "test", new Object[]{"110101200507123071","513701930509101"});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }

}
