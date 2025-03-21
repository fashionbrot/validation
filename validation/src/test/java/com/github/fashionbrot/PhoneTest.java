package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Phone;
import com.github.fashionbrot.annotation.Validated;
import org.junit.Assert;
import org.junit.Test;

public class PhoneTest {

    public class Controller1{

        @Validated(failFast = false)
        public void test(@Phone String b1,
                         @Phone CharSequence b2,
                         @Phone(skipEmpty = true)String b3,
                         @Phone CharSequence b4){

        }
    }

    @Test
    public void test1(){
        String returnResult="无效手机号码";
        Object[] objects = {"17600382911", "1760039211", "", "19912341234"};
        String test = MethodUtil.getMessage(Controller1.class, "test",objects );
        System.out.println(test);
        Assert.assertEquals(test,returnResult);
    }

}
