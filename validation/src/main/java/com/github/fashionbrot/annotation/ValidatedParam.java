package com.github.fashionbrot.annotation;




import java.lang.annotation.*;


@Documented
@Target({ElementType.TYPE,ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatedParam {
    String value();
}
