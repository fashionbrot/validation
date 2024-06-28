package com.github.fashionbrot.internal;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;

import java.util.regex.Pattern;

public class PatternConstraint implements ConstraintValidator<com.github.fashionbrot.annotation.Pattern, Object> {


    @Override
    public boolean isValid(com.github.fashionbrot.annotation.Pattern pattern, Object objectValue, Class<?> valueType) {
        if (objectValue == null) {
            return pattern.skipEmpty();
        }

        if (ClassUtil.isString(valueType)) {
            String strValue = (String) objectValue;
            if (ObjectUtil.isEmpty(strValue)) {
                return pattern.skipEmpty();
            }
            String regexp = pattern.regexp();
            if (ObjectUtil.isNotEmpty(regexp)) {
                Pattern patternV = Pattern.compile(pattern.regexp());
                return patternV.matcher(strValue).matches();
            }
        }

        return true;
    }

}
