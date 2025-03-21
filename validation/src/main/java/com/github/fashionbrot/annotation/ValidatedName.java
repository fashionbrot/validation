package com.github.fashionbrot.annotation;




import java.lang.annotation.*;


@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatedName {
    String value();
}
