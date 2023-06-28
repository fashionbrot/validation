package com.github.fashionbrot.annotation;


import java.lang.annotation.*;

/**
 * 是否设置默认值
 *
 * Integer
 * Double
 * Long
 * Short
 * Float
 * BigDecimal
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {

    String  value() ;

    /**
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
