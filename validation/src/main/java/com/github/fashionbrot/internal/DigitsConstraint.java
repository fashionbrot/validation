package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.Digits;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;


public class DigitsConstraint implements ConstraintValidator<Digits, Object> {


    @Override
    public boolean isValid(Digits annotation, Object value, Class<?> valueType) {
        String strValue = ObjectUtil.formatString(value);
        if (ObjectUtil.isEmpty(strValue) && annotation.skipEmpty()) {
            return true;
        }
        if (value instanceof CharSequence) {
            return strValue.matches("-?\\d+(\\.\\d+)?");
        }
        return true;
    }


}
