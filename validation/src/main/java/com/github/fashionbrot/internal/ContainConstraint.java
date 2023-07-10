package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Contain;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ObjectUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ContainConstraint implements ConstraintValidator<Contain, Object> {


    @Override
    public boolean isValid(Contain contain, Object value, Class<?> valueType) {
        if (value == null ) {
            return contain.skipEmpty();
        }

        String[] values = contain.value();
        boolean ignoreCase = contain.ignoreCase();

        if (valueType == BigDecimal.class) {
            BigDecimal bigDecimal = (BigDecimal) value;
            return checkContain(values,bigDecimal.toPlainString(),contain,ignoreCase);
        } else if (valueType ==  BigInteger.class) {
            BigInteger bigInteger = (BigInteger) value;
            return checkContain(values,bigInteger.toString(),contain,ignoreCase);
        } else if (valueType==Short.class || valueType==short.class) {
            Short aShort = (Short) value;
            return checkContain(values,aShort.toString(),contain,ignoreCase);
        } else if (valueType==Integer.class || valueType==int.class) {
            Integer integer = (Integer) value;
            return checkContain(values,integer.toString(),contain,ignoreCase);
        } else if (valueType==Long.class || valueType==long.class) {
            Long aLong = (Long) value;
            return checkContain(values,aLong.toString(),contain,ignoreCase);
        } else if (valueType==Float.class || valueType==float.class) {
            Float aFloat = (Float) value;
            return checkContain(values,aFloat.toString(),contain,ignoreCase);
        } else if (valueType==Double.class || valueType==double.class) {
            Double aDouble = (Double) value;
            return checkContain(values,aDouble.toString(),contain,ignoreCase);
        }else if (valueType==String.class ){
            return checkContain(values,(String) value,contain,ignoreCase);
        }else if (valueType==CharSequence.class){
            CharSequence charSequence = (CharSequence)value;
            return checkContain(values,charSequence.toString(),contain,ignoreCase);
        }

        return true;
    }

    public boolean checkContain(String[] values,String value,Contain contain,boolean ignoreCase){
        if (ObjectUtil.isEmpty(value)){
            return contain.skipEmpty();
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
