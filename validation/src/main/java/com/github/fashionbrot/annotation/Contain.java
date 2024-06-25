package com.github.fashionbrot.annotation;

import java.lang.annotation.*;

/**
 * 验证参数是否包含
 * CharSequence
 * String
 * BigDecimal
 * BigInteger
 * Short
 * Integer
 * Long
 * Float
 * Double
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

    /**
     * 是否跳过空值
     * @return boolean
     */
    boolean skipEmpty() default false;


    String msg() default "${validated.Contain.msg}";

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
