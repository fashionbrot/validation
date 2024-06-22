package com.github.fashionbrot;

import com.github.fashionbrot.annotation.Valid;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.annotation.ValidatedParam;
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
import com.github.fashionbrot.util.ExceptionUtil;
import com.github.fashionbrot.util.MethodUtil;
import com.github.fashionbrot.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fashionbrot
 */
@Slf4j
public class ValidationConfiguration  {

    private Validated validated;
    private Method method;
    private String language;
    private List<Violation> violationList=new ArrayList<>();

    private ValidationConfiguration() {
    }

    public ValidationConfiguration(Method method, String language) {
        this.method = method;
        this.validated = method.getDeclaredAnnotation(Validated.class);
        this.language = language;
    }

    public void validParameter(Object[] arguments) {
        Parameter[] parameters = method.getParameters();
        validationParameters(parameters,arguments);
    }

    private void validationParameters(Parameter[] parameters,Object[] params) {
        if (validated == null) {
            return;
        }
        if (ObjectUtil.isEmpty(parameters)) {
            return;
        }
        for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
            validationParameter(parameters,params,parameterIndex);
        }
        if (ObjectUtil.isFalse(validated.failFast())) {
            ExceptionUtil.throwException(violationList);
        }
    }

    private void validationParameter(Parameter[] parameters,Object[] params,int parameterIndex) {
        List<Annotation> constraintAnnotationList = getConstraintAnnotation(parameters[parameterIndex].getDeclaredAnnotations());
        if (ObjectUtil.isNotEmpty(constraintAnnotationList)) {
            for (int annotationIndex = 0; annotationIndex < constraintAnnotationList.size(); annotationIndex++) {
                Annotation annotation = constraintAnnotationList.get(annotationIndex);
                validationParameterAnnotation(parameters,annotation,params, parameterIndex);
            }
        } else {
            Parameter parameter = parameters[parameterIndex];
            Class<?> parameterType = parameter.getType();

            Valid valid = parameter.getDeclaredAnnotation(Valid.class);
            if (valid!=null){
                if (JavaUtil.isArray(parameterType)) {
                    validArrayObject( parameters, params,  parameterIndex);
                } else if (JavaUtil.isCollection(parameterType)) {
                    validListObject( parameters, params, parameterIndex );
                }
            }else{
                if (JavaUtil.isNotPrimitive(parameter.getType())){
                    entityFieldsAnnotationValid( parameters,parameterType, params, parameterIndex );
                }
            }
        }
    }

    private void validArrayObject(Parameter[] parameters,Object[] params,Integer parameterIndex) {
        Class<?> parameterType = parameters[parameterIndex].getType();
        Class convertClass = parameterType.getComponentType();
        if (JavaUtil.isNotPrimitive(convertClass.getTypeName())) {
            Object[] array = (Object[]) params[parameterIndex];
            if (ObjectUtil.isNotEmpty(array)) {
                for (int objIndex = 0; objIndex < array.length; objIndex++) {
                    entityFieldsAnnotationValid(parameters,convertClass, array , objIndex);
                }
            }
        }
    }

    private void validArrayObject(Parameter[] parameters,Field field,Object[] params,Integer parameterIndex) {
        Class convertClass = field.getType().getComponentType();
        if (JavaUtil.isNotPrimitive(convertClass.getTypeName())) {
            Object[] array = (Object[]) MethodUtil.getFieldValue(field, params[parameterIndex]);
            if (ObjectUtil.isNotEmpty(array)) {
                for (int objIndex = 0; objIndex < array.length; objIndex++) {
                    entityFieldsAnnotationValid(parameters,convertClass, array , objIndex);
                }
            }
        }
    }

    private void validListObject(Parameter[] parameters,Object[] params,Integer parameterIndex ){
        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(parameters[parameterIndex]);
        if (ObjectUtil.isNotEmpty(actualTypeArguments) &&
            actualTypeArguments[0] instanceof Class &&
            JavaUtil.isNotPrimitive(actualTypeArguments[0].getTypeName())) {

            Class typeConvertClass = TypeUtil.convertTypeToClass(actualTypeArguments[0]);
            if (typeConvertClass != null && params[parameterIndex] instanceof List) {
                List param = (List) params[parameterIndex];

                if (ObjectUtil.isNotEmpty(param)) {
                    for (int listIndex = 0; listIndex < param.size(); listIndex++) {

                        entityFieldsAnnotationValid( parameters,typeConvertClass, param.toArray(),listIndex );
                    }
                }
            }
        }
    }

    public void validListObject(Parameter[] parameters,Field field,Object[] params,Integer parameterIndex){
        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(field);
        if (ObjectUtil.isNotEmpty(actualTypeArguments) &&
            actualTypeArguments[0] instanceof Class &&
            JavaUtil.isNotPrimitive(actualTypeArguments[0].getTypeName())) {

            Class typeConvertClass = TypeUtil.convertTypeToClass(actualTypeArguments[0]);
            if (typeConvertClass != null ) {
                List param = (List)MethodUtil.getFieldValue(field, params[parameterIndex]);
                if (ObjectUtil.isNotEmpty(param)) {
                    for (int listIndex = 0; listIndex < param.size(); listIndex++) {

                        entityFieldsAnnotationValid(parameters,typeConvertClass, param.toArray(),listIndex);
                    }
                }
            }
        }
    }

    private void entityFieldsAnnotationValid(Parameter[] parameters,Class entityClass,Object[] params,Integer parameterIndex) {
        // 判断是否 有继承类
        checkClassSuper(parameters,entityClass,params,parameterIndex);

        Field[] fields = entityClass.getDeclaredFields();
        if (ObjectUtil.isEmpty(fields)) {
            return;
        }

        for (Field field : fields) {
            if (MethodUtil.isStaticOrFinal(field)) {
                continue;
            }

            List<Annotation> constraintAnnotationList = getConstraintAnnotation(field.getDeclaredAnnotations());
            if (ObjectUtil.isNotEmpty(constraintAnnotationList)) {

                for (Annotation annotation : constraintAnnotationList) {
                    validationFieldAnnotation(parameters,params,parameterIndex,field,annotation);
                }

            }else{
                Class<?> fieldClassType = field.getType();
                Valid valid = field.getDeclaredAnnotation(Valid.class);
                if (valid!=null){
                    if (JavaUtil.isArray(fieldClassType)) {
                        validArrayObject(parameters,field,params,parameterIndex);
                    } else if (JavaUtil.isCollection(fieldClassType)) {
                        validListObject(parameters,field,params,parameterIndex);
                    }
                }else{
                    if (JavaUtil.isNotPrimitive(field.getType())){
                        entityFieldsAnnotationValid(parameters,fieldClassType, params, parameterIndex );
                    }
                }
            }
        }
    }


    private void checkClassSuper(Parameter[] parameters,Class entityClass,Object[] params, Integer parameterIndex) {
        if (JavaUtil.isPrimitive(entityClass)){//#issue5 修复
            return;
        }
        Class superclass = entityClass.getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {
            //如果不是定义的类型，则把 class 当做bean 进行校验 field
            entityFieldsAnnotationValid(parameters,superclass,params,parameterIndex);
        }
    }

    private void validationParameterAnnotation(Parameter[] parameters,Annotation annotation,Object[] params, int parameterIndex) {
        if (!checkAnnotationGroups(annotation)) {
            return;
        }

        List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getAnnotationConstraintValidator(annotation);
        if (ObjectUtil.isNotEmpty(constraintValidatorList)) {
            for (int constraintIndex = 0; constraintIndex < constraintValidatorList.size(); constraintIndex++) {
                ConstraintValidator constraintValidator = constraintValidatorList.get(constraintIndex);
                constraintValidatorParameter(parameters,annotation,constraintValidator,params, parameterIndex);
            }
        }
    }

    private void validationFieldAnnotation(Parameter[] parameters,Object[] params,int parameterIndex,Field field,Annotation annotation){
        if (!checkAnnotationGroups(annotation)) {
            return;
        }
        List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getAnnotationConstraintValidator(annotation);
        if (ObjectUtil.isNotEmpty(constraintValidatorList)) {
            for (int constraintIndex = 0; constraintIndex < constraintValidatorList.size(); constraintIndex++) {
                ConstraintValidator constraintValidator = constraintValidatorList.get(constraintIndex);
                constraintValidatorField(parameters,field,annotation,constraintValidator,params, parameterIndex);
            }
        }
    }

    private void constraintValidatorField(Parameter[] parameters,
                                          Field field,
                                          Annotation annotation,
                                          ConstraintValidator constraintValidator,
                                          Object[] params,
                                          int parameterIndex) {
        Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
        if (ObjectUtil.isEmpty(annotationMethods)) {
            return;
        }
        Object fieldValue = MethodUtil.getFieldValue(field, params[parameterIndex]);
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();

        Class validConstraintClass = constraintValidator.getClass();
        Boolean expression = checkExpressionField(parameters,params,field,annotation,annotationMethods);
        if (ObjectUtil.isTrue(expression)){
            boolean isValid = constraintValidator.isValid(annotation, fieldValue, fieldType);
            if (!isValid) {
                String msg  = getAnnotationMsg(annotation,language);
                addViolations(fieldValue, fieldName, annotation, msg, parameterIndex);
            }
        }

        if (MethodUtil.checkDeclaredMethod(validConstraintClass, ValidatedConst.METHOD_NAME_MODIFY)) {
            Object reValue = constraintValidator.modify(annotation, fieldValue, fieldType);
            if (reValue==null){
                return;
            }
            if (reValue.getClass()!= fieldType){
                log.warn("default value reValue class!=valueType");
            }
            MethodUtil.setField(field,params[parameterIndex],reValue);
        }
    }


    private void constraintValidatorParameter(Parameter[] parameters,
                                    Annotation annotation,
                                    ConstraintValidator constraintValidator,
                                    Object[] params,
                                    int parameterIndex) {

        Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
        if (ObjectUtil.isEmpty(annotationMethods)) {
            return;
        }

        Object parameterValue = params[parameterIndex];
        Parameter parameter = parameters[parameterIndex];
        String parameterName = parameter.getName();
        Class<?> parameterType = parameter.getType();

        Boolean expression = checkExpressionParameter(parameters,params,annotation,annotationMethods);
        if (ObjectUtil.isTrue(expression)){
            boolean isValid = constraintValidator.isValid(annotation, parameterValue, parameterType);
            if (!isValid) {
                String msg  = getAnnotationMsg(annotation,language);
                addViolations(parameterValue, parameterName, annotation, msg, parameterIndex);
            }
        }

        Class validConstraintClass = constraintValidator.getClass();
        if (MethodUtil.checkDeclaredMethod(validConstraintClass, ValidatedConst.METHOD_NAME_MODIFY)) {
            Object reValue = constraintValidator.modify(annotation, parameterValue, parameterType);
            if (reValue==null){
                return;
            }
            if (reValue.getClass()!= parameterType){
                log.warn("default value reValue class!=valueType");
            }
            params[parameterIndex] = reValue;
        }
    }

    private Boolean checkExpressionParameter(Parameter[] parameters,Object[] params,Annotation annotation,Method[] annotationMethods){
        Method expressionMethod = MethodUtil.filterMethodName(annotationMethods, ValidatedConst.EXPRESSION);
        if (expressionMethod == null) {
            return true;
        }
        String expression = (String) MethodUtil.getReturnValue(expressionMethod, annotation);
        if (ObjectUtil.isEmpty(expression)) {
            return true;
        }
        int initialCapacity = (int) (parameters.length / 0.75) + 1;
        Map<String,Object> rootMap = new HashMap<>(initialCapacity);
        for (int j = 0; j < parameters.length; j++) {
            Parameter parameter = parameters[j];
            Object argument = params[j];
            rootMap.put(parameter.getName(),argument);
        }
        return OgnlCache.executeExpression(expression, rootMap);
    }

    private Boolean checkExpressionField(Parameter[] parameters,Object[] params,Field field,Annotation annotation,Method[] annotationMethods){
        Method expressionMethod = MethodUtil.filterMethodName(annotationMethods, ValidatedConst.EXPRESSION);
        if (expressionMethod == null) {
            return true;
        }
        String expression = (String) MethodUtil.getReturnValue(expressionMethod, annotation);
        if (ObjectUtil.isEmpty(expression)) {
            return true;
        }
        Map<String, Object> rootMap = createRootMap(parameters, params, field);
        return OgnlCache.executeExpression(expression, rootMap);
    }

    private Map<String, Object> createRootMap(Parameter[] parameters, Object[] params, Field field) {
        int initialCapacity = (int) (parameters.length / 0.75) + 1;
        Map<String, Object> rootMap = new HashMap<>(initialCapacity);
        for (int i = 0; i < parameters.length; i++) {
            String name = getClassParamName(parameters[i], field);
            rootMap.put(name, params[i]);
        }
        return rootMap;
    }

    private String getClassParamName(Parameter parameter, Field field) {
        ValidatedParam validatedParam = parameter.getDeclaredAnnotation(ValidatedParam.class);
        if (validatedParam == null) {
            validatedParam = field.getDeclaringClass().getDeclaredAnnotation(ValidatedParam.class);
        }
        return validatedParam != null ? validatedParam.value() : captureName(field.getDeclaringClass().getSimpleName());
    }

    private String getAnnotationMsg(Annotation annotation, String language) {
        String annotationMsg = MethodUtil.getAnnotationMsg(annotation);
        String filterMsg = ValidatorUtil.filterMessage(annotationMsg, language);
        if (GenericTokenUtil.isOpenToken(filterMsg, ValidatedConst.OPEN_TOKEN)) {
            Map<String, Object> annotationAttributes = MethodUtil.getAnnotationMapExcludeMsgAndGroups(annotation);
            return GenericTokenUtil.parse(filterMsg, annotationAttributes);
        }
        return filterMsg;
    }

    private void addViolations(Object value, String paramName, Annotation annotation, String msg,Integer valueIndex) {
        if (validated.failFast()) {
            ValidatedException.throwMsg(paramName, msg, annotation.annotationType().getSimpleName(), value, valueIndex);
        } else {
            violationList.add(Violation.builder()
                .annotationName(annotation.annotationType().getSimpleName())
                .fieldName(paramName)
                .msg(msg)
                .value(value)
                .valueIndex(valueIndex)
                .build());
        }
    }

    private boolean checkAnnotationGroups(Annotation annotation) {
        Class<?>[] validatedGroups = validated.groups();
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

    private boolean checkGroup(Class<?>[] vGroup, Class<?>[] aGroup) {
        if (ObjectUtil.isEmpty(aGroup)) {
            return false;
        }
        if (log.isDebugEnabled()) {
            log.debug("@Validated groups:{}  annotation groups:{}", vGroup, aGroup);
        }
        for (Class v : vGroup) {
            if (checkGroup(aGroup, v)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkGroup(Class[] groups, Class group) {
        for (Class g : groups) {
            if (group.equals(g)) {
                return true;
            }
        }
        return false;
    }


    private List<Annotation> getConstraintAnnotation(Annotation[] annotations) {
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

    private boolean isConstraintAnnotation(Annotation annotation) {
        return annotation != null && (ConstraintHelper.containsKey(annotation.annotationType()) || annotation.annotationType().isAnnotationPresent(Constraint.class));
    }

    private static String captureName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        char[] cs = name.toCharArray();
        cs[0] = Character.toLowerCase(cs[0]);
        return new String(cs);
    }

}
