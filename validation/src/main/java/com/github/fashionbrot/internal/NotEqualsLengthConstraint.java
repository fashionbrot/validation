package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.NotEqualLength;
import com.github.fashionbrot.constraint.ConstraintValidator;


public class NotEqualsLengthConstraint implements ConstraintValidator<NotEqualLength, Object> {

	@Override
	public boolean isValid(NotEqualLength notEqualSize, Object value, Class<?> valueType) {

		int valueLength = 0;
		int length = notEqualSize.length();
		if (value != null) {
			valueLength = value.toString().length();
		}
		if (value == null || valueLength != length) {
			return false;
		}
		return true;
	}

}
