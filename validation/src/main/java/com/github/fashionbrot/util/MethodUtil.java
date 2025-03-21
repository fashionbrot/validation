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

@Slf4j
public class MethodUtil {

    /**
     * 判断 Field 是否是 static 或者 final
     * @param field field
     * @return boolean
     */
    public static boolean isStaticOrFinal(Field field){
        return isStatic(field) || isFinal(field);
    }

    /**
     * 判断 Field 是否是 static
     * @param field field
     * @return boolean
     */
    public static boolean isStatic(Field field){
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * 判断 Field 是否是 final
     * @param field field
     * @return boolean
     */
    public static boolean isFinal(Field field){
        return Modifier.isFinal(field.getModifiers());
    }

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

    public static Object getFieldValue(String fieldName,Class clazz,Object object){
        try {
            // 获取指定名称的字段
            Field field = clazz.getDeclaredField(fieldName);

            // 确保字段是可访问的
            field.setAccessible(true);

            // 返回字段的值
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            // 根据需要处理异常，或者重新抛出异常
            return null;
        }
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
                break;
            }
        }
        return groups;
    }



    public static Method filterMethodName(Method[] methods,String methodName){
        if (ObjectUtil.isNotEmpty(methods)){
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (methodName.equals(method.getName())){
                    return method;
                }
            }
        }
        return null;
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
