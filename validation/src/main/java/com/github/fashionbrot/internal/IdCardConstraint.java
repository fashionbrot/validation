package com.github.fashionbrot.internal;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.IdCard;
import com.github.fashionbrot.constraint.ConstraintValidator;
import java.util.regex.Pattern;


public class IdCardConstraint implements ConstraintValidator<IdCard, Object> {

	@Override
	public boolean isValid(IdCard idCard, Object objectValue, Class<?> valueType) {



        if (objectValue instanceof CharSequence){
            String value = (String) objectValue;

            if (ObjectUtil.isEmpty(value) && idCard.skipEmpty()) {
                return true;
            }
            String regexp = idCard.regexp();
            if (ObjectUtil.isEmpty(regexp) || PatternSts.ID_CARD_PATTERN.pattern().equals(regexp)){
                return PatternSts.ID_CARD_PATTERN.matcher(value).matches();
            }else{
                return Pattern.compile(regexp).matcher(value).matches();
            }
        }

        return true;
	}


}
