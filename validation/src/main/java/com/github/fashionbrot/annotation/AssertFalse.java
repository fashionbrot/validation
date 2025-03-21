package com.github.fashionbrot.annotation;


import java.lang.annotation.*;

/**
 * 验证是否为false
 * boolean
 * String
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertFalse {

    /**
     * 验证失败返回信息
     * @return String
     */
    String message() default "${validated.AssertFalse.message}";

    /**
     * 是否跳过空值
     * @return boolean
     */
    boolean skipEmpty() default false;
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
