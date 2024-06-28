package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.NotBlank;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;


public class NotBlankConstraint implements ConstraintValidator<NotBlank, Object> {

    @Override
    public boolean isValid(NotBlank notBlank, Object value, Class<?> valueType) {
        if (ClassUtil.isString(valueType)) {
            String str = (String) value;
            return ObjectUtil.isNotBlank(str);
        }
        return true;
    }

}
