
package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.AssertFalse;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ObjectUtil;

/**
 * Validates that the value passed is false
 */
public class AssertFalseConstraint implements ConstraintValidator<AssertFalse, Object> {

	@Override
	public boolean isValid(AssertFalse annotation, Object value, Class<?> valueType) {
        String strValue = ObjectUtil.formatString(value);
        if (ObjectUtil.isEmpty(strValue)){
            return annotation.skipEmpty();
        }

        if(valueType == String.class || valueType== boolean.class || valueType == Boolean.class){
            boolean bool = ObjectUtil.parseBoolean(strValue);
            return !bool;
        }
        return true;
	}
}
