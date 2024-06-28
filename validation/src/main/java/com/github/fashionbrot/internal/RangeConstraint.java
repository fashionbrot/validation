package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Range;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

public class RangeConstraint implements ConstraintValidator<Range, Object> {


    @Override
    public boolean isValid(Range size, Object value, Class<?> valueType) {
        if (value == null) {
            return size.skipEmpty();
        }
        long min = size.min();
        long max = size.max();

        if (ClassUtil.isBigDecimal(valueType)) {
            BigDecimal bigDecimal = (BigDecimal) value;
            return min <= bigDecimal.longValue() && bigDecimal.longValue() <= max;
        } else if (ClassUtil.isBigInteger(valueType)) {
            BigInteger bigInteger = (BigInteger) value;
            return (min <= bigInteger.longValue() && bigInteger.longValue() <= max);
        } else if (ClassUtil.isByte(valueType)) {
            Byte aByte = (Byte) value;
            return (min <= aByte.longValue() && aByte.longValue() <= max);
        } else if (ClassUtil.isShort(valueType)) {
            Short aShort = (Short) value;
            return (min <= aShort.longValue() && aShort.longValue() <= max);
        } else if (ClassUtil.isInt(valueType)) {
            Integer integer = (Integer) value;
            return (min <= integer.longValue() && integer.longValue() <= max);
        } else if (ClassUtil.isLong(valueType)) {
            Long aLong = (Long) value;
            return (min <= aLong.longValue() && aLong.longValue() <= max);
        } else if (ClassUtil.isFloat(valueType)) {
            Float aFloat = (Float) value;
            return (min <= aFloat.longValue() && aFloat.longValue() <= max);
        } else if (ClassUtil.isDouble(valueType)) {
            Double aDouble = (Double) value;
            return (min <= aDouble.longValue() && aDouble.longValue() <= max);
        }

        return true;
    }


}
