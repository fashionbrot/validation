package com.github.fashionbrot.annotation;




import java.lang.annotation.*;

/**
 * 验证是否是手机号
 * String 类型
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {


    String regexp() default "^1(3([0-35-9]\\d|4[1-8])|4[14-9]\\d|5([0125689]\\d|7[1-79])|66\\d|7[2-35-8]\\d|8\\d{2}|9[89]\\d)\\d{7}$";

    /**
     * 验证失败返回信息
     * @return String
     */
    String message() default "${validated.Phone.message}";

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
