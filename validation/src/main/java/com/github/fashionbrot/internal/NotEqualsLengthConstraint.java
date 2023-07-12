package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.NotEqualLength;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.JavaUtil;
import com.github.fashionbrot.util.ObjectUtil;

import java.util.Collection;
import java.util.Map;


public class NotEqualsLengthConstraint implements ConstraintValidator<NotEqualLength, Object> {


    @Override
    public boolean isValid(NotEqualLength annotation, Object objectValue, Class<?> valueType) {
        if (objectValue == null) {
            return annotation.skipEmpty();
        }
        int length = annotation.length();
        if (valueType == String.class || valueType == CharSequence.class) {
            String value = ObjectUtil.formatString(objectValue);
            if (ObjectUtil.isEmpty(value)) {
                return annotation.skipEmpty();
            }
            return length == value.length();

        } else if (JavaUtil.isMap(valueType)) {
            Map map = (Map<?, ?>) objectValue;
            if (ObjectUtil.isEmpty(map)){
                return annotation.skipEmpty();
            }
            return length == map.size();
        } else if (JavaUtil.isCollection(valueType)) {
            Collection collection = (Collection) objectValue;
            if (ObjectUtil.isEmpty(collection)){
                return annotation.skipEmpty();
            }
            return length == collection.size();
        } else if (JavaUtil.isArray(valueType.getTypeName())) {
            Object[] objects = (Object[]) objectValue;
            if (ObjectUtil.isEmpty(objects)){
                return annotation.skipEmpty();
            }
            return length == objects.length;
        }
        return true;
    }

}
