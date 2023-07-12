package com.github.fashionbrot.annotation;

import com.github.fashionbrot.constraint.Constraint;
import com.github.fashionbrot.constraint.CustomAnnotationConstraintValidator;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomAnnotationConstraintValidator.class})
public @interface CustomAnnotation {

    boolean modify() default true;

    String defaultValue() default "";
}
