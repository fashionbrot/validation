package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.NotEqualLength;
import com.github.fashionbrot.common.util.JavaUtil;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.ClassUtil;

import java.util.Collection;
import java.util.Map;


public class NotEqualsLengthConstraint implements ConstraintValidator<NotEqualLength, Object> {


    @Override
    public boolean isValid(NotEqualLength annotation, Object objectValue, Class<?> valueType) {
        if (objectValue == null) {
            return annotation.skipEmpty();
        }

        int length = annotation.length();
        if (ClassUtil.isString(valueType)) {
            String value = (String) objectValue;
            if (ObjectUtil.isEmpty(value)) {
                return annotation.skipEmpty();
            }
            return length == value.length();
        } else if (ClassUtil.isMap(valueType)) {
            Map map = (Map<?, ?>) objectValue;
            if (ObjectUtil.isEmpty(map)) {
                return annotation.skipEmpty();
            }
            return length == map.size();
        } else if (ClassUtil.isCollection(valueType)) {
            Collection collection = (Collection) objectValue;
            if (ObjectUtil.isEmpty(collection)) {
                return annotation.skipEmpty();
            }
            return length == collection.size();
        } else if (ClassUtil.isArray(valueType)) {
            Object[] objects = (Object[]) objectValue;
            if (ObjectUtil.isEmpty(objects)) {
                return annotation.skipEmpty();
            }
            return length == objects.length;
        }
        return true;
    }

}
