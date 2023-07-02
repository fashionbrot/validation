package com.github.fashionbrot.annotation;



import java.lang.annotation.*;


@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEqualLength {

    int length() default 0;

    String msg() default "validated.NotEqualLength.msg";

    /**
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
