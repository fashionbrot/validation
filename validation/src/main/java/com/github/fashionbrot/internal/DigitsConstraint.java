package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.Digits;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;


public class DigitsConstraint implements ConstraintValidator<Digits, Object> {

    private static final String REGEX="^(\\-|\\+)?\\d+(\\.\\d+)?$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public boolean isValid(Digits annotation, Object value, Class<?> valueType) {
        String strValue = ObjectUtil.formatString(value);
        if (ObjectUtil.isEmpty(strValue)){
            return annotation.skipEmpty();
        }
        if (valueType == String.class || valueType==CharSequence.class){
            return  PATTERN.matcher(strValue).matches();
        }

        return true;
	}



}
