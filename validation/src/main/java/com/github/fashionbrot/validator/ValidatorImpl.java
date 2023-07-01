package com.github.fashionbrot.validator;

import com.github.fashionbrot.constraint.Constraint;
import com.github.fashionbrot.constraint.ConstraintHelper;
import com.github.fashionbrot.constraint.ConstraintValidator;
import com.github.fashionbrot.constraint.MarsViolation;
import com.github.fashionbrot.consts.ValidatedConst;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.groups.DefaultGroup;
import com.github.fashionbrot.util.*;
import com.github.fashionbrot.annotation.Valid;
import com.github.fashionbrot.annotation.Validated;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class ValidatorImpl implements Validator {


    public static final String BEAN_NAME = "defaultValidatorImpl";

    public void entityFieldsAnnotationValid(Validated validated, Class<?> clazz, Object[] params,Integer paramIndex,String language) {


        // 判断是否 有继承类
        checkClassSuper(validated, clazz, params,paramIndex,language);

        //如果填写 validClass
        Class<?>[] validClass = validated != null ? validated.validClass() : null;
        if (!isValidClass(validClass, clazz)) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        if (ObjectUtil.isEmpty(fields)) {
            return;
        }

        for (Field field : fields) {
            if (JavaUtil.isFinal(field)) {
                continue;
            }
            Class<?> fieldClassType = field.getType();
            String fieldName = field.getName();


            List<Annotation> fieldAnnotations = getValidAnnotation(field.getDeclaredAnnotations());
            if (ObjectUtil.isNotEmpty(fieldAnnotations)) {

                for (Annotation annotation : fieldAnnotations) {

                    validated(validated, params,paramIndex, fieldClassType, fieldName, annotation, field , language);
                }

            }else{

                Valid valid = field.getDeclaredAnnotation(Valid.class);
                String typeName = fieldClassType.getTypeName();
                if (JavaUtil.isArray(typeName)) {
                    if (valid==null){
                        continue;
                    }
                    validArrayObject(validated,field,params,paramIndex,language);
                } else if (JavaUtil.isCollection(typeName)) {
                    if (valid==null){
                        continue;
                    }
                    validListObject(validated,field, params,paramIndex,language);
                } else {
                    //验证参数属性
                    entityFieldsAnnotationValid(validated , fieldClassType, params, paramIndex ,language);
                }

            }
        }

    }



    private void checkClassSuper(Validated validated, Class clazz, Object[] params,Integer valueIndex,String language) {
        Class superclass = clazz.getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {
            //如果不是定义的类型，则把 class 当做bean 进行校验 field
            entityFieldsAnnotationValid(validated , superclass, params,valueIndex ,language);
        }
    }

    @Override
    public void validReturnValue(Method method, Object argument,String language) {
        Validated validated = getValidated(method);
        if (validated==null){
            return;
        }
        if (!validated.validReturnValue()){
            return;
        }

        try {
            if (JavaUtil.isNotPrimitive(argument.getClass().getName())) {
                //验证参数属性
                entityFieldsAnnotationValid(validated, argument.getClass(), new Object[]{argument}, 0, language);
            }
            if (!validated.failFast()) {
                ExceptionUtil.throwException();
            }
        } finally {
            if (!validated.failFast()) {
                ExceptionUtil.reset();
            }
        }
    }

    private Validated getValidated(Method method){
        Validated validated = method.getDeclaredAnnotation(Validated.class);
        if (validated==null){
            validated = method.getDeclaringClass().getDeclaredAnnotation(Validated.class);
        }
        return validated;
    }



    @Override
    public void validParameter(Method method, Object[] arguments,String language) {
        Validated validated = getValidated(method);
        if (validated==null){
            return;
        }

        Parameter[] parameters = method.getParameters();
        if (ObjectUtil.isEmpty(parameters)) {
            return;
        }

        try {
            for (int j = 0; j < parameters.length; j++) {

                Parameter parameter = parameters[j];
                Class<?> classType = parameter.getType();
                String parameterTypeName = classType.getTypeName();
                if (JavaUtil.isMvcIgnoreParams(parameterTypeName)) {
                    continue;
                }

                List<Annotation> annotationList = getValidAnnotation(parameter.getDeclaredAnnotations());
                if (ObjectUtil.isNotEmpty(annotationList)){

                    for (Annotation parameterAnnotation : annotationList) {

                        validated(validated, arguments, j, parameter.getType(), parameter.getName(), parameterAnnotation, null,language);
                    }

                }else{

                    Valid valid = parameter.getDeclaredAnnotation(Valid.class);
                    if (JavaUtil.isArray(parameterTypeName)) {
                        if (valid == null) {
                            continue;
                        }
                        validArrayObject(validated, parameter.getType(), arguments, j, parameter.getName());
                    } else if (JavaUtil.isCollection(parameterTypeName)) {
                        if (valid == null) {
                            continue;
                        }
                        validListObject(validated, parameter, arguments, j, language);
                    } else {
                        //验证参数属性
                        entityFieldsAnnotationValid(validated, classType, arguments, j , language);
                    }

                }
            }

            if (!validated.failFast()) {
                ExceptionUtil.throwException();
            }
        } finally {
            if (!validated.failFast()) {
                ExceptionUtil.reset();
            }
        }
    }


    public void validArrayObject(Validated validated, Class objectClass,Object[] params,Integer paramIndex, String language) {
        Class convertClass = objectClass.getComponentType();
        if (JavaUtil.isNotPrimitive(convertClass.getTypeName())) {
            Object[] array = (Object[]) params[paramIndex];
            if (ObjectUtil.isNotEmpty(array)) {
                for (int objIndex = 0; objIndex < array.length; objIndex++) {
                    entityFieldsAnnotationValid(validated, convertClass, array , objIndex,language);
                }
            }
        }
    }

    public void validArrayObject(Validated validated, Field field,Object[] params,Integer paramIndex,String language) {
        Class convertClass = field.getType().getComponentType();
        if (JavaUtil.isNotPrimitive(convertClass.getTypeName())) {
            Object[] array = (Object[]) MethodUtil.getFieldValue(field, params[paramIndex]);
            if (ObjectUtil.isNotEmpty(array)) {
                for (int objIndex = 0; objIndex < array.length; objIndex++) {
                    entityFieldsAnnotationValid(validated,  convertClass, array , objIndex,language);
                }
            }
        }
    }


    public void validListObject(Validated validated,Parameter parameter,Object[] params ,Integer paramIndex,String language ){
        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(parameter);
        if (ObjectUtil.isNotEmpty(actualTypeArguments) &&
            actualTypeArguments[0] instanceof Class &&
            JavaUtil.isNotPrimitive(actualTypeArguments[0].getTypeName())) {

            Class typeConvertClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
            if (typeConvertClass != null && params[paramIndex] instanceof List) {
                List param = (List) params[paramIndex];

                if (ObjectUtil.isNotEmpty(param)) {
                    for (int listIndex = 0; listIndex < param.size(); listIndex++) {

                        entityFieldsAnnotationValid(validated,  typeConvertClass, param.toArray(),listIndex ,language);
                    }
                }
            }
        }
    }

    public void validListObject(Validated validated,Field field,Object[] params ,Integer paramIndex,String language ){
        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(field);
        if (ObjectUtil.isNotEmpty(actualTypeArguments) &&
            actualTypeArguments[0] instanceof Class &&
            JavaUtil.isNotPrimitive(actualTypeArguments[0].getTypeName())) {

            Class typeConvertClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
            if (typeConvertClass != null ) {
                List param = (List)MethodUtil.getFieldValue(field, params[paramIndex]);
                if (ObjectUtil.isNotEmpty(param)) {
                    for (int listIndex = 0; listIndex < param.size(); listIndex++) {

                        entityFieldsAnnotationValid(validated,  typeConvertClass, param.toArray(),listIndex,language);
                    }
                }
            }
        }
    }

    private List<Annotation> getValidAnnotation(Annotation[] annotations) {
        if (ObjectUtil.isNotEmpty(annotations)) {
            return Arrays.stream(annotations)
                .filter(annotation -> isValidAnnotation(annotation))
                .collect(Collectors.toList());
        }
        return null;
    }

    private boolean isValidAnnotation(Annotation annotation){
        return annotation!=null && (ConstraintHelper.containsKey(annotation.annotationType()) || annotation.annotationType().isAnnotationPresent(Constraint.class));
    }


    private void validated(Validated validated,
                           Object[] params,
                           Integer paramIndex,
                           Class<?> valueType,
                           String paramName,
                           Annotation annotation,
                           Field field,String language) {


        if (checkValidatedGroups(validated,annotation)) {
            return;
        }

        List<ConstraintValidator> constraintValidatorList = getAnnotationConstraintValidator(annotation);
        if (ObjectUtil.isEmpty(constraintValidatorList)){
            return;
        }

        validatedConstrain(validated,annotation,constraintValidatorList, params,paramIndex, paramName, valueType, field,language);
    }

    private boolean checkValidatedGroups(Validated validated,Annotation annotation){
        Class<?>[] validatedGroups = validated.groups();
        if (ObjectUtil.isEmpty(validatedGroups)) {
            return false;
        }
        //issue#6 如果注解 groups 为空，则默认 annotation 注解 groups=DefaultGroup.class
        if (checkGroup(DefaultGroup.class,validatedGroups)) {
            return false;
        }
        Class[] annotationGroups = MethodUtil.getAnnotationGroups(annotation);
        if (ObjectUtil.isEmpty(annotationGroups)){
            return false;
        }
        return !checkGroup(validatedGroups, annotationGroups);
    }

    private List<ConstraintValidator> getAnnotationConstraintValidator(Annotation annotation){
        List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getConstraint(annotation.annotationType());
        if (ObjectUtil.isNotEmpty(constraintValidatorList)){
            return constraintValidatorList;
        }
        Constraint constraint = annotation.annotationType().getDeclaredAnnotation(Constraint.class);
        if (constraint==null){
            return null;
        }
        Class<? extends ConstraintValidator<? extends Annotation, ?>>[] classes = constraint.validatedBy();
        if (ObjectUtil.isEmpty(classes)) {
            return null;
        }
        ConstraintHelper.putConstraintValidator(annotation.annotationType(), classes);
        return ConstraintHelper.getConstraint(annotation.annotationType());
    }


    private void validatedConstrain(Validated validated,
                                    Annotation annotation,
                                    List<ConstraintValidator> constraintValidatorList,
                                    Object[] params,
                                    Integer index,
                                    String paramName,
                                    Class valueType,
                                    Field field,
                                    String language) {

        if (ObjectUtil.isNotEmpty(constraintValidatorList)) {
            Object value = params[index];
            if (field != null) {
                value = MethodUtil.getFieldValue(field, params[index]);
            }
            for (int i = 0; i < constraintValidatorList.size(); i++) {

                ConstraintValidator constraintValidator = constraintValidatorList.get(i);
                boolean isValid = constraintValidator.isValid(annotation, value, valueType);
                if (!isValid) {
                    String msg  = getAnnotationMsg(annotation,language); //issue#8
                    if (validated.failFast()) {
                        ValidatedException.throwMsg(paramName, msg, annotation.annotationType().getName(), value,index);
                    } else {
                        addMarsViolations(value, paramName, annotation, msg,index);
                    }
                }

                Class validConstraintClass = constraintValidator.getClass();
                if (MethodUtil.checkDeclaredMethod(validConstraintClass, ValidatedConst.METHOD_NAME_MODIFY)) {
                    Object reValue = constraintValidator.modify(annotation, value, valueType);
                    if (reValue != null) {
                        if (field != null) {
                            Object param = params[index];
                            Object formatValue = ObjectUtil.formatObject(reValue, field.getType());
                            if (formatValue != null && field.getType() == formatValue.getClass()) {
                                try {
                                    field.set(param, formatValue);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                log.error("mars valid error setField fieldName:{} annotation:{} oldType:{} newType:{} ", field.getName(), annotation.annotationType().getName(), field.getType(), reValue.getClass());
                            }
                        } else {
                            params[index] = ObjectUtil.formatObject(reValue, valueType);
                        }
                    }
                }
            }
        }
    }


    private String getAnnotationMsg(Annotation annotation, String language) {
        String annotationMsg = MethodUtil.getAnnotationMsg(annotation);
        String filterMsg = ValidatorUtil.filterMsg(annotationMsg, language);
        if (GenericTokenUtil.isOpenToken(filterMsg, ValidatedConst.OPEN_TOKEN)) {
            Map<String, Object> annotationAttributes = MethodUtil.getAnnotationAttributes(annotation);
            return GenericTokenUtil.parse(filterMsg, annotationAttributes);
        }
        return filterMsg;
    }




    private void addMarsViolations(Object value, String paramName, Annotation annotation, String msg,Integer valueIndex) {
        ExceptionUtil.addMarsViolation(MarsViolation.builder()
                .annotationName(annotation.annotationType().getName())
                .fieldName(paramName)
                .msg(msg)
                .value(value)
                .valueIndex(valueIndex)
            .build());
    }


    private boolean isValidClass(Class<?>[] validClass, Class<?> clazz) {
        if (ObjectUtil.isEmpty(validClass)) {
            return true;
        }
        return Arrays.asList(validClass).contains(clazz);
    }


    private boolean checkGroup(Class<?>[] vGroup, Class<?>[] aGroup) {
        if (ObjectUtil.isNotEmpty(aGroup)) {
            if (log.isDebugEnabled()) {
                log.debug("@Validated groups:{}  annotation groups:{}", vGroup, aGroup);
            }
            for (Class v : vGroup) {
                if (checkGroup(v, aGroup)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkGroup(Class clazz, Class[] classes) {
        for (Class c : classes) {
            if (clazz.equals(c)) {
                return true;
            }
        }
        return false;
    }




}
