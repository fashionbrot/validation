package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.NotNull;
import com.github.fashionbrot.constraint.ConstraintValidator;

public class NotNullConstraint implements ConstraintValidator<NotNull,Object> {


    @Override
    public boolean isValid(NotNull annotation, Object object,Class<?> valueType) {
        return object != null;
    }

}
