package com.github.fashionbrot.internal;

import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class PatternConstraint implements ConstraintValidator<com.github.fashionbrot.annotation.Pattern,Object> {


    @Override
    public boolean isValid(com.github.fashionbrot.annotation.Pattern pattern, Object objectValue, Class<?> valueType) {

        if (objectValue == null) {
            return false;
        } else {
            String regexp = pattern.regexp();
            String str = ObjectUtil.formatString(objectValue);
            if (ObjectUtil.isNotEmpty(regexp)) {
                Pattern patternV = Pattern.compile(pattern.regexp());
                return patternV.matcher(str).matches();
            }
        }
        return true;
    }

}
