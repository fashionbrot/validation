package com.github.fashionbrot.annotation;



import java.lang.annotation.*;

/**
 * 验证数量大小范围
 * Collection
 * Map
 * Array
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {

    /**
     * 最小数量
     * @return long
     */
    long min() default 0;
    /**
     * 最大数量
     * @return long
     */
    long max() default Long.MAX_VALUE;

    /**
     * 是否跳过空值
     * @return boolean
     */
    boolean skipEmpty() default true;

    /**
     * 验证失败返回信息
     * @return String
     */
    String message() default "${validated.Size.message}";

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
