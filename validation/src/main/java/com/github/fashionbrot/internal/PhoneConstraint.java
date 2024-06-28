package com.github.fashionbrot.internal;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.ClassUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.Phone;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class PhoneConstraint implements ConstraintValidator<Phone, Object> {


    @Override
    public boolean isValid(Phone phone, Object objectValue, Class<?> valueType) {
        if (objectValue == null) {
            return phone.skipEmpty();
        }

        if (ClassUtil.isString(valueType)) {
            String strValue = (String) objectValue;
            if (ObjectUtil.isEmpty(strValue)) {
                return phone.skipEmpty();
            }

            String regexp = phone.regexp();
            if (ObjectUtil.isEmpty(regexp) || PatternSts.PHONE_PATTERN.pattern().equals(regexp)) {
                return PatternSts.PHONE_PATTERN.matcher(strValue).matches();
            } else {
                return Pattern.compile(regexp).matcher(strValue).matches();
            }
        }

        return true;
    }

}
