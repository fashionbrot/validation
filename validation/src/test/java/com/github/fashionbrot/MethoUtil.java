package com.github.fashionbrot;

import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.validator.Validator;
import com.github.fashionbrot.validator.ValidatorImpl;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethoUtil {

    public static Method getMethod(Class clazz,String methoName){
        Method[] methods = clazz.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals(methoName)).findFirst().get();
        return method;
    }

    public static String getMsg(Class clazz,String methoName,Object[] values){

        Method method = getMethod( clazz,  methoName);

        String returnResult="";
        try {
            Validator marsValidator = new ValidatorImpl();
            marsValidator.validParameter(method,values,null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        return returnResult;
    }

}
