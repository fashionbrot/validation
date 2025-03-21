package com.github.fashionbrot.annotation;



import java.lang.annotation.*;

/**
 * 验证 是否为空
 *
 * Object 类型
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

    /**
     * 验证失败返回信息
     * @return String
     */
    String  message() default "${validated.NotNull.message}";

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
