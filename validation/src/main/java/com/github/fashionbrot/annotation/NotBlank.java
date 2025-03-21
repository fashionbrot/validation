package com.github.fashionbrot.annotation;




import java.lang.annotation.*;

/**
 * 验证 字符串是否为空
 *
 * String
 * CharSequence
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {

    /**
     * 验证失败返回信息
     * @return String
     */
    String  message() default "${validated.NotBlank.message}";

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
