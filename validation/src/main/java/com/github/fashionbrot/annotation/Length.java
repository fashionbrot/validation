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

    /**
     * 最小长度
     * @return int
     */
    int min() default 0;

    /**
     * 最大长度
     * @return int
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 验证失败返回信息
     * @return String
     */
    String message() default "${validated.Length.message}";

    /**
     * 是否跳过空值
     * @return boolean
     */
    boolean skipEmpty() default false;
    /**
     * @see com.github.fashionbrot.groups.DefaultGroup default
     * @return groups
     */
    Class<?>[] groups() default  {};

    /**
     * ognl expression
     * @return String
     */
    String expression() default "";
}
