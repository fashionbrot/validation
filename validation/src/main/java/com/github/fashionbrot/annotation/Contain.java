package com.github.fashionbrot.annotation;

import java.lang.annotation.*;

/**
 * CharSequence
 * String
 * BigDecimal
 * BigInteger
 * short
 * int
 * long
 * float
 * double
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Contain { //issue#2


    String[] value();

    /**
     * 是否忽略大小写
     * @return boolean
     */
    boolean ignoreCase() default true;

    boolean notEmpty() default false;

    String msg() default "validated.Contain.msg";

    /**
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};

}
