package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.CreditCard;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class CreditCardConstraint implements ConstraintValidator<CreditCard, Object> {


    @Override
    public boolean isValid(CreditCard creditCard, Object value, Class<?> valueType) {

        if (value instanceof CharSequence) {
            String strValue = ObjectUtil.formatString(value);
            if (ObjectUtil.isEmpty(strValue) && creditCard.skipEmpty()) {
                return true;
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
