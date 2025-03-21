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

    /**
     * 最小值
     * @return long
     */
    long min() default 0L;

    /**
     * 最大值
     * @return long
     */
    long max() default Integer.MIN_VALUE;

    /**
     * 验证失败返回信息
     * @return String
     */
    String message() default "${validated.Range.message}";

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

    /**
     * ognl expression
     * @return String
     */
    String expression() default "";
}
