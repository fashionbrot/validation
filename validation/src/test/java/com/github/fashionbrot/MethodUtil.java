package com.github.fashionbrot;

import com.github.fashionbrot.exception.ValidatedException;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodUtil {

    public static Method getMethod(Class clazz,String methodName){
        Method[] methods = clazz.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals(methodName)).findFirst().get();
        return method;
    }

    public static String getMsg(Class clazz,String methodName,Object[] values){

        Method method = getMethod( clazz,  methodName);

        String returnResult="";
        try {
            ValidationConfiguration configuration=new ValidationConfiguration(method,"");
            configuration.validParameter(values);
//            Validator marsValidator = new ValidatorImpl();
//            marsValidator.validParameter(method,values,null);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        return returnResult;
    }

    public static ValidatedException getException(Class clazz,String methodName,Object[] values){

        Method method = getMethod( clazz,  methodName);
        try {
            ValidationConfiguration configuration=new ValidationConfiguration(method,"");
            configuration.validParameter(values);
//            Validator marsValidator = new ValidatorImpl();
//            marsValidator.validParameter(method,values,null);
        }catch (ValidatedException e){
            return e;
        }
        return null;
    }

    public static ValidatedException getNewException(Class clazz,String methodName,Object[] values){

        Method method = getMethod( clazz,  methodName);
        try {
            ValidationConfiguration configuration=new ValidationConfiguration(method,"");
            configuration.validParameter(values);
        }catch (ValidatedException e){
            return e;
        }
        return null;
    }

    public static Object getValue(Class clazz,String methodName,Object[] values){

        Method method = getMethod( clazz,  methodName);
        try {
//            Validator marsValidator = new ValidatorImpl();
//            marsValidator.validParameter(method,values,null);
            ValidationConfiguration configuration=new ValidationConfiguration(method,"");
            configuration.validParameter(values);

            return values;
        }catch (ValidatedException e){
            return null;
        }
    }

}
