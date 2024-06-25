
package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.AssertFalse;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;

/**
 * Validates that the value passed is false
 */
public class AssertFalseConstraint implements ConstraintValidator<AssertFalse, Object> {

    @Override
    public boolean isValid(AssertFalse annotation, Object value, Class<?> valueType) {

        if (value instanceof CharSequence || valueType == String.class || valueType == CharSequence.class) {
            String strValue = (String) value;
            if (ObjectUtil.isEmpty(strValue) && annotation.skipEmpty()) {
                return true;
            }
            return ObjectUtil.isFalse(strValue);
        } else if (value instanceof Boolean || valueType==boolean.class || valueType == Boolean.class) {
            Boolean b = (Boolean) value;
            if (b == null && annotation.skipEmpty()) {
                return true;
            }
            return ObjectUtil.isFalse((Boolean) value);
        }

        return true;

    }


}
