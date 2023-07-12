package com.github.fashionbrot.internal;


import com.github.fashionbrot.util.JavaUtil;
import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.constraint.ConstraintValidator;

import java.util.Collection;
import java.util.Map;


public class NotEmptyConstraint implements ConstraintValidator<NotEmpty, Object> {

    /**
     * 验证 字符串是否为空
     *
     * String
     * CharSequence
     * Collection
     * Map
     * Array
     */
	@Override
	public boolean isValid(NotEmpty notEmpty, Object value, Class<?> valueType) {

        if (valueType== String.class || valueType == CharSequence.class){
            if (value == null || ObjectUtil.isEmpty(value.toString())) {
                return false;
            }
        }else if (JavaUtil.isMap(valueType)){
            return ObjectUtil.isNotEmpty((Map<?, ?>) value);
        }else if (JavaUtil.isCollection(valueType)){
            return ObjectUtil.isNotEmpty((Collection<?>) value);
        }else if (JavaUtil.isArray(valueType.getTypeName())){
            return ObjectUtil.isNotEmpty((Object[])value);
        }

		return true;
	}

}
