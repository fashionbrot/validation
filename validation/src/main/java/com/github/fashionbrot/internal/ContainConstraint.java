package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Contain;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ContainConstraint implements ConstraintValidator<Contain, Object> {


    @Override
    public boolean isValid(Contain contain, Object value, Class<?> valueType) {
        if (value == null) {
            return contain.skipEmpty();
        }


        String[] values = contain.value();
        boolean ignoreCase = contain.ignoreCase();

        if (ClassUtil.isBigDecimal(valueType)) {
            BigDecimal bigDecimal = (BigDecimal) value;
            return checkContain(values, bigDecimal.toPlainString(), ignoreCase);
        } else if (ClassUtil.isBigInteger(valueType)) {
            BigInteger bigInteger = (BigInteger) value;
            return checkContain(values, bigInteger.toString(), ignoreCase);
        } else if (ClassUtil.isShort(valueType)) {
            Short aShort = (Short) value;
            return checkContain(values, aShort.toString(), ignoreCase);
        } else if (ClassUtil.isInt(valueType)) {
            Integer integer = (Integer) value;
            return checkContain(values, integer.toString(), ignoreCase);
        } else if (ClassUtil.isLong(valueType)) {
            Long aLong = (Long) value;
            return checkContain(values, aLong.toString(), ignoreCase);
        } else if (ClassUtil.isFloat(valueType)) {
            Float aFloat = (Float) value;
            return checkContain(values, aFloat.toString(), ignoreCase);
        } else if (ClassUtil.isDouble(valueType)) {
            Double aDouble = (Double) value;
            return checkContain(values, aDouble.toString(), ignoreCase);
        } else if (ClassUtil.isString(valueType)) {
            String strValue = (String) value;
            if (ObjectUtil.isEmpty(strValue)) {
                return contain.skipEmpty();
            }
            return checkContain(values, strValue, ignoreCase);
        }

        return true;
    }

    public boolean checkContain(String[] values, String value, boolean ignoreCase) {

        for (int i = 0; i < values.length; i++) {
            if (ignoreCase && values[i].equalsIgnoreCase(value)) {
                return true;
            } else if (!ignoreCase && values[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        BigDecimal b = null;
        System.out.println(b instanceof BigDecimal);
    }


}
