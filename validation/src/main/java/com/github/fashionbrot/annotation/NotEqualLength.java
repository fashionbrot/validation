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


    String msg() default "${validated.NotEqualLength.msg}";
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
}
