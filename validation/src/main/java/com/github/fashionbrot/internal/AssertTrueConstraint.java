
package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.AssertTrue;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;

/**
 * Validates that the value passed is true
 *
 * @author Alaa Nassef
 */
public class AssertTrueConstraint implements ConstraintValidator<AssertTrue, Object> {

    @Override
    public boolean isValid(AssertTrue annotation, Object value, Class<?> valueType) {
        if (value == null) {
            return annotation.skipEmpty();
        }
        if (ClassUtil.isString(valueType)) {
            String str = (String) value;
            if (ObjectUtil.isEmpty(str)) {
                return annotation.skipEmpty();
            }
            return "true".equalsIgnoreCase(str);
        } else if (ClassUtil.isBoolean(valueType)) {
            return Boolean.TRUE.equals(value);
        }
        return true;
    }
}
