package com.github.fashionbrot.validator;

import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.consts.ValidatedConst;

import java.lang.reflect.Method;

public interface MarsValidator {

    default String getFileName(){
        return ValidatedConst.FILE_NAME_PREFIX;
    }

    default String getLanguage(){
        return ValidatedConst.DEFAULT_LANGUAGE;
    }

    /**
     * 入口
     * 获取 接口参数，验证是否含有注解
     * @param method method
     * @param objects parameter
     */
    void parameterAnnotationValid(Method method, Object[] objects);

    /**
     * 入口
     * 验证返回值
     * @param validated validated
     * @param object validated
     */
    void returnValueAnnotationValid(Validated validated,Object object);


    /**
     * 验证 entity fields 是否包含注解
     * @param validated      validated
     * @param valueTypeName  valueTypeName
     * @param clazz          clazz
     * @param params        params
     * @param paramIndex    paramIndex
     */
    void entityFieldsAnnotationValid(Validated validated, String valueTypeName, Class<?> clazz, Object[] params,Integer paramIndex);

}
