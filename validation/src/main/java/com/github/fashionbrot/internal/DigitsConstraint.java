package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.Digits;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;

import java.util.regex.Pattern;


public class DigitsConstraint implements ConstraintValidator<Digits, Object> {


    @Override
    public boolean isValid(Digits annotation, Object value, Class<?> valueType) {
        if (value == null) {
            return annotation.skipEmpty();
        }

        if (ClassUtil.isString(valueType)) {
            String strValue = (String) value;
            if (ObjectUtil.isEmpty(strValue)) {
                return annotation.skipEmpty();
            }

            return strValue.matches("-?\\d+(\\.\\d+)?");
        }
        return true;
    }


}
