package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Default;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;


import java.math.BigDecimal;
import java.math.BigInteger;

public class DefaultConstraint implements ConstraintValidator<Default,Object> {

    @Override
    public boolean isValid(Default annotation, Object var1,Class<?> valueType) {
        return true;
    }


    @Override
    public Object modify(Default annotation, Object value, Class<?> valueType) {
        if (ObjectUtil.isEmpty(annotation.value())){
            return value;
        }


        String strValue = (String) value;
        if (ObjectUtil.isNotEmpty(strValue)){
            return value;
        }

        if (valueType == BigDecimal.class){
                return new BigDecimal(annotation.value());
        }else if (valueType == BigInteger.class) {
                return new BigInteger(annotation.value());
        }else if (valueType== Short.class){
                return ObjectUtil.parseShort(annotation.value());
        }else if (valueType == Long.class){
                return ObjectUtil.parseLong(annotation.value());
        }else if (valueType == Integer.class){
            return ObjectUtil.parseInteger(annotation.value());
        }else if (valueType == Float.class){
            return ObjectUtil.parseFloat(annotation.value());
        }else if (valueType == Double.class){
            return ObjectUtil.parseDouble(annotation.value());
        }else if (valueType==String.class || valueType == CharSequence.class){
            return annotation.value();
        }

        return value;
    }

}
