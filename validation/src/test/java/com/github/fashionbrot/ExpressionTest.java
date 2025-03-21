package com.github.fashionbrot;

import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.NotNull;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.annotation.ValidatedName;
import com.github.fashionbrot.constraint.Violation;
import com.github.fashionbrot.exception.ValidatedException;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author fashionbrot
 */
public class ExpressionTest {

    public class TestController1{
        @Validated(failFast = false)
        private void test(@NotNull Integer type,
                          @NotEmpty(expression = "arg0!=null and arg0==1") String username){
        }
    }

    @Test
    public void test1(){
        Object[] objects = {1,null};
        ValidatedException validatedException = MethodUtil.getNewException(ExpressionTest.TestController1.class, "test", objects);
        if (validatedException!=null){
            List<Violation> violations = validatedException.getViolations();
            Assert.assertEquals("arg1",violations.get(0).getFieldName());
        }
    }


    @Data
    @ValidatedName("arg0")
    public class ExpressionEntity{
        //type==1 验证码登录  type==2 密码登录
        private Integer type;
        @NotEmpty(expression = "arg0.type!=null and arg0.type==1")
        private String verificationCode;
        @NotEmpty(expression = "arg0.type!=null and arg0.type==2")
        private String password;
    }

    public class TestController2{
        @Validated(failFast = false)
        private void test(ExpressionEntity expression){

        }
    }

    @Test
    public void test2(){
        ExpressionEntity entity=new ExpressionEntity();
        entity.setType(1);

        ValidatedException validatedException = MethodUtil.getNewException(ExpressionTest.TestController2.class, "test", new Object[]{entity});
        System.out.println(validatedException.toString());
        Assert.assertEquals(validatedException.getViolations().get(0).getFieldName(),"verificationCode");

    }



}
