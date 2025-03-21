package com.github.fashionbrot;

import com.github.fashionbrot.annotation.IdCard;
import com.github.fashionbrot.annotation.Length;
import com.github.fashionbrot.annotation.Validated;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author fashionbrot
 */
public class LengthTest {

    public class Controller1{

        @Validated(failFast = false)
        public void test(@Length(min = 1,max = 2)String b1,
                         @Length(min = 2,max = 10)CharSequence b2,
                         @Length(min = 1,max = 2)String b3,
                         @Length(min = 2,max = 10,skipEmpty = true)CharSequence b4){

        }
    }

    @Test
    public void test1(){
        String returnResult="长度需要在 1 和 2 之间,长度需要在 2 和 10 之间,长度需要在 1 和 2 之间";
        String test = MethodUtil.getMessage(Controller1.class, "test", new Object[]{"110101200507123071","513701930509101","",null});
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }

}
