package com.github.fashionbrot.internal;

import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.Phone;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class PhoneConstraint implements ConstraintValidator<Phone,Object> {


    @Override
    public boolean isValid(Phone phone, Object objectValue, Class<?> valueType) {

        String regexp = phone.regexp();
        String value = ObjectUtil.formatString(objectValue);
        if (ObjectUtil.isEmpty(value)) {
            return false;
        } else {
            if (PatternSts.PHONE_PATTERN.pattern().equals(regexp)) {
                return PatternSts.PHONE_PATTERN.matcher(value).matches();
            } else {
                Pattern pattern ;
                if (ObjectUtil.isEmpty(regexp)) {
                    pattern = PatternSts.PHONE_PATTERN;
                } else {
                    pattern = Pattern.compile(regexp);
                }
                return pattern.matcher(value).matches();
            }
        }
    }

}
