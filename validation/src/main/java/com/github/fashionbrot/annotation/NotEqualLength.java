package com.github.fashionbrot.annotation;



import java.lang.annotation.*;

/**
 * 验证是否为 length
 *
 * String
 * CharSequence
 * Collection
 * Map
 * Array
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEqualLength {

    int length();


    /**
     * 验证失败返回信息
     * @return String
     */
    String message() default "${validated.NotEqualLength.message}";
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
