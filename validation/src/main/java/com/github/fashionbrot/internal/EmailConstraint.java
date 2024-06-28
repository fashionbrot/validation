package com.github.fashionbrot.internal;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.ClassUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.Email;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;


public class EmailConstraint implements ConstraintValidator<Email, Object> {

    @Override
    public boolean isValid(Email email, Object value, Class<?> valueType) {
        if (value == null) {
            return email.skipEmpty();
        }

        if (ClassUtil.isString(valueType)) {
            String str = (String) value;
            if (ObjectUtil.isEmpty(str)) {
                return email.skipEmpty();
            }

            String regexp = email.regexp();
            if (ObjectUtil.isEmpty(regexp) || PatternSts.EMAIL_PATTERN.pattern().equals(regexp)) {
                return PatternSts.EMAIL_PATTERN.matcher(str).matches();
            } else {
                return Pattern.compile(regexp).matcher(str).matches();
            }
        }

        return true;
    }

}
