package com.github.fashionbrot.annotation;


import java.lang.annotation.*;


/**
 * 验证 是否为数字
 * String
 * CharSequence
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Digits {

    /**
     * 验证失败返回信息
     * @return String
     */
    String message() default "${validated.Digits.message}";

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
