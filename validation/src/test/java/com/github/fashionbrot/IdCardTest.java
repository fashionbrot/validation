package com.github.fashionbrot;

import com.github.fashionbrot.annotation.IdCard;
import com.github.fashionbrot.annotation.Validated;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author fashionbrot
 */
public class IdCardTest {

    public class Controller1{

        @Validated(failFast = false)
        public void test(@IdCard(message =  "身份证格式不正确1")String b1,
                         @IdCard(message =  "身份证格式不正确2")String b2){

        }
    }

    @Test
    public void test1(){
        String returnResult="身份证格式不正确2";
        String test = MethodUtil.getMessage(Controller1.class, "test", new Object[]{"110101200507123071","513701930509101"});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }

}
