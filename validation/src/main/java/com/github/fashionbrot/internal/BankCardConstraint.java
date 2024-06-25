package com.github.fashionbrot.internal;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.BankCard;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.regex.Pattern;

public class BankCardConstraint implements ConstraintValidator<BankCard,Object> {


    @Override
    public boolean isValid(BankCard annotation, Object value,Class<?> valueType) {

        if (value instanceof   CharSequence){
            String strValue = (String) value;
            if (ObjectUtil.isEmpty(strValue) && annotation.skipEmpty()){
                return true;
            }

            String regexp = annotation.regexp();

            if ( ObjectUtil.isEmpty(regexp) || PatternSts.BANKCARD_PATTERN.pattern().equals(regexp)){
                return PatternSts.BANKCARD_PATTERN.matcher(strValue).matches();
            }else{
                return Pattern.compile(regexp).matcher(strValue).matches();
            }
        }

        return true;
    }

}
