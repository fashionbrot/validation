//package com.github.fashionbrot.test;
//
//import com.github.fashionbrot.annotation.Valid;
//import com.github.fashionbrot.common.util.JavaUtil;
//import com.github.fashionbrot.common.util.ObjectUtil;
//import com.github.fashionbrot.common.util.TypeUtil;
//import com.github.fashionbrot.constraint.Constraint;
//import com.github.fashionbrot.constraint.ConstraintHelper;
//import com.github.fashionbrot.constraint.ConstraintValidator;
//import com.github.fashionbrot.constraint.Violation;
//import com.github.fashionbrot.consts.ValidatedConst;
//import com.github.fashionbrot.util.MethodUtil;
//import com.github.fashionbrot.validate.MetaConstraint;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author fashionbrot
// */
//public class ValidationImpl {
//
//    private static final ConcurrentHashMap<Class,List<MetaClass>> CLASS_MAP = new ConcurrentHashMap<>();
//
//    public static List<Violation> validParameters(Configuration configuration, Parameter[] parameters, Object[] arguments) {
//        if (ObjectUtil.isEmpty(parameters)){
//            return null;
//        }
//
//        for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
//            Parameter parameter = parameters[parameterIndex];
//            Class parameterType = parameter.getType();
//
//        }
//
//        return null;
//    }
//
//
//    public static List<Violation> validClass(Configuration configuration, Object input) {
//        Class<?> inputClass = input.getClass();
//        List<MetaClass> metaClassList = getMetaClass(inputClass,false, (byte) 0x01);
//        if (ObjectUtil.isEmpty(metaClassList)){
//            return null;
//        }
//
//        List<Violation> violationList=new ArrayList<>();
//
//        boolean failFast = configuration.isFailFast();
//        for (int metaClassIndex = 0; metaClassIndex < metaClassList.size(); metaClassIndex++) {
//
//            MetaClass metaClass = metaClassList.get(metaClassIndex);
//            if (metaClass.getType()==0x01 || metaClass.getType()==0x02){
//                List<MetaConstraint> metaConstraintList = metaClass.getMetaConstraintList();
//
//                for (int metaIndex = 0; metaIndex < metaConstraintList.size(); metaIndex++) {
//                    MetaConstraint metaConstraint = metaConstraintList.get(metaIndex);
//                    String fieldName = metaConstraint.getFieldName();
//                    Object fieldValue = MethodUtil.getFieldValue(fieldName,metaConstraint.getClazz(), input);
//
//                    List<ConstraintValidator> containConstraint = metaConstraint.getConstraintValidatorList();
//                    for (int i = 0; i < containConstraint.size(); i++) {
//                        ConstraintValidator constraintValidator = containConstraint.get(i);
//                        boolean valid = constraintValidator.isValid(metaConstraint.getAnnotation(), fieldValue, metaConstraint.getFieldType());
//                        if (!valid){
//                            String msg = (String) metaConstraint.getAnnotationMap().get(ValidatedConst.MSG);
//                            violationList.add(Violation.builder().msg(msg).build());
//                            if (failFast){
//                                return violationList;
//                            }
//                        }
//                    }
//                }
//            }else if (metaClass.getType() == 0x03){
//
//                Field[] declaredFields = inputClass.getDeclaredFields();
//                if (ObjectUtil.isNotEmpty(declaredFields)){
//                    for (int fieldIndex = 0; fieldIndex < declaredFields.length; fieldIndex++) {
//
//                        Field field = declaredFields[fieldIndex];
//                        if (MethodUtil.isStaticOrFinal(field)) {
//                            continue;
//                        }
//                        Valid valid = field.getDeclaredAnnotation(Valid.class);
//                        if (valid==null){
//                            continue;
//                        }
//                        List<Object> objectList = (List<Object>) MethodUtil.getFieldValue(field, input);
//                        if (ObjectUtil.isEmpty(objectList)){
//                            continue;
//                        }
//                        for (int listIndex = 0; listIndex < objectList.size(); listIndex++) {
//                            Object object = objectList.get(listIndex);
//                            List<Violation> fieldListViolationList = validClass(configuration, object);
//                            if (ObjectUtil.isNotEmpty(fieldListViolationList)){
//                                violationList.addAll(fieldListViolationList);
//                            }
//                            if (failFast){
//                                return fieldListViolationList;
//                            }
//                        }
//                    }
//                }
//
//            }
//
//        }
//        return violationList;
//    }
//
//    public static List<MetaClass> getMetaClass(Class clazz,boolean isSuperClass,byte type){
//
//        if (CLASS_MAP.containsKey(clazz)){
//            return CLASS_MAP.get(clazz);
//        }
//
//
//        List<MetaClass> metaClassList=new ArrayList<>();
//
//        Class superclass = clazz.getSuperclass();
//        if (superclass!=null && !superclass.isAssignableFrom(Object.class)){
//            List<MetaClass> superMetaClassList = getMetaClass(superclass,true, (byte) 0x02);
//            if (ObjectUtil.isNotEmpty(superMetaClassList)){
//                metaClassList.addAll(superMetaClassList);
//            }
//        }
//
//        List<MetaConstraint> metaConstraintList =new ArrayList<>();
//        Field[] fields = clazz.getDeclaredFields();
//        if (ObjectUtil.isEmpty(fields)) {
//            return null;
//        }
//
//        for (Field field : fields) {
//            if (MethodUtil.isStaticOrFinal(field)) {
//                continue;
//            }
//            String fieldName = field.getName();
//            Class<?> fieldType = field.getType();
//            List<Annotation> constraintAnnotationList = getConstraintAnnotation(field.getDeclaredAnnotations());
//            if (ObjectUtil.isNotEmpty(constraintAnnotationList)){
//
//                for (int annotationIndex = 0; annotationIndex < constraintAnnotationList.size(); annotationIndex++) {
//                    Annotation annotation = constraintAnnotationList.get(annotationIndex);
//                    List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getAnnotationConstraintValidator(annotation);
//                    if (ObjectUtil.isNotEmpty(constraintValidatorList)){
//                        MetaConstraint metaConstraint=new MetaConstraint();
//                        metaConstraint.setClazz(clazz);
//                        metaConstraint.setFieldName(fieldName);
//                        metaConstraint.setFieldType(fieldType);
//                        metaConstraint.setAnnotation(annotation);
//                        metaConstraint.setConstraintValidatorList(constraintValidatorList);
//                        metaConstraint.setAnnotationMap(convertAnnotationToMap(annotation));
//                        metaConstraintList.add(metaConstraint);
//                    }
//                }
//            }else{
//                Valid valid = field.getDeclaredAnnotation(Valid.class);
//                if (valid==null){
//                    continue;
//                }
//
//                if (JavaUtil.isArray(fieldType)){
//                    Class<?> componentType = fieldType.getComponentType();
//                    List<MetaClass> fieldMetaClassList = getMetaClass(componentType,false, (byte) 0x03);
//                    if (ObjectUtil.isNotEmpty(fieldMetaClassList)){
//                        metaClassList.addAll(fieldMetaClassList);
//                    }
//                }else if (List.class.isAssignableFrom(fieldType)){
//                    Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(field);
//                    Class listType = (Class) actualTypeArguments[0];
//                    List<MetaClass> fieldMetaClassList = getMetaClass(listType,false, (byte) 0x03);
//                    if (ObjectUtil.isNotEmpty(fieldMetaClassList)){
//                        metaClassList.addAll(fieldMetaClassList);
//                    }
//                }
//            }
//        }
//        if (isSuperClass){
//            MetaClass metaClass=new MetaClass();
//            metaClass.setType(type);
//            metaClass.setClazz(clazz);
//            metaClass.setMetaConstraintList(metaConstraintList);
//            metaClassList.add(metaClass);
//        }else{
//            MetaClass metaClass=new MetaClass();
//            metaClass.setType(type);
//            metaClass.setClazz(clazz);
//            metaClass.setMetaConstraintList(metaConstraintList);
//            metaClassList.add(metaClass);
//            if (type==0x01) {
//                CLASS_MAP.putIfAbsent(clazz, metaClassList);
//            }
//        }
//
//
//        return metaClassList;
//    }
//
//
//    private static List<Annotation> getConstraintAnnotation(Annotation[] annotations) {
//        if (ObjectUtil.isEmpty(annotations)) {
//            return null;
//        }
//        List<Annotation> validAnnotations = new ArrayList<>(annotations.length);
//        for (int i = 0; i < annotations.length; i++) {
//            Annotation annotation = annotations[i];
//            if (isConstraintAnnotation(annotation)) {
//                validAnnotations.add(annotation);
//            }
//        }
//        return validAnnotations;
//    }
//
//    private static boolean isConstraintAnnotation(Annotation annotation) {
//        return annotation != null && (ConstraintHelper.containsKey(annotation.annotationType()) || annotation.annotationType().isAnnotationPresent(Constraint.class));
//    }
//
//    private static Map<String, Object> convertAnnotationToMap(Annotation annotation) {
//        Method[] methods = annotation.annotationType().getDeclaredMethods();
//        int initialCapacity = (int) (methods.length / 0.75) + 1;
//        Map<String, Object> result = new HashMap<>(initialCapacity);
//        for (Method method : methods) {
//            try {
//                result.put(method.getName(), method.invoke(annotation));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//}
