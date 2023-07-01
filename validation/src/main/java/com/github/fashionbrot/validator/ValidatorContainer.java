package com.github.fashionbrot.validator;

import com.github.fashionbrot.constraint.ConstraintValidator;

import java.lang.annotation.Annotation;

/**
 * @author fashionbrot
 */
public interface ValidatorContainer {


    ConstraintValidator injectContainer(Class<? extends ConstraintValidator<? extends Annotation, ?>> clazz);

}
