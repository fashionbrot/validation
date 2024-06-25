package com.github.fashionbrot.internal;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.Email;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;


public class EmailConstraint implements ConstraintValidator<Email, Object> {

    @Override
    public boolean isValid(Email email, Object object, Class<?> valueType) {

        if (object instanceof CharSequence) {
            String value = ObjectUtil.formatString(object);
            if (ObjectUtil.isEmpty(value) && email.skipEmpty()) {
                return true;
            }

            String regexp = email.regexp();
            if (ObjectUtil.isEmpty(regexp) || PatternSts.EMAIL_PATTERN.pattern().equals(regexp)) {
                return PatternSts.EMAIL_PATTERN.matcher(value).matches();
            } else {
                return Pattern.compile(regexp).matcher(value).matches();
            }
        }

        return true;
    }

}
