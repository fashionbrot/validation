package com.github.fashionbrot.annotation;

import java.lang.annotation.*;

/**
 * BigDecimal
 * BigInteger
 * byte
 * short
 * int
 * long
 * float
 * double
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {//issue#1

    long min() default 0L;

    long max() default Integer.MIN_VALUE;

    String msg() default "validated.Range.msg";

    /**
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
