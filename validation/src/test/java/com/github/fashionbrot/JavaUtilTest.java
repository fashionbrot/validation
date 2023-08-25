package com.github.fashionbrot;

import com.github.fashionbrot.common.util.JavaUtil;
import org.junit.Test;

public class JavaUtilTest {

    @Test
    public void test1(){
        boolean primitive = JavaUtil.isPrimitive(Void.class);
        System.out.println(primitive);
    }
}
