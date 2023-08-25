package com.github.fashionbrot.internal;

import com.github.fashionbrot.annotation.Size;
import com.github.fashionbrot.common.util.JavaUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.Collection;
import java.util.Map;

public class SizeConstraint implements ConstraintValidator<Size, Object> {


    @Override
    public boolean isValid(Size annotation, Object value, Class<?> valueType) {
        if (value == null) {
            return annotation.skipEmpty();
        }
        long min = annotation.min();
        long max = annotation.max();

        if (JavaUtil.isMap(valueType)) {
            Map map = (Map<?, ?>) value;
            int size = map.size();

            return checkSize(size, annotation, min, max);

        } else if (JavaUtil.isCollection(valueType)) {
            Collection collection = (Collection) value;
            int size = collection.size();

            return checkSize(size, annotation, min, max);
        } else if (JavaUtil.isArray(valueType.getTypeName())) {
            Object[] array = (Object[]) value;
            int size = array.length;

            return checkSize(size, annotation, min, max);
        }

        return true;
    }

    private boolean checkSize(int size, Size annotation, long min, long max) {
        if (size == 0) {//#issues4
            return annotation.skipEmpty();
        }
        return !(min > size || size > max);
    }

}
