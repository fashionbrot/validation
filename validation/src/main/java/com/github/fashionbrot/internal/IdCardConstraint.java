package com.github.fashionbrot.internal;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.util.PatternSts;
import com.github.fashionbrot.annotation.IdCard;
import com.github.fashionbrot.constraint.ConstraintValidator;
import java.util.regex.Pattern;


public class IdCardConstraint implements ConstraintValidator<IdCard, Object> {

	@Override
	public boolean isValid(IdCard idCard, Object objectValue, Class<?> valueType) {
        String value = ObjectUtil.formatString(objectValue);
        if (ObjectUtil.isEmpty(value)) {
            return idCard.skipEmpty();
        }

		String regexp = idCard.regexp();

        if (valueType==String.class || valueType == CharSequence.class){
            if (PatternSts.ID_CARD_PATTERN.pattern().equals(regexp)) {
                return PatternSts.ID_CARD_PATTERN.matcher(value).matches();
            } else {
                Pattern pattern;
                if (ObjectUtil.isEmpty(regexp)) {
                    pattern = PatternSts.ID_CARD_PATTERN;
                } else {
                    pattern = Pattern.compile(regexp);
                }

                return pattern.matcher(value).matches();
            }
        }

        return true;
	}


}
