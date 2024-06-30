package com.github.fashionbrot;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.constraint.Violation;
import com.github.fashionbrot.test.ValidateImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author fashionbrot
 */
public class ValidaionImplTest {


    @Test
    public void test(){
        TestModel demoModel=new TestModel();

        ValidateImpl validate=new ValidateImpl(null,true,"","");

        List<Violation> validate1 = validate.validate(demoModel);
        System.out.println(JSON.toJSONString(validate1));
    }

}
