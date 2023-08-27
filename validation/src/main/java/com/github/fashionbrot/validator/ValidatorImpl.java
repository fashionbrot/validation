package com.github.fashionbrot.validator;

import com.github.fashionbrot.common.util.GenericTokenUtil;
import com.github.fashionbrot.common.util.JavaUtil;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.common.util.TypeUtil;
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

    public void entityFieldsAnnotationValid(Validated validated, Class<?> clazz, Object[] params,Integer parameterIndex,String language) {


        // 判断是否 有继承类
        checkClassSuper(validated, clazz, params,parameterIndex,language);

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
            if (com.github.fashionbrot.common.util.MethodUtil.isStatic(field) || com.github.fashionbrot.common.util.MethodUtil.isFinal(field)) {
                continue;
            }
            Class<?> fieldClassType = field.getType();
            String fieldName = field.getName();


            List<Annotation> fieldAnnotations = getValidAnnotation(field.getDeclaredAnnotations());
            if (ObjectUtil.isNotEmpty(fieldAnnotations)) {

                for (Annotation annotation : fieldAnnotations) {

                    validated(validated, params,parameterIndex, fieldClassType, fieldName, annotation, field , language);
                }

            }else{

                Valid valid = field.getDeclaredAnnotation(Valid.class);
                if (valid==null){
                    if (JavaUtil.isPrimitive(field.getType())){
                        continue;
                    }
                    //验证参数属性
                    entityFieldsAnnotationValid(validated , fieldClassType, params, parameterIndex ,language);
                }else{
                    String typeName = fieldClassType.getTypeName();
                    if (JavaUtil.isArray(typeName)) {
                        validArrayObject(validated,field,params,parameterIndex,language);
                    } else if (JavaUtil.isCollection(fieldClassType)) {
                        validListObject(validated,field, params,parameterIndex,language);
                    }
                }
            }
        }

    }



    private void checkClassSuper(Validated validated, Class clazz, Object[] params,Integer parameterIndex,String language) {
        if (JavaUtil.isPrimitive(clazz)){//#issue5 修复
            return;
        }
        Class superclass = clazz.getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {
            //如果不是定义的类型，则把 class 当做bean 进行校验 field
            entityFieldsAnnotationValid(validated , superclass, params,parameterIndex ,language);
        }
    }



    @Override
    public void validReturnValue(Method method, Object argument, String language) {
        Validated validated = getValidated(method);
        if (validated == null || ObjectUtil.isFalse(validated.validReturnValue())) {
            return;
        }
        if (argument==null){
            return;
        }
        if (ObjectUtil.equals(argument.getClass(),Void.class)){
            return;
        }
        if (JavaUtil.isPrimitive(argument.getClass())) {
            return;
        }

        try {
            //验证参数属性
            entityFieldsAnnotationValid(validated, argument.getClass(), new Object[]{argument}, 0, language);
            if (ObjectUtil.isFalse(validated.failFast())) {
                ExceptionUtil.throwException();
            }
        } finally {
            if (ObjectUtil.isFalse(validated.failFast())) {
                ExceptionUtil.reset();
            }
        }
    }

    private Validated getValidated(Method method){
        return method.getDeclaredAnnotation(Validated.class);
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
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {

                Parameter parameter = parameters[parameterIndex];
                Class<?> classType = parameter.getType();
                String parameterTypeName = classType.getTypeName();

                List<Annotation> annotationList = getValidAnnotation(parameter.getDeclaredAnnotations());
                if (ObjectUtil.isNotEmpty(annotationList)){

                    for (Annotation parameterAnnotation : annotationList) {

                        validated(validated, arguments, parameterIndex , parameter.getType(), parameter.getName(), parameterAnnotation, null,language);
                    }

                }else{

                    Valid valid = parameter.getDeclaredAnnotation(Valid.class);
                    if (valid==null){
                        if (JavaUtil.isPrimitive(parameter.getType())){
                            continue;
                        }
                        //验证参数属性
                        entityFieldsAnnotationValid(validated, classType, arguments, parameterIndex , language);
                    }else{
                        if (JavaUtil.isArray(parameterTypeName)) {
                            validArrayObject(validated, parameter.getType(), arguments, parameterIndex , parameter.getName());
                        } else if (JavaUtil.isCollection(classType)) {
                            validListObject(validated, parameter, arguments, parameterIndex , language);
                        }
                    }
                }
            }

            if (ObjectUtil.isFalse(validated.failFast())) {
                ExceptionUtil.throwException();
            }
        } finally {
            if (ObjectUtil.isFalse(validated.failFast())) {
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

            Class typeConvertClass = TypeUtil.convertTypeToClass(actualTypeArguments[0]);
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

            Class typeConvertClass = TypeUtil.convertTypeToClass(actualTypeArguments[0]);
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


        if (!isValidatedGroups(validated,annotation)) {
            return;
        }

        List<ConstraintValidator> constraintValidatorList = getAnnotationConstraintValidator(annotation);
        if (ObjectUtil.isEmpty(constraintValidatorList)){
            return;
        }

        validatedConstrain(validated,annotation,constraintValidatorList, params,paramIndex, paramName, valueType, field,language);
    }

    /**
     * true 包含 false 不包含
     * @param validated
     * @param annotation
     * @return boolean
     */
    private boolean isValidatedGroups(Validated validated,Annotation annotation){
        Class<?>[] validatedGroups = validated.groups();
        if (ObjectUtil.isEmpty(validatedGroups) ) {
            return true;
        }else{

            Class[] annotationGroups = MethodUtil.getAnnotationGroups(annotation);
            if (ObjectUtil.isNotEmpty(annotationGroups)) {
                return checkGroup(validatedGroups, annotationGroups);
            }else{
                return checkGroup(DefaultGroup.class, validatedGroups);
            }
        }
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
                    String msg  = getAnnotationMsg(annotation,language);
                    if (validated.failFast()) {
                        ValidatedException.throwMsg(paramName, msg, annotation.annotationType().getSimpleName(), value,index);
                    } else {
                        addMarsViolations(value, paramName, annotation, msg,index);
                    }
                }

                Class validConstraintClass = constraintValidator.getClass();
                if (MethodUtil.checkDeclaredMethod(validConstraintClass, ValidatedConst.METHOD_NAME_MODIFY)) {
                    Object reValue = constraintValidator.modify(annotation, value, valueType);
                    if (reValue==null){
                        return;
                    }
                    if (reValue.getClass()!=valueType){
                        log.warn("default value reValue class!=valueType");
                    }
                    if (field==null){
                        params[index] = reValue;
                    }else{
                        MethodUtil.setField(field,params[index],reValue);
                    }
                }
            }
        }
    }


    private String getAnnotationMsg(Annotation annotation, String language) {
        String annotationMsg = MethodUtil.getAnnotationMsg(annotation);
        String filterMsg = ValidatorUtil.filterMsg(annotationMsg, language);
        if (GenericTokenUtil.isOpenToken(filterMsg, ValidatedConst.OPEN_TOKEN)) {
            Map<String, Object> annotationAttributes = MethodUtil.getAnnotationMapExcludeMsgAndGroups(annotation);
            return GenericTokenUtil.parse(filterMsg, annotationAttributes);
        }
        return filterMsg;
    }




    private void
    addMarsViolations(Object value, String paramName, Annotation annotation, String msg,Integer valueIndex) {
        ExceptionUtil.addMarsViolation(MarsViolation.builder()
                .annotationName(annotation.annotationType().getSimpleName())
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
