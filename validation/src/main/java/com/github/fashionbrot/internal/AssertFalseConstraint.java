
package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.AssertFalse;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;

/**
 * Validates that the value passed is false
 */
public class AssertFalseConstraint implements ConstraintValidator<AssertFalse, Object> {

    @Override
    public boolean isValid(AssertFalse annotation, Object value, Class<?> valueType) {
        if (value == null) {
            return annotation.skipEmpty();
        }
        if (ClassUtil.isString(valueType)) {
            String str = (String) value;
            if (ObjectUtil.isEmpty(str)) {
                return annotation.skipEmpty();
            }
            return "false".equalsIgnoreCase(str);
        } else if (ClassUtil.isBoolean(valueType)) {
            return Boolean.FALSE.equals(value);
        }
        return true;
    }


}
