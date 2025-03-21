package com.github.fashionbrot.annotation;



import java.lang.annotation.*;


/**
 * 验证 是否是邮箱
 * String
 * CharSequence
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {


    String regexp() default "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 验证失败返回信息
     * @return String
     */
    String message() default "${validated.Email.message}";

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
