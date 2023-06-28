package com.github.fashionbrot.annotation;


import java.lang.annotation.*;


/**
 * 验证 是否为数字
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Digits {


    String msg() default "validated.Digits.msg";

    /**
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
