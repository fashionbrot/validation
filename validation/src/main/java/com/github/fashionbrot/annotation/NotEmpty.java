package com.github.fashionbrot.annotation;



import java.lang.annotation.*;

/**
 * 验证是否为空
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
public @interface NotEmpty {

    String msg() default  "validated.NotEmpty.msg";

    /**
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
