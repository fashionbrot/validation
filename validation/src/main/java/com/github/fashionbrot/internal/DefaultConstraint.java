package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Default;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;


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

        if (ClassUtil.isBigDecimal(valueType)) {
            return new BigDecimal(annotation.value());
        } else if (ClassUtil.isBigInteger(valueType)) {
            return new BigInteger(annotation.value());
        } else if (ClassUtil.isShort(valueType)) {
            return ObjectUtil.parseShort(annotation.value());
        } else if (ClassUtil.isLong(valueType)) {
            return ObjectUtil.parseLong(annotation.value());
        } else if (ClassUtil.isInt(valueType)) {
            return ObjectUtil.parseInteger(annotation.value());
        } else if (ClassUtil.isFloat(valueType)) {
            return ObjectUtil.parseFloat(annotation.value());
        } else if (ClassUtil.isDouble(valueType)) {
            return ObjectUtil.parseDouble(annotation.value());
        } else if (ClassUtil.isString(valueType)) {
            return annotation.value();
        }

        return value;
    }

}
