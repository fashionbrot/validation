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

    long min() default 0;

    long max() default Long.MAX_VALUE;

    /**
     * 是否跳过空值
     * @return boolean
     */
    boolean skipEmpty() default true;

    String msg() default "validated.Size.msg";

    /**
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
