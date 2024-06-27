
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
        if (value==null){
            return annotation.skipEmpty();
        }
        if (valueType == String.class || valueType==CharSequence.class){
            String str = (String) value;
            if (ObjectUtil.isEmpty(str) && annotation.skipEmpty()){
                return true;
            }
            return "false".equals(str);
        }else if (valueType== boolean.class || valueType == Boolean.class){
            return Boolean.FALSE.equals(value);
        }
        return true;
    }


}
