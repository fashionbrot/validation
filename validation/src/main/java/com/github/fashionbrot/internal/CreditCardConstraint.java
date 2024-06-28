package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.CreditCard;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.ClassUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class CreditCardConstraint implements ConstraintValidator<CreditCard, Object> {


    @Override
    public boolean isValid(CreditCard creditCard, Object value, Class<?> valueType) {
        if (value == null) {
            return creditCard.skipEmpty();
        }
        if (ClassUtil.isString(valueType)) {
            String strValue = (String) value;
            if (ObjectUtil.isEmpty(strValue)) {
                return creditCard.skipEmpty();
            }

            String regexp = creditCard.regexp();
            if (ObjectUtil.isEmpty(regexp) || PatternSts.CREDIT_CARD_PATTERN.pattern().equals(regexp)) {
                return PatternSts.CREDIT_CARD_PATTERN.matcher(strValue).matches();
            } else {
                return Pattern.compile(regexp).matcher(strValue).matches();
            }
        }

        return true;
    }

}
