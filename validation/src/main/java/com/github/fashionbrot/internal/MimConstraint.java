package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Min;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author fashionbrot
 */
public class MimConstraint implements ConstraintValidator<Min, Object> {

    @Override
    public boolean isValid(Min annotation, Object value, Class<?> valueType) {
        if (value == null) {
            return annotation.skipEmpty();
        }
        if (ObjectUtil.isEmpty(annotation.value())) {
            return true;
        }

        BigDecimal max = new BigDecimal(annotation.value());
        if (ClassUtil.isBigDecimal(valueType)) {
            return max.compareTo((BigDecimal) value) < 1;
        } else if (ClassUtil.isBigInteger(valueType)) {
            BigInteger bigInteger = (BigInteger) value;
            return max.toBigInteger().longValue() <= bigInteger.longValue();
        } else if (ClassUtil.isShort(valueType)) {
            Short obj = (Short) value;
            return max.shortValue() <= obj.shortValue();
        } else if (ClassUtil.isInt(valueType)) {
            Integer obj = (Integer) value;
            return max.intValue() <= obj.intValue();
        } else if (ClassUtil.isLong(valueType)) {
            Long obj = (Long) value;
            return max.longValue() <= obj.longValue();
        } else if (ClassUtil.isFloat(valueType)) {
            Float obj = (Float) value;
            return max.floatValue() <= obj.floatValue();
        } else if (ClassUtil.isDouble(valueType)) {
            Double obj = (Double) value;
            return max.doubleValue() <= obj.doubleValue();
        }

        return true;
    }
}
