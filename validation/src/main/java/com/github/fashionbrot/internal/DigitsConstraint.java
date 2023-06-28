package com.github.fashionbrot.internal;


import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.annotation.Digits;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.math.BigDecimal;


public class DigitsConstraint implements ConstraintValidator<Digits, Object> {

	@Override
	public boolean isValid(Digits annotation, Object value, Class<?> valueType) {

		if (value instanceof Number){
			return true;
		}else if (value instanceof BigDecimal ) {
			return true;
		}else{
			String strValue = ObjectUtil.formatString(value);
			return ObjectUtil.isDigits(strValue);
		}
	}

}
