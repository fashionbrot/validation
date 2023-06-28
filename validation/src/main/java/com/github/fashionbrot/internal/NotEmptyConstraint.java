package com.github.fashionbrot.internal;


import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.constraint.ConstraintValidator;


public class NotEmptyConstraint implements ConstraintValidator<NotEmpty, Object> {

	@Override
	public boolean isValid(NotEmpty notEmpty, Object value, Class<?> valueType) {

		if (value == null || ObjectUtil.isEmpty(value.toString())) {
			return false;
		}
		return true;
	}

}
