package com.github.fashionbrot.internal;

import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.Phone;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class PhoneConstraint implements ConstraintValidator<Phone,Object> {


    @Override
    public boolean isValid(Phone phone, Object objectValue, Class<?> valueType) {
        String strValue = ObjectUtil.formatString(objectValue);
        if (ObjectUtil.isEmpty(strValue)){
            return phone.skipEmpty();
        }
        if (valueType == String.class || valueType== CharSequence.class){
            String regexp = phone.regexp();
            if (PatternSts.PHONE_PATTERN.pattern().equals(regexp)) {
                return PatternSts.PHONE_PATTERN.matcher(strValue).matches();
            } else {
                Pattern pattern ;
                if (ObjectUtil.isEmpty(regexp)) {
                    pattern = PatternSts.PHONE_PATTERN;
                } else {
                    pattern = Pattern.compile(regexp);
                }
                return pattern.matcher(strValue).matches();
            }
        }

        return true;
    }

}
