package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.CreditCard;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class CreditCardConstraint implements ConstraintValidator<CreditCard,Object> {


    @Override
    public boolean isValid(CreditCard annotation, Object value,Class<?> valueType) {
        String strValue = ObjectUtil.formatString(value);
        if (ObjectUtil.isEmpty(strValue)){
            return annotation.skipEmpty();
        }

        if (valueType == String.class || valueType==CharSequence.class){

            if (PatternSts.CREDIT_CARD_PATTERN.pattern().equals(annotation.regexp())) {
                return PatternSts.CREDIT_CARD_PATTERN.matcher(strValue).matches();
            } else {
                Pattern pattern ;
                if (ObjectUtil.isEmpty(annotation.regexp())) {
                    pattern = PatternSts.CREDIT_CARD_PATTERN;
                } else {
                    pattern = Pattern.compile(annotation.regexp());
                }
                return pattern.matcher(strValue).matches();
            }
        }

        return true;
    }

}
