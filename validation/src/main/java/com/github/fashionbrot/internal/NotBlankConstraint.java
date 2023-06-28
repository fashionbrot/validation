package com.github.fashionbrot.internal;


import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.annotation.NotBlank;
import com.github.fashionbrot.constraint.ConstraintValidator;


public class NotBlankConstraint implements ConstraintValidator<NotBlank, Object> {

	@Override
	public boolean isValid(NotBlank length, Object value, Class<?> valueType) {

		if (value == null || ObjectUtil.isBlank(value.toString())) {
			return false;
		}
		return true;
	}

}
