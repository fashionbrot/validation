package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.exception.ValidatedException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MethodUtil {

    public static Method getMethod(Class clazz,String methodName){
        Method[] methods = clazz.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals(methodName)).findFirst().get();
        return method;
    }


    public static String getMessage(Class clazz,String methodName,Object[] values){
        ValidatedException validatedException = getException(clazz, methodName, values);
        if (validatedException!=null){
            if (ObjectUtil.isNotEmpty(validatedException.getViolations())){
                return validatedException.getViolations().stream().map(m-> m.getMessage()).collect(Collectors.joining(","));
            }
        }
        return "";
    }

    public static ValidatedException getException(Class clazz,String methodName,Object[] values){
        Method method = getMethod( clazz,  methodName);
        try {
            Validated validated = method.getDeclaredAnnotation(Validated.class);
            if (validated!=null){
                ValidHelper.validParameter("",method,values);
            }
        }catch (ValidatedException e){
            return e;
        }
        return null;
    }


//    public static ValidatedException getException2(Class clazz,String methodName,Object[] values){
//        Method method = getMethod( clazz,  methodName);
//        try {
//            Validated validated = method.getDeclaredAnnotation(Validated.class);
//            if (validated!=null){
//                ValidationConfiguration configuration=new ValidationConfiguration(validated.groups(),validated.failFast(),"","default");
//                configuration.validParameter(method.getParameters(),values);
//            }
//        }catch (ValidatedException e){
//            return e;
//        }
//        return null;
//    }

    public static ValidatedException getNewException(Class clazz,String methodName,Object[] values){
        return getException(clazz,methodName,values);
    }


}
