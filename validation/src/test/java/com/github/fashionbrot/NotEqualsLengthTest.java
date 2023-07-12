package com.github.fashionbrot;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.NotEqualLength;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.constraint.MarsViolation;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.util.ObjectUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class NotEqualsLengthTest {

    public class TestController1{
        @Validated(failFast = false)
        private void test(@NotEqualLength(length = 1) Collection collection,
                          @NotEqualLength(length = 1) HashMap map,
                          @NotEqualLength(length = 1) String[] strArray,
                          @NotEqualLength(length = 1,skipEmpty = false) Integer[] integerArray){

        }
    }

    @Test
    public void test1(){
        String returnResult="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg3, msg=长度不等于 1, annotationName=NotEqualLength, value=[Ljava.lang.Integer;@eefb7d1, valueIndex=3)])";
        List collection= new ArrayList();
        collection.add("string");
        HashMap map=new HashMap();
        map.put("test","1");
        Object[] objects = {collection,map,new String[]{},new Integer[]{}};
        ValidatedException validatedException = MethodUtil.getException(TestController1.class, "test",objects);
        if (ObjectUtil.isNotEmpty(validatedException)){
            List<MarsViolation> violations = validatedException.getViolations();
            long count = violations.stream().filter(m -> m.getFieldName().equals("arg3") ).count();
            Assert.assertEquals(count,1);
            System.out.println(JSON.toJSONString(validatedException.getViolations()));
        }
    }

}
