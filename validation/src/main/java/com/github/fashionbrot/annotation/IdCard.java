package com.github.fashionbrot.annotation;



import java.lang.annotation.*;

/**
 * 验证身份证
 * String 类型
 */
@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdCard {

    String regexp() default "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])|([1-9]\\d5\\d2((0[1−9])|(10|11|12))(([0−2][1−9])|10|20|30|31)\\d2[0−9Xx])";

    String msg() default "validated.IdCard.msg";

    /**
     * 判断是否是18岁以下
     * @return boolean
     */
    boolean below18() default  false;

    String below18Msg() default "validated.IdCard.below18.msg";

    /**
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};

}
