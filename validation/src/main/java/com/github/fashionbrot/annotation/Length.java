package com.github.fashionbrot.annotation;



import java.lang.annotation.*;

/**
 * 验证 长度
 * String
 * CharSequence
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String msg() default "${validated.Length.msg}";

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
