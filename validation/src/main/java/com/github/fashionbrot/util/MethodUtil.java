package com.github.fashionbrot.util;

import com.github.fashionbrot.constraint.ConstraintValidator;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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



    private static Map<Annotation, Map<String,Object>> TEMP = new ConcurrentHashMap<>();

    public static Map<String,Object> getAnnotationAttributes(Annotation annotation){
        Map<String, Object> tempMap = TEMP.get(annotation);
        if (ObjectUtil.isNotEmpty(tempMap)){
            return tempMap;
        }
        Class<? extends Annotation> annotationClass = annotation.getClass();
        Method[] methods = annotationClass.getDeclaredMethods();
        if (ObjectUtil.isNotEmpty(methods)){
            Map<String,Object> methodMap = new HashMap<>(methods.length);
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (isObjectMethod(method) || isAnnotationType(method)){
                    continue;
                }
                if (method.getParameterTypes().length == 0 && method.getReturnType() != void.class) {
                    methodMap.put(method.getName(),getReturnValue(method,annotation));
                }
            }
            TEMP.put(annotation,methodMap);

            return methodMap;
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


    /**
     * Determine whether the given method is an "equals" method.
     * @see java.lang.Object#equals(Object)
     */
    public static boolean isEqualsMethod( Method method) {
        if (method == null) {
            return false;
        }
        if (method.getParameterCount() != 1) {
            return false;
        }
        if (!"equals".equals(method.getName())) {
            return false;
        }
        return method.getParameterTypes()[0] == Object.class;
    }

    /**
     * Determine whether the given method is a "hashCode" method.
     * @see java.lang.Object#hashCode()
     */
    public static boolean isHashCodeMethod( Method method) {
        return method != null && method.getParameterCount() == 0 && "hashCode".equals(method.getName());
    }

    /**
     * Determine whether the given method is a "toString" method.
     * @see java.lang.Object#toString()
     */
    public static boolean isToStringMethod( Method method) {
        return (method != null && method.getParameterCount() == 0 && "toString".equals(method.getName()));
    }

    /**
     * Determine whether the given method is originally declared by {@link java.lang.Object}.
     */
    public static boolean isObjectMethod( Method method) {
        return (method != null && (method.getDeclaringClass() == Object.class ||
            isEqualsMethod(method) || isHashCodeMethod(method) || isToStringMethod(method)));
    }


    public static boolean isAnnotationType(Method method){
        return (method != null && method.getParameterCount() == 0 && "annotationType".equals(method.getName()));
    }


}
