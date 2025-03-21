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

    /**
     * 验证失败返回信息
     * @return String
     */
    String  message() default "${validated.Min.message}";

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
