package com.github.fashionbrot.validator;

import java.lang.reflect.Method;

public interface Validator {

    /**
     * 入口
     * 获取 接口参数，验证是否含有注解
     * @param method method
     * @param arguments arguments
     * @param language error msg language,language==null?zh_CN:language
     */
    void validParameter(Method method, Object[] arguments,String language);

    /**
     * 入口
     * 验证返回值
     * @param method method
     * @param argument argument
     * @param language error msg language,language==null?zh_CN:language
     */
    void validReturnValue(Method method, Object argument,String language);

}
