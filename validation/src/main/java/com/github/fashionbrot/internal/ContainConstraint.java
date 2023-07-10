package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Contain;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ObjectUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ContainConstraint implements ConstraintValidator<Contain, Object> {


    @Override
    public boolean isValid(Contain contain, Object value, Class<?> valueType) {
        if (value == null) {
            return true;
        }
        String[] values = contain.value();
        boolean ignoreCase = contain.ignoreCase();

        if (value instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) value;

            return checkContain(values,bigDecimal.toPlainString(),ignoreCase);
        } else if (value instanceof BigInteger) {
            BigInteger bigInteger = (BigInteger) value;
            return checkContain(values,bigInteger.toString(),ignoreCase);
        } else if (valueType==Short.class || valueType==short.class) {
            Short aShort = (Short) value;
            return checkContain(values,aShort.toString(),ignoreCase);
        } else if (valueType==Integer.class || valueType==int.class) {
            Integer integer = (Integer) value;
            return checkContain(values,integer.toString(),ignoreCase);
        } else if (valueType==Long.class || valueType==long.class) {
            Long aLong = (Long) value;
            return checkContain(values,aLong.toString(),ignoreCase);
        } else if (valueType==Float.class || valueType==float.class) {
            Float aFloat = (Float) value;
            return checkContain(values,aFloat.toString(),ignoreCase);
        } else if (valueType==Double.class || valueType==double.class) {
            Double aDouble = (Double) value;
            return checkContain(values,aDouble.toString(),ignoreCase);
        }else if (valueType==String.class ){
            return checkContain(values,(String) value,ignoreCase);
        }else if (valueType==CharSequence.class){
            CharSequence charSequence = (CharSequence)value;
            return checkContain(values,charSequence.toString(),ignoreCase);
        }

        return true;
    }

    public boolean checkContain(String[] values,String value,boolean ignoreCase){
        if (ObjectUtil.isEmpty(value)){
            return true;
        }
        for (int i = 0; i < values.length; i++) {
            if (ignoreCase && values[i].equalsIgnoreCase(value)){
                return true;
            }else if (!ignoreCase && values[i].equals(value)){
                return true;
            }
        }
        return false;
    }


}
