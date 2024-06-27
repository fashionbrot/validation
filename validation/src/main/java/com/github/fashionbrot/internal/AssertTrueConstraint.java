
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
        if (value==null){
            return annotation.skipEmpty();
        }
        if (valueType == String.class || valueType==CharSequence.class){
            String str = (String) value;
            if (ObjectUtil.isEmpty(str) && annotation.skipEmpty()){
                return true;
            }
            return "true".equals(str);
        }else if (valueType== boolean.class || valueType == Boolean.class){
            return Boolean.TRUE.equals(value);
        }
        return true;
    }
}
