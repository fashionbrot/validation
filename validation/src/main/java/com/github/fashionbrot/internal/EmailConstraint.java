package com.github.fashionbrot.internal;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.Email;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;


public class EmailConstraint implements ConstraintValidator<Email, Object> {

	@Override
	public boolean isValid(Email email, Object object, Class<?> valueType) {

		String value = ObjectUtil.formatString(object);
		if (ObjectUtil.isEmpty(value)) {
			return email.skipEmpty();
		}
        if (valueType == String.class || valueType == CharSequence.class){
            String regexp = email.regexp();
            if (PatternSts.EMAIL_PATTERN.pattern().equals(regexp)) {
                return PatternSts.EMAIL_PATTERN.matcher(value).matches();
            } else {
                Pattern pattern ;
                if (ObjectUtil.isEmpty(regexp)) {
                    pattern = PatternSts.EMAIL_PATTERN;
                } else {
                    pattern = Pattern.compile(regexp);
                }
                return pattern.matcher(value).matches();
            }
        }

        return true;
	}

}
