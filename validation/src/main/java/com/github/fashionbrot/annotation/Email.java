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

    String msg() default "${validated.Email.msg}";

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
