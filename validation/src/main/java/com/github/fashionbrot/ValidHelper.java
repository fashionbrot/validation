package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Valid;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.annotation.ValidatedName;
import com.github.fashionbrot.common.util.GenericTokenUtil;
import com.github.fashionbrot.common.util.JavaUtil;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.common.util.TypeUtil;
import com.github.fashionbrot.constraint.Constraint;
import com.github.fashionbrot.constraint.ConstraintHelper;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.constraint.Violation;
import com.github.fashionbrot.consts.ValidatedConst;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.groups.DefaultGroup;
import com.github.fashionbrot.ognl.OgnlCache;
import com.github.fashionbrot.util.MethodUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author fashionbrot
 */
public class ValidHelper {

    public static String SPRING_PROFILES_ACTIVE = System.getProperty("validation.spring.profiles.active");


    public static void setSpringProfilesActive(String springProfilesActive) {
        SPRING_PROFILES_ACTIVE = springProfilesActive;
    }

    public static void validParameter(String language, Method method, Object[] arguments) {
        Validated validated = method.getAnnotation(Validated.class);
        if (validated == null) {
            return;
        }
        Class<?>[] validatedGroups = validated.groups();
        boolean failFast = validated.failFast();

        Parameter[] parameters = method.getParameters();
        if (ObjectUtil.isEmpty(parameters)) {
            return;
        }

        List<Violation> violationList = failFast ? new ArrayList<>(1) : new ArrayList<>();


        try {
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {


                List<Annotation> constraintAnnotationList = getConstraintAnnotation(parameters[parameterIndex].getDeclaredAnnotations());
                if (ObjectUtil.isEmpty(constraintAnnotationList)) {
                    Parameter parameter = parameters[parameterIndex];
                    Class<?> parameterType = parameter.getType();

                    Valid valid = parameter.getDeclaredAnnotation(Valid.class);
                    if (valid != null) {
                        if (JavaUtil.isArray(parameterType)) {
                            validArrayObject(language, validatedGroups, failFast, parameters, arguments, parameterIndex, violationList);
                        } else if (JavaUtil.isCollection(parameterType)) {
                            validListObject(language, validatedGroups, failFast, parameters, arguments, parameterIndex, violationList);
                        }
                    }

                    if (JavaUtil.isNotPrimitive(parameterType)) {
                        entityFieldsAnnotationValid(language, parameterType, validatedGroups, failFast, arguments, parameterIndex, violationList);
                    }


                } else {

                    for (int annotationIndex = 0; annotationIndex < constraintAnnotationList.size(); annotationIndex++) {
                        Annotation annotation = constraintAnnotationList.get(annotationIndex);

                        Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
                        if (ObjectUtil.isEmpty(annotationMethods)) {
                            continue;
                        }

                        if (!checkAnnotationGroups(annotation, validatedGroups)) {
                            continue;
                        }

                        List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getAnnotationConstraintValidator(annotation);
                        if (ObjectUtil.isNotEmpty(constraintValidatorList)) {
                            for (int constraintIndex = 0; constraintIndex < constraintValidatorList.size(); constraintIndex++) {
                                ConstraintValidator constraintValidator = constraintValidatorList.get(constraintIndex);


                                Object parameterValue = arguments[parameterIndex];
                                Parameter parameter = parameters[parameterIndex];
                                String parameterName = parameter.getName();
                                Class<?> parameterType = parameter.getType();

                                if (ObjectUtil.isTrue(checkExpressionParameter(parameters, arguments, annotation, annotationMethods))) {
                                    if (!constraintValidator.isValid(annotation, parameterValue, parameterType)) {

                                        String annotationName = getAnnotationName(annotation);
                                        Violation violation = Violation.builder()
                                            .annotationName(annotationName)
                                            .fieldName(parameterName)
                                            .message(getAnnotationMsg(annotation, annotationMethods, language))
                                            .value(parameterValue)
                                            .valueIndex(parameterIndex)
                                            .build();

                                        violationList.add(violation);

                                        if (failFast) {
                                            throw new ValidatedException(violationList);
                                        }
                                    }
                                }


                                Class validConstraintClass = constraintValidator.getClass();
                                if (MethodUtil.checkDeclaredMethod(validConstraintClass, ValidatedConst.METHOD_NAME_MODIFY)) {
                                    Object reValue = constraintValidator.modify(annotation, parameterValue, parameterType);
                                    if (reValue == null) {
                                        return;
                                    }
                                    arguments[parameterIndex] = reValue;
                                }

                            }
                        }

                    }


                }
            }
        } finally {
            if (ObjectUtil.isNotEmpty(violationList)) {
                throw new ValidatedException(violationList);
            }
        }


    }


    private static void validArrayObject(
        String language,
        Class[] validatedGroups,
        boolean failFast,
        Parameter[] parameters,
        Object[] arguments,
        Integer parameterIndex,
        List<Violation> violationList) {

        Class<?> parameterType = parameters[parameterIndex].getType();
        Class parameterClass = parameterType.getComponentType();
        if (JavaUtil.isNotPrimitive(parameterClass.getTypeName())) {
            Object[] array = (Object[]) arguments[parameterIndex];
            if (ObjectUtil.isNotEmpty(array)) {
                for (int arrayIndex = 0; arrayIndex < array.length; arrayIndex++) {
                    entityFieldsAnnotationValid(language, parameterClass, validatedGroups, failFast, array, arrayIndex, violationList);
                }
            }
        }
    }


    private static void validListObject(
        String language,
        Class[] validatedGroups,
        boolean failFast,
        Parameter[] parameters,
        Object[] params,
        Integer parameterIndex,
        List<Violation> violationList) {

        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(parameters[parameterIndex]);
        if (ObjectUtil.isNotEmpty(actualTypeArguments) &&
            actualTypeArguments[0] instanceof Class &&
            JavaUtil.isNotPrimitive(actualTypeArguments[0].getTypeName())) {

            Class typeConvertClass = TypeUtil.convertTypeToClass(actualTypeArguments[0]);
            if (typeConvertClass != null && params[parameterIndex] instanceof List) {
                List list = (List) params[parameterIndex];

                if (ObjectUtil.isNotEmpty(list)) {
                    for (int listIndex = 0; listIndex < list.size(); listIndex++) {

                        entityFieldsAnnotationValid(language, typeConvertClass, validatedGroups, failFast, list.toArray(), listIndex, violationList);
                    }
                }
            }
        }
    }

    public static void validated(Object object) {
        validated("", true, null, object);
    }


    public static void validated(String language,
                                 boolean failFast,
                                 Class[] groups,
                                 Object object) {
        if (object == null) {
            return;
        }
        Class<?> clazz = object.getClass();
        if (JavaUtil.isArray(clazz) || JavaUtil.isCollection(clazz) || JavaUtil.isPrimitive(clazz)) {
            return;
        }

        List<Violation> violationList = failFast ? new ArrayList<>(1) : new ArrayList<>();
        try {
            entityFieldsAnnotationValid(language, clazz, groups, failFast, new Object[]{object}, 0, violationList);
        } finally {
            if (ObjectUtil.isNotEmpty(violationList)) {
                throw new ValidatedException(violationList);
            }
        }
    }


    private static void entityFieldsAnnotationValid(String language,
                                                    Class entityClass,
                                                    Class[] validatedGroups,
                                                    boolean failFast,
                                                    Object[] arguments,
                                                    Integer parameterIndex,
                                                    List<Violation> violationList) {
        // 判断是否 有继承类
        checkClassSuper(language, entityClass, validatedGroups, failFast, arguments, parameterIndex, violationList);

        Field[] fields = entityClass.getDeclaredFields();
        if (ObjectUtil.isEmpty(fields)) {
            return;
        }

        for (Field field : fields) {
            if (MethodUtil.isStaticOrFinal(field)) {
                continue;
            }
            Class<?> fieldType = field.getType();

            String fieldName = field.getName();

            List<Annotation> constraintAnnotationList = getConstraintAnnotation(field.getDeclaredAnnotations());
            if (ObjectUtil.isEmpty(constraintAnnotationList)) {


                Valid valid = field.getDeclaredAnnotation(Valid.class);
                if (valid != null) {
                    if (JavaUtil.isArray(fieldType)) {
                        validArrayObject(language, validatedGroups, failFast, field, arguments, parameterIndex, violationList);
                    } else if (JavaUtil.isCollection(fieldType)) {
                        validListObject(language, validatedGroups, failFast, field, arguments, parameterIndex, violationList);
                    }
                }

                if (JavaUtil.isNotPrimitive(fieldType)) {
                    entityFieldsAnnotationValid(language, fieldType, validatedGroups, failFast, arguments, parameterIndex, violationList);
                }

            } else {


                for (int annotationIndex = 0; annotationIndex < constraintAnnotationList.size(); annotationIndex++) {
                    Annotation annotation = constraintAnnotationList.get(annotationIndex);

                    Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
                    if (ObjectUtil.isEmpty(annotationMethods)) {
                        continue;
                    }

                    if (!checkAnnotationGroups(annotation, validatedGroups)) {
                        continue;
                    }

                    List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getAnnotationConstraintValidator(annotation);
                    if (ObjectUtil.isNotEmpty(constraintValidatorList)) {
                        for (int constraintIndex = 0; constraintIndex < constraintValidatorList.size(); constraintIndex++) {
                            ConstraintValidator constraintValidator = constraintValidatorList.get(constraintIndex);


                            Object fieldValue = MethodUtil.getFieldValue(field, arguments[parameterIndex]);

                            if (ObjectUtil.isTrue(checkExpressionField(arguments, field, annotation, annotationMethods))) {
                                if (!constraintValidator.isValid(annotation, fieldValue, fieldType)) {

                                    String annotationName = getAnnotationName(annotation);
                                    Violation violation = Violation.builder()
                                        .annotationName(annotationName)
                                        .fieldName(fieldName)
                                        .message(getAnnotationMsg(annotation, annotationMethods, language))
                                        .value(fieldValue)
                                        .valueIndex(parameterIndex)
                                        .build();
                                    violationList.add(violation);

                                    if (failFast) {
                                        throw new ValidatedException(violationList);
                                    }
                                }
                            }


                            Class validConstraintClass = constraintValidator.getClass();
                            if (MethodUtil.checkDeclaredMethod(validConstraintClass, ValidatedConst.METHOD_NAME_MODIFY)) {
                                Object reValue = constraintValidator.modify(annotation, fieldValue, fieldType);
                                if (reValue == null) {
                                    return;
                                }
                                MethodUtil.setField(field, arguments[parameterIndex], reValue);
                            }

                        }
                    }

                }


            }
        }
    }


    private static void validArrayObject(
        String language,
        Class[] validatedGroups,
        boolean failFast,
        Field field,
        Object[] arguments,
        Integer parameterIndex,
        List<Violation> violationList) {
        Class convertClass = field.getType().getComponentType();
        if (JavaUtil.isNotPrimitive(convertClass.getTypeName())) {
            Object[] array = (Object[]) MethodUtil.getFieldValue(field, arguments[parameterIndex]);
            if (ObjectUtil.isNotEmpty(array)) {
                for (int arrayIndex = 0; arrayIndex < array.length; arrayIndex++) {
                    entityFieldsAnnotationValid(language, convertClass, validatedGroups, failFast, array, arrayIndex, violationList);
                }
            }
        }
    }

    private static void validListObject(
        String language,
        Class[] validatedGroups,
        boolean failFast,
        Field field,
        Object[] arguments,
        Integer parameterIndex,
        List<Violation> violationList) {

        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(field);
        if (ObjectUtil.isNotEmpty(actualTypeArguments) &&
            actualTypeArguments[0] instanceof Class &&
            JavaUtil.isNotPrimitive(actualTypeArguments[0].getTypeName())) {

            Class typeConvertClass = TypeUtil.convertTypeToClass(actualTypeArguments[0]);
            if (typeConvertClass != null) {
                List list = (List) MethodUtil.getFieldValue(field, arguments[parameterIndex]);
                if (ObjectUtil.isNotEmpty(list)) {
                    for (int listIndex = 0; listIndex < list.size(); listIndex++) {

                        entityFieldsAnnotationValid(language, typeConvertClass, validatedGroups, failFast, list.toArray(), listIndex, violationList);
                    }
                }
            }
        }
    }


    private static void checkClassSuper(String language,
                                        Class entityClass,
                                        Class[] validatedGroups,
                                        boolean failFast,
                                        Object[] arguments,
                                        Integer parameterIndex,
                                        List<Violation> violationList) {
        if (JavaUtil.isPrimitive(entityClass)) {
            return;
        }
        Class superclass = entityClass.getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {
            //如果不是定义的类型，则把 class 当做bean 进行校验 field
            entityFieldsAnnotationValid(language, superclass, validatedGroups, failFast, arguments, parameterIndex, violationList);
        }
    }


    public static String getAnnotationName(Annotation annotation) {
        return annotation != null ? annotation.annotationType().getSimpleName() : "";
    }

    private static Boolean checkExpressionField(Object[] params, Field field, Annotation annotation, Method[] annotationMethods) {
        Method expressionMethod = MethodUtil.filterMethodName(annotationMethods, ValidatedConst.EXPRESSION);
        if (expressionMethod == null) {
            return true;
        }
        String expression = (String) MethodUtil.getReturnValue(expressionMethod, annotation);
        if (ObjectUtil.isEmpty(expression)) {
            return true;
        }
        Map<String, Object> rootMap = createRootMap(params, field);
        return OgnlCache.executeExpression(expression, rootMap);
    }

    private static Map<String, Object> createRootMap(Object[] params, Field field) {
        int initialCapacity = (int) (params.length + 1 / 0.75) + 1;
        Map<String, Object> rootMap = new HashMap<>(initialCapacity);
        for (int i = 0; i < params.length; i++) {
            String name = getClassParamName(field);
            rootMap.put(name, params[i]);
        }
        rootMap.put(ValidatedConst.SPRING_PROFILES_ACTIVE, SPRING_PROFILES_ACTIVE);
        return rootMap;
    }

    private static String getClassParamName(Field field) {
        ValidatedName validatedName = field.getDeclaringClass().getDeclaredAnnotation(ValidatedName.class);
        return validatedName != null ? validatedName.value() : captureName(field.getDeclaringClass().getSimpleName());
    }


    private static String captureName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        char[] cs = name.toCharArray();
        cs[0] = Character.toLowerCase(cs[0]);
        return new String(cs);
    }

    private static Boolean checkExpressionParameter(Parameter[] parameters, Object[] params, Annotation annotation, Method[] annotationMethods) {
        Method expressionMethod = MethodUtil.filterMethodName(annotationMethods, ValidatedConst.EXPRESSION);
        if (expressionMethod == null) {
            return true;
        }
        String expression = (String) MethodUtil.getReturnValue(expressionMethod, annotation);
        if (ObjectUtil.isEmpty(expression)) {
            return true;
        }
        int initialCapacity = (int) (parameters.length + 1 / 0.75) + 1;
        Map<String, Object> rootMap = new HashMap<>(initialCapacity);
        for (int j = 0; j < parameters.length; j++) {
            Parameter parameter = parameters[j];
            Object argument = params[j];
            rootMap.put(parameter.getName(), argument);
        }
        rootMap.put(ValidatedConst.SPRING_PROFILES_ACTIVE, SPRING_PROFILES_ACTIVE);
        return OgnlCache.executeExpression(expression, rootMap);
    }

    private static String getAnnotationMsg(Annotation annotation, Method[] annotationMethods, String language) {
        Method method = MethodUtil.filterMethodName(annotationMethods, ValidatedConst.MESSAGE);
        if (method == null) {
            return null;
        }
        String annotationMsg = (String) MethodUtil.getReturnValue(method, annotation);
        String filterMsg = MessageHelper.filterMessage(annotationMsg, language);
        if (GenericTokenUtil.isOpenToken(filterMsg, ValidatedConst.OPEN_TOKEN)) {
            Map<String, Object> annotationAttributes = getAnnotationMap(annotation, annotationMethods);
            return GenericTokenUtil.parse(filterMsg, annotationAttributes);
        }
        return filterMsg;
    }

    public static Map<String, Object> getAnnotationMap(Annotation annotation, Method[] annotationMethod) {
        int initialCapacity = (int) (annotationMethod.length + 1 / 0.75);
        Map<String, Object> methodMap = new HashMap<>(initialCapacity);
        for (int i = 0; i < annotationMethod.length; i++) {
            Method method = annotationMethod[i];
            if (ValidatedConst.MESSAGE.equals(method.getName())
                || ValidatedConst.GROUPS.equals(method.getName())
                || ValidatedConst.EXPRESSION.equals(method.getName())) {
                continue;
            }
            if (method.getParameterTypes().length == 0 && method.getReturnType() != void.class) {
                methodMap.put(method.getName(), MethodUtil.getReturnValue(method, annotation));
            }
        }
        return methodMap;
    }

    private static List<Annotation> getConstraintAnnotation(Annotation[] annotations) {
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


    private static boolean isConstraintAnnotation(Annotation annotation) {
        return annotation != null && (ConstraintHelper.containsKey(annotation.annotationType()) || annotation.annotationType().isAnnotationPresent(Constraint.class));
    }


    private static boolean checkAnnotationGroups(Annotation annotation, Class<?>[] validatedGroups) {
        if (ObjectUtil.isEmpty(validatedGroups)) {
            return true;
        }
        Class[] annotationGroups = MethodUtil.getAnnotationGroups(annotation);
        if (ObjectUtil.isNotEmpty(annotationGroups)) {
            return checkGroup(validatedGroups, annotationGroups);
        } else {
            return checkGroup(validatedGroups, DefaultGroup.class);
        }
    }

    private static boolean checkGroup(Class<?>[] vGroup, Class<?>[] aGroup) {
        if (ObjectUtil.isEmpty(aGroup)) {
            return false;
        }
        for (Class v : vGroup) {
            if (checkGroup(aGroup, v)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkGroup(Class[] groups, Class group) {
        for (Class g : groups) {
            if (group.equals(g)) {
                return true;
            }
        }
        return false;
    }

}
