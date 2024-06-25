
package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.AssertTrue;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;

/**
 * Validates that the value passed is true
 *
 * @author Alaa Nassef
 */
public class AssertTrueConstraint implements ConstraintValidator<AssertTrue, Object> {

    @Override
    public boolean isValid(AssertTrue annotation, Object value, Class<?> valueType) {

        if (value instanceof CharSequence) {
            String strValue = (String) value;
            if (ObjectUtil.isEmpty(strValue) && annotation.skipEmpty()) {
                return true;
            }
            return ObjectUtil.isTrue(strValue);
        } else if (value instanceof Boolean) {
            Boolean b = (Boolean) value;
            if (b == null && annotation.skipEmpty()) {
                return true;
            }
            return ObjectUtil.isTrue(b);
        }
        return true;
    }
}
