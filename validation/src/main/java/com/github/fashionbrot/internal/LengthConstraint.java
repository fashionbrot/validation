package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.Length;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;


public class LengthConstraint implements ConstraintValidator<Length, Object> {

    @Override
    public boolean isValid(Length length, Object value, Class<?> valueType) {
        if (value == null) {
            return length.skipEmpty();
        }

        if (ClassUtil.isString(valueType)) {
            String str = (String) value;
            if (ObjectUtil.isEmpty(str)) {
                return length.skipEmpty();
            }
            int len = str.length();
            int min = length.min();
            int max = length.max();
            return len >= min && len <= max;
        }
        return true;
    }


}
