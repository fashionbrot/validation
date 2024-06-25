package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Default;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;


import java.math.BigDecimal;
import java.math.BigInteger;

public class DefaultConstraint implements ConstraintValidator<Default, Object> {

    @Override
    public boolean isValid(Default annotation, Object var1, Class<?> valueType) {
        return true;
    }


    @Override
    public Object modify(Default annotation, Object value, Class<?> valueType) {
        if (ObjectUtil.isEmpty(annotation.value())) {
            return value;
        }

        String strValue = (String) value;
        if (ObjectUtil.isNotEmpty(strValue)) {
            return value;
        }

        if (value instanceof BigDecimal) {
            return new BigDecimal(annotation.value());
        } else if (value instanceof BigInteger) {
            return new BigInteger(annotation.value());
        } else if (value instanceof Short) {
            return ObjectUtil.parseShort(annotation.value());
        } else if (value instanceof Long) {
            return ObjectUtil.parseLong(annotation.value());
        } else if (value instanceof Integer) {
            return ObjectUtil.parseInteger(annotation.value());
        } else if (value instanceof Float) {
            return ObjectUtil.parseFloat(annotation.value());
        } else if (value instanceof Double) {
            return ObjectUtil.parseDouble(annotation.value());
        } else if (value instanceof CharSequence) {
            return annotation.value();
        }

        return null;
    }

}
