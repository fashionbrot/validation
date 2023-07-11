
package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.AssertTrue;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ObjectUtil;

/**
 * Validates that the value passed is true
 *
 * @author Alaa Nassef
 */
public class AssertTrueConstraint implements ConstraintValidator<AssertTrue, Object> {

	@Override
	public boolean isValid(AssertTrue annotation, Object value, Class<?> valueType) {
        String strValue = ObjectUtil.formatString(value);
        if (ObjectUtil.isEmpty(strValue)){
            return annotation.skipEmpty();
        }

        if(valueType == String.class || valueType== boolean.class || valueType == Boolean.class){
            boolean bool = ObjectUtil.parseBoolean(strValue);
            return bool;
        }
        return true;
	}
}
