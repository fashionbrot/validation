package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.Length;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;


public class LengthConstraint implements ConstraintValidator<Length, Object> {

	@Override
	public boolean isValid(Length annotation, Object objectValue, Class<?> valueType) {
        String value = ObjectUtil.formatString(objectValue);
        if (ObjectUtil.isEmpty(value)) {
            return annotation.skipEmpty();
        }

        if (valueType == String.class || valueType==CharSequence.class){
            int length = value.length();
            int min = annotation.min();
            int max = annotation.max();
            return length >= min && length <= max;
        }
		return true;
	}

}
