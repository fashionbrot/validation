package com.github.fashionbrot.internal;

import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class PatternConstraint implements ConstraintValidator<com.github.fashionbrot.annotation.Pattern,Object> {


    @Override
    public boolean isValid(com.github.fashionbrot.annotation.Pattern pattern, Object objectValue, Class<?> valueType) {
        String strValue = ObjectUtil.formatString(objectValue);
        if (ObjectUtil.isEmpty(strValue)){
            return pattern.skipEmpty();
        }
        if (valueType== String.class || valueType==CharSequence.class){
            String regexp = pattern.regexp();
            if (ObjectUtil.isNotEmpty(regexp)) {
                Pattern patternV = Pattern.compile(pattern.regexp());
                return patternV.matcher(strValue).matches();
            }
        }

        return true;
    }

}
