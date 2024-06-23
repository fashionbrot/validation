package com.github.fashionbrot.constraint;

import java.lang.annotation.Annotation;

/**
 * @author fashionbrot
 */
public interface ValidatorContainer {


    ConstraintValidator injectContainer(Class<? extends ConstraintValidator<? extends Annotation, ?>> clazz);

}
