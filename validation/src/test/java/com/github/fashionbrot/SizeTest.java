package com.github.fashionbrot;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.annotation.Size;
import com.github.fashionbrot.annotation.Valid;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.constraint.MarsViolation;
import com.github.fashionbrot.exception.ValidatedException;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class SizeTest {


    public class TestController2 {
        @Validated(failFast = false)
        private void test(@Valid @Size(min = 2, max = 3, msg = "abc1 必须2~3") Integer[] abc1,
                           @Valid @Size(min = 2, max = 3, msg = "abc2 必须2~3") List<Integer> abc2) {
        }
    }


    @Test
    public void test2() {

        Object[] objects = {new Integer[]{Integer.getInteger("1")}, Arrays.asList(Integer.valueOf(2))};
        ValidatedException validatedException = MethodUtil.getException(TestController2.class, "test", objects);
        if (validatedException != null) {
            List<MarsViolation> violations = validatedException.getViolations();
            long count = violations.stream().filter(m -> m.getFieldName().equals("arg0") || m.getFieldName().equals("arg1")).count();
            Assert.assertEquals(count, 2);
            System.out.println(JSON.toJSONString(validatedException.getViolations()));
        }

    }


    public class TestController3 {
        @Validated(failFast = false)
        private void test(@Size(min = 1, max = 10) Collection collection,
                          @Size(min = 1, max = 10) HashMap map,
                          @Size String[] strArray,
                          @Size(min = 1, max = 10, skipEmpty = false) Integer[] integerArray) {

        }
    }

    @Test
    public void test3() {
        List collection = new ArrayList();
        collection.add("string");
        HashMap map = new HashMap();
        map.put("test", "1");
        Object[] objects = {collection, map, null, new Integer[]{}};
        ValidatedException validatedException = MethodUtil.getException(TestController3.class, "test", objects);
        if (validatedException != null) {
            List<MarsViolation> violations = validatedException.getViolations();
            long count = violations.stream().filter(m -> m.getFieldName().equals("arg3")).count();
            Assert.assertEquals(count, 1);
            System.out.println(JSON.toJSONString(validatedException.getViolations()));
        }else{
            Assert.assertEquals(validatedException != null, true);
        }

    }

}
