package com.github.fashionbrot.util;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.consts.ValidatedConst;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MethodUtil {


    public static Object getFieldValue(Field field,Object object){
        if (field!=null && !Modifier.isStatic(field.getModifiers())){
            //打开私有访问
            field.setAccessible(true);
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static ConstraintValidator newInstance(Class<? extends ConstraintValidator> constraint){
        try {
            return constraint.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean checkDeclaredMethod(Class<?> clazz,String method){
        if (clazz!=null){
            Method[] methods = clazz.getDeclaredMethods();
            if (ObjectUtil.isNotEmpty(methods)){
                for(int i=0;i<methods.length;i++){
                    if (methods[i].getName().equals(method)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Class[] getAnnotationGroups(Annotation annotation){
        if (annotation==null){
            return null;
        }
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        if (ObjectUtil.isEmpty(methods)){
            return null;
        }
        Class[] groups = null;
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (ValidatedConst.GROUPS.equals(method.getName())){
                groups = (Class[]) getReturnValue(method,annotation);
            }
        }
        return groups;
    }

    public static String getAnnotationMsg(Annotation annotation){
        if (annotation==null){
            return null;
        }
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        if (ObjectUtil.isEmpty(methods)){
            return null;
        }
        String msg = null;
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (ValidatedConst.MSG.equals(method.getName())){
                msg = (String) getReturnValue(method,annotation);
            }
        }
        return msg;
    }


    public static Map<String,Object> getAnnotationAttributes(Annotation annotation){
        if (annotation==null){
            return null;
        }
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        if (ObjectUtil.isEmpty(methods)){
            return null;
        }
        Map<String,Object> methodMap = new HashMap<>(methods.length);
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getParameterTypes().length == 0 && method.getReturnType() != void.class) {
                methodMap.put(method.getName(),getReturnValue(method,annotation));
            }
        }
        return methodMap;
    }

    public static Map<String,Object> getAnnotationMapExcludeMsgAndGroups(Annotation annotation){
        if (annotation==null){
            return null;
        }
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        if (ObjectUtil.isEmpty(methods)){
            return null;
        }
        Map<String,Object> methodMap = new HashMap<>(methods.length);
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (ValidatedConst.MSG.equals(method.getName()) || ValidatedConst.GROUPS.equals(method.getName())){
                continue;
            }
            if (method.getParameterTypes().length == 0 && method.getReturnType() != void.class) {
                methodMap.put(method.getName(),getReturnValue(method,annotation));
            }
        }
        return methodMap;
    }

    public static Object getReturnValue(Method method,Annotation annotation){
        if (method!=null){
            try {
                return method.invoke(annotation);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static void setField(Field field,Object obj,Object reValue){
        try {
            field.set(obj,reValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
