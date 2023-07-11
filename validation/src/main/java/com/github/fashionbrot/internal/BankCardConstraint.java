package com.github.fashionbrot.internal;

import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.BankCard;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class BankCardConstraint implements ConstraintValidator<BankCard,Object> {


    @Override
    public boolean isValid(BankCard annotation, Object value,Class<?> valueType) {
        String strValue = ObjectUtil.formatString(value);
        if (ObjectUtil.isEmpty(strValue)){
            return annotation.skipEmpty();
        }
        if (valueType== String.class || valueType ==  CharSequence.class){

            String regexp = annotation.regexp();
            if (PatternSts.BANKCARD_PATTERN.pattern().equals(regexp)) {
                return PatternSts.BANKCARD_PATTERN.matcher(strValue).matches();
            } else {
                Pattern pattern ;
                if (ObjectUtil.isNotEmpty(regexp)) {
                    pattern =PatternSts.BANKCARD_PATTERN;
                } else {
                    pattern = Pattern.compile(regexp);
                }
                return pattern.matcher(strValue).matches();
            }
        }

        return true;
    }

}
