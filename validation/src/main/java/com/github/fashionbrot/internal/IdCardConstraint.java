package com.github.fashionbrot.internal;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.ClassUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.IdCard;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;


public class IdCardConstraint implements ConstraintValidator<IdCard, Object> {

    @Override
    public boolean isValid(IdCard idCard, Object value, Class<?> valueType) {
        if (value == null) {
            return idCard.skipEmpty();
        }


        if (ClassUtil.isString(valueType)) {
            String str = (String) value;

            if (ObjectUtil.isEmpty(str)) {
                return idCard.skipEmpty();
            }

            String regexp = idCard.regexp();
            if (ObjectUtil.isEmpty(regexp) || PatternSts.ID_CARD_PATTERN.pattern().equals(regexp)) {
                return PatternSts.ID_CARD_PATTERN.matcher(str).matches();
            } else {
                return Pattern.compile(regexp).matcher(str).matches();
            }
        }

        return true;
    }


}
