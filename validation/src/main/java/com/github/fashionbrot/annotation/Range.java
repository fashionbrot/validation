package com.github.fashionbrot.annotation;

import java.lang.annotation.*;

/**
 * BigDecimal
 * BigInteger
 * Byte
 * Short
 * Integer
 * Long
 * Float
 * Double
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {//issue#1

    long min() default 0L;

    long max() default Integer.MIN_VALUE;

    String msg() default "validated.Range.msg";

    /**
     * 是否跳过空值
     * @return boolean
     */
    boolean skipEmpty() default true;

    /**
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
