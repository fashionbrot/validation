package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Min;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;

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
        if (valueType == BigDecimal.class) {
            return max.compareTo((BigDecimal) value) < 1;
        } else if (valueType == BigInteger.class) {
            BigInteger bigInteger = (BigInteger) value;
            return max.toBigInteger().longValue() <= bigInteger.longValue();
        } else if (valueType == Short.class || valueType == short.class) {
            Short obj = (Short) value;
            return max.shortValue() <= obj.shortValue();
        } else if (valueType == Integer.class || valueType == int.class) {
            Integer obj = (Integer) value;
            return max.intValue() <= obj.intValue();
        } else if (valueType == Long.class || valueType == long.class) {
            Long obj = (Long) value;
            return max.longValue() <= obj.longValue();
        } else if (valueType == Float.class || valueType == float.class) {
            Float obj = (Float) value;
            return max.floatValue() <= obj.floatValue();
        } else if (valueType == Double.class || valueType == double.class) {
            Double obj = (Double) value;
            return max.doubleValue() <= obj.doubleValue();
        }

        return true;
    }
}
