package com.github.fashionbrot;

import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.Validated;

/**
 * @author fashionbrot
 */
public class Test {

    public static void main(String[] args) {


    }

    @Validated
    public void  test(@NotEmpty(msg = "abc 不能为空") String abc){

    }
}
