package com.github.fashionbrot.annotation;




import java.lang.annotation.*;

/**
 * 验证银行卡
 * 支持类型
 * String
 * CharSequence
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BankCard {

    String msg() default "${validated.BankCard.msg}";

    /**
     * @return String
     */
    String regexp() default "^([1-9]{1})(\\d{14}|\\d{18})$";

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
