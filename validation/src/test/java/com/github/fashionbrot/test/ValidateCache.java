package com.github.fashionbrot.test;

import com.github.fashionbrot.annotation.Valid;
import com.github.fashionbrot.util.ClassUtil;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.Constraint;
import com.github.fashionbrot.constraint.ConstraintHelper;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.util.MethodUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fashionbrot
 */
public class ValidateCache {

    private final static Map<Class, List<MetaConstraint>> CLASS_CACHE = new ConcurrentHashMap<>();


    public static List<MetaConstraint> getMetaConstraint(Class clazz,boolean isSuperClass){
        if (CLASS_CACHE.containsKey(clazz)){
            return CLASS_CACHE.get(clazz);
        }
        List<MetaConstraint> metaConstraintList =new ArrayList<>();
        List<MetaConstraint> superMetaConstraint = getSuperMetaConstraint(clazz, isSuperClass);
        if (ObjectUtil.isNotEmpty(superMetaConstraint)){
            metaConstraintList.addAll(superMetaConstraint);
        }

        Field[] fields = clazz.getDeclaredFields();
        if (ObjectUtil.isEmpty(fields)) {
            return null;
        }

        for (int fieldIndex = 0; fieldIndex < fields.length; fieldIndex++) {
            Field field = fields[fieldIndex];
            if (MethodUtil.isStaticOrFinal(field)){
                continue;
            }
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            List<Annotation> constraintAnnotationList = getConstraintAnnotation(field.getDeclaredAnnotations());
            if (ObjectUtil.isNotEmpty(constraintAnnotationList)){
                for (int annotationIndex = 0; annotationIndex < constraintAnnotationList.size(); annotationIndex++) {
                    Annotation annotation = constraintAnnotationList.get(annotationIndex);
                    List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getAnnotationConstraintValidator(annotation);
                    if (ObjectUtil.isNotEmpty(constraintValidatorList)){
                        MetaConstraint metaConstraint=new MetaConstraint();
                        metaConstraint.setPropertyType(PropertyType.PRIMITIVE);
                        metaConstraint.setClazz(clazz);
                        metaConstraint.setFieldName(fieldName);
                        metaConstraint.setFieldType(fieldType);
                        metaConstraint.setAnnotation(annotation);
                        metaConstraint.setConstraintValidatorList(constraintValidatorList);
                        metaConstraint.setAnnotationMap(convertAnnotationToMap(annotation));
                        metaConstraintList.add(metaConstraint);
                    }
                }
            }else{
                Valid valid = field.getDeclaredAnnotation(Valid.class);
                if (valid!=null){
                    if (ClassUtil.isArray(fieldType)){

                    }else if (ClassUtil.isCollection(fieldType)){

                    }
                }
            }

        }
        if (!isSuperClass){
            CLASS_CACHE.putIfAbsent(clazz,metaConstraintList);
        }
        return metaConstraintList;
    }

    private static List<MetaConstraint> getSuperMetaConstraint(Class clazz, boolean isSuperClass) {
        Class superclass = clazz.getSuperclass();
        if (superclass!=null && !superclass.isAssignableFrom(Object.class)){
            return getMetaConstraint(superclass, isSuperClass);
        }
        return null;
    }

    public static List<Annotation> getConstraintAnnotation(Annotation[] annotations) {
        if (ObjectUtil.isEmpty(annotations)) {
            return null;
        }
        List<Annotation> validAnnotations = new ArrayList<>(annotations.length);
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            if (isConstraintAnnotation(annotation)) {
                validAnnotations.add(annotation);
            }
        }
        return validAnnotations;
    }

    public static boolean isConstraintAnnotation(Annotation annotation) {
        return annotation != null && (ConstraintHelper.containsKey(annotation.annotationType()) || annotation.annotationType().isAnnotationPresent(Constraint.class));
    }

    public static Map<String, Object> convertAnnotationToMap(Annotation annotation) {
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        int initialCapacity = (int) (methods.length / 0.75) + 1;
        Map<String, Object> result = new HashMap<>(initialCapacity);
        for (Method method : methods) {
            try {
                result.put(method.getName(), method.invoke(annotation));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
