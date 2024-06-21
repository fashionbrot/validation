package com.github.fashionbrot.annotation;

import java.lang.annotation.*;

/**
 *
 * @author fashionbrot
 * Long
 * Integer
 * Short
 * Float
 * Double
 * BigDecimal
 * BigInteger
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Min {

    String value();

    /**
     * 是否跳过空值
     * @return boolean
     */
    boolean skipEmpty() default true;

    String  msg() default "${validated.Min.msg}";

    /**
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};

}
