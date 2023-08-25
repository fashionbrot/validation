package com.github.fashionbrot;

import com.github.fashionbrot.common.util.JavaUtil;
import org.junit.Test;

public class JavaUtilTest {

    @Test
    public void test1(){
        boolean primitive = JavaUtil.isPrimitive(Void.class);
        System.out.println(primitive);
    }

    @Test
    public void test2(){
        for (int i = 0; i < 20; i++) {
            if (i==5){
                continue;
            }
            System.out.println(i);
        }
    }
}
