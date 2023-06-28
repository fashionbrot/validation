package com.github.fashionbrot.internal;

import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.BankCard;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class CreditCardConstraint implements ConstraintValidator<BankCard,Object> {


    @Override
    public boolean isValid(BankCard creditCard, Object value,Class<?> valueType) {

        String str = ObjectUtil.formatString(value);
        if (ObjectUtil.isEmpty(str)) {
            return false;
        } else {
            String regexp = creditCard.regexp();
            if (PatternSts.CREDIT_CARD_PATTERN.pattern().equals(regexp)) {
                return PatternSts.CREDIT_CARD_PATTERN.matcher(str).matches();
            } else {
                Pattern pattern ;
                if (ObjectUtil.isEmpty(regexp)) {
                    pattern = PatternSts.CREDIT_CARD_PATTERN;
                } else {
                    pattern = Pattern.compile(regexp);
                }
                return pattern.matcher(str).matches();
            }
        }
    }

}
