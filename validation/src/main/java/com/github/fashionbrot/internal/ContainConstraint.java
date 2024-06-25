package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Contain;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ContainConstraint implements ConstraintValidator<Contain, Object> {


    @Override
    public boolean isValid(Contain contain, Object value, Class<?> valueType) {
        String strValue = ObjectUtil.formatString(value);
        if (ObjectUtil.isEmpty(strValue) && contain.skipEmpty()) {
            return true;
        }

        String[] values = contain.value();
        boolean ignoreCase = contain.ignoreCase();

        if (value instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) value;
            return checkContain(values,bigDecimal.toPlainString(),ignoreCase);
        } else if (value instanceof   BigInteger) {
            BigInteger bigInteger = (BigInteger) value;
            return checkContain(values,bigInteger.toString(),ignoreCase);
        } else if (value instanceof Short) {
            Short aShort = (Short) value;
            return checkContain(values,aShort.toString(),ignoreCase);
        } else if (value instanceof Integer) {
            Integer integer = (Integer) value;
            return checkContain(values,integer.toString(),ignoreCase);
        } else if (value instanceof Long) {
            Long aLong = (Long) value;
            return checkContain(values,aLong.toString(),ignoreCase);
        } else if (value instanceof Float) {
            Float aFloat = (Float) value;
            return checkContain(values,aFloat.toString(),ignoreCase);
        } else if (value instanceof Double) {
            Double aDouble = (Double) value;
            return checkContain(values,aDouble.toString(),ignoreCase);
        }else if (value instanceof CharSequence){
            return checkContain(values,(String) value,ignoreCase);
        }

        return true;
    }

    public boolean checkContain(String[] values,String value,boolean ignoreCase){

        for (int i = 0; i < values.length; i++) {
            if (ignoreCase && values[i].equalsIgnoreCase(value)){
                return true;
            }else if (!ignoreCase && values[i].equals(value)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        BigDecimal b=null;
        System.out.println(b instanceof BigDecimal);
    }


}
