package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Range;
import com.github.fashionbrot.annotation.Size;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ObjectUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

public class RangeConstraint implements ConstraintValidator<Range, Object> {


    @Override
    public boolean isValid(Range size, Object value, Class<?> valueType) {
        if (value == null) {
            return size.skipEmpty();
        }
        long min = size.min();
        long max = size.max();

        if (value instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) value;
            return min <= bigDecimal.longValue() && bigDecimal.longValue() <= max;
        } else if (value instanceof BigInteger) {
            BigInteger bigInteger = (BigInteger) value;
            return (min <= bigInteger.longValue() && bigInteger.longValue() <= max);
        } else if (Byte.class == valueType || byte.class ==valueType) {
            Byte aByte = (Byte) value;
            return (min <= aByte.longValue() && aByte.longValue() <= max);
        } else if (valueType==Short.class || valueType==short.class) {
            Short aShort = (Short) value;
            return (min <= aShort.longValue() && aShort.longValue() <= max);
        } else if (valueType==Integer.class || valueType==int.class) {
            Integer integer = (Integer) value;
            return (min <= integer.longValue() && integer.longValue() <= max);
        } else if (valueType==Long.class || valueType==long.class) {
            Long aLong = (Long) value;
            return (min <= aLong.longValue() && aLong.longValue() <= max);
        } else if (valueType==Float.class || valueType==float.class) {
            Float aFloat = (Float) value;
            return (min <= aFloat.longValue() && aFloat.longValue() <= max);
        } else if (valueType==Double.class || valueType==double.class) {
            Double aDouble = (Double) value;
            return (min <= aDouble.longValue() && aDouble.longValue() <= max);
        }

        return true;
    }


}
