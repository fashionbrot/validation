package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.common.util.JavaUtil;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;

import java.util.Collection;
import java.util.Map;


public class NotEmptyConstraint implements ConstraintValidator<NotEmpty, Object> {

    /**
     * 验证 字符串是否为空
     * <p>
     * String
     * CharSequence
     * Collection
     * Map
     * Array
     */
    @Override
    public boolean isValid(NotEmpty notEmpty, Object value, Class<?> valueType) {

        if (ClassUtil.isString(valueType)) {
            String str = (String) value;
            return ObjectUtil.isNotEmpty(str);
        } else if (ClassUtil.isMap(valueType)) {
            return ObjectUtil.isNotEmpty((Map<?, ?>) value);
        } else if (ClassUtil.isCollection(valueType)) {
            return ObjectUtil.isNotEmpty((Collection<?>) value);
        } else if (ClassUtil.isArray(valueType)) {
            return ObjectUtil.isNotEmpty((Object[]) value);
        }

        return true;
    }

}
