package com.github.fashionbrot.annotation;




import java.lang.annotation.*;


@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validated {

    /**
     * 校验组 default @see com.github.fashionbrot.groups.DefaultGroup
     * @return Class
     */
    Class<?>[] groups() default {};

    /**
     * true 快速失败
     * @return boolean
     */
    boolean failFast() default true;

    /**
     * 验证返回值 默认false
     * @return boolean
     */
    boolean validReturnValue() default false;
}
