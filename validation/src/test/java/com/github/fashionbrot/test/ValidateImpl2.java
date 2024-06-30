//package com.github.fashionbrot.test;
//
//import com.github.fashionbrot.annotation.ValidatedParam;
//import com.github.fashionbrot.common.util.ObjectUtil;
//import com.github.fashionbrot.constraint.ConstraintHelper;
//import com.github.fashionbrot.constraint.ConstraintValidator;
//import com.github.fashionbrot.constraint.Violation;
//import com.github.fashionbrot.consts.ValidatedConst;
//import com.github.fashionbrot.groups.DefaultGroup;
//import com.github.fashionbrot.ognl.OgnlCache;
//import com.github.fashionbrot.util.MethodUtil;
//import com.github.fashionbrot.validate.MetaConstraint;
//import com.github.fashionbrot.validate.ValidateCache;
//import lombok.extern.slf4j.Slf4j;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author fashionbrot
// */
//@Slf4j
//public class ValidateImpl2 {
//
//    private Class[] validatedGroups;
//    private boolean failFast;
//    private String language;
//    private String springProfilesActive;
//    private boolean fail;
//
//    private ValidateImpl2(){
//
//    }
//
//    public ValidateImpl2(Class[] validatedGroups, boolean failFast, String language, String springProfilesActive) {
//        this.validatedGroups = validatedGroups;
//        this.failFast = failFast;
//        this.language = language;
//        this.springProfilesActive = springProfilesActive;
//    }
//
//    public  List<Violation> validate(Parameter[] parameters, Object[] parameterValues){
//        if (ObjectUtil.isEmpty(parameters)){
//            return null;
//        }
//        return validateParameter(parameters,parameterValues);
//    }
//
//    public  List<Violation> validate(Object[] parameterValues,int parameterIndex){
//
//        return null;
//    }
//
//
//
//    private  List<Violation> validateParameter(Parameter[] parameters,Object[] parameterValues) {
//
//        for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
//            if (fail){
//                break;
//            }
//            Parameter parameter = parameters[parameterIndex];
//            Class<?> parameterType = parameter.getType();
//
//            List<Annotation> constraintAnnotationList = ValidateCache.getConstraintAnnotation(parameter.getDeclaredAnnotations());
//            if (ObjectUtil.isNotEmpty(constraintAnnotationList)) {
//                for (int annotationIndex = 0; annotationIndex < constraintAnnotationList.size(); annotationIndex++) {
//                    if (fail){
//                        break;
//                    }
//                    Annotation annotation = constraintAnnotationList.get(annotationIndex);
//                    if (!checkAnnotationGroups(annotation)) {
//                        continue;
//                    }
//
//                    List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getAnnotationConstraintValidator(annotation);
//                    if (ObjectUtil.isEmpty(constraintValidatorList)){
//                        continue;
//                    }
//                    if (ObjectUtil.isNotEmpty(constraintValidatorList)) {
//                        for (int constraintIndex = 0; constraintIndex < constraintValidatorList.size(); constraintIndex++) {
//                            ConstraintValidator constraintValidator = constraintValidatorList.get(constraintIndex);
//                            constraintValidatorParameter(parameters,annotation,constraintValidator,parameterValues, parameterIndex);
//                        }
//                    }
//                }
//            } else {
//
////                Valid valid = parameter.getDeclaredAnnotation(Valid.class);
////                if (valid!=null){
////                    if (JavaUtil.isArray(parameterType)) {
////                        validArrayObject( parameters, params,  parameterIndex);
////                    } else if (JavaUtil.isCollection(parameterType)) {
////                        validListObject( parameters, params, parameterIndex );
////                    }
////                }else{
////                    if (JavaUtil.isNotPrimitive(parameter.getType())){
////                        entityFieldsAnnotationValid(parameterType, params, parameterIndex );
////                    }
////                }
//            }
//
//        }
//
//
//        return null;
//    }
//
//    private void constraintValidatorParameter(Parameter[] parameters,
//                                              Annotation annotation,
//                                              ConstraintValidator constraintValidator,
//                                              Object[] params,
//                                              int parameterIndex) {
//
//        Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
//        if (ObjectUtil.isEmpty(annotationMethods)) {
//            return;
//        }
//
//        Object parameterValue = params[parameterIndex];
//        Parameter parameter = parameters[parameterIndex];
//        String parameterName = parameter.getName();
//        Class<?> parameterType = parameter.getType();
////
////        Boolean expression = checkExpressionParameter(parameters,params,annotation,annotationMethods);
////        if (ObjectUtil.isTrue(expression)){
////            boolean isValid = constraintValidator.isValid(annotation, parameterValue, parameterType);
////            if (!isValid) {
////                String msg  = getAnnotationMsg(annotation,annotationMethods);
////                addViolations(parameterValue, parameterName, annotation, msg, parameterIndex);
////            }
////        }
//
//        Class validConstraintClass = constraintValidator.getClass();
//        if (MethodUtil.checkDeclaredMethod(validConstraintClass, ValidatedConst.METHOD_NAME_MODIFY)) {
//            Object reValue = constraintValidator.modify(annotation, parameterValue, parameterType);
//            if (reValue==null){
//                return;
//            }
//            if (reValue.getClass()!= parameterType){
//                log.warn("default value reValue class!=valueType");
//            }
//            params[parameterIndex] = reValue;
//        }
//    }
//    private Boolean checkExpressionParameter(Parameter[] parameters,Object[] params,Annotation annotation,Method[] annotationMethods){
//        Method expressionMethod = MethodUtil.filterMethodName(annotationMethods, ValidatedConst.EXPRESSION);
//        if (expressionMethod == null) {
//            return true;
//        }
//        String expression = (String) MethodUtil.getReturnValue(expressionMethod, annotation);
//        if (ObjectUtil.isEmpty(expression)) {
//            return true;
//        }
//        int initialCapacity = (int) (parameters.length+1 / 0.75) + 1;
//        Map<String,Object> rootMap = new HashMap<>(initialCapacity);
//        for (int j = 0; j < parameters.length; j++) {
//            Parameter parameter = parameters[j];
//            Object argument = params[j];
//            rootMap.put(parameter.getName(),argument);
//        }
//        rootMap.put(ValidatedConst.SPRING_PROFILES_ACTIVE,this.springProfilesActive);
//        return OgnlCache.executeExpression(expression, rootMap);
//    }
//
//
//
//    public static List<Violation> validate(Configuration configuration,Object[] parameterValues,int parameterIndex){
//        Object parameterValue = parameterValues[parameterIndex];
//        Class parameterValueClass = parameterValue.getClass();
//        List<MetaConstraint> metaConstraintList = ValidateCache.getMetaConstraint(parameterValueClass, false);
//        if (ObjectUtil.isEmpty(metaConstraintList)){
//            return null;
//        }
//        boolean failFast = configuration.isFailFast();
//        List<Violation> violationList=new ArrayList<>();
//
//        for (int metaIndex = 0; metaIndex < metaConstraintList.size(); metaIndex++) {
//            MetaConstraint metaConstraint = metaConstraintList.get(metaIndex);
//            String fieldName = metaConstraint.getFieldName();
//            Object fieldValue = MethodUtil.getFieldValue(fieldName,metaConstraint.getClazz(), parameterValue);
//            Map<String,Object> annotationMap = metaConstraint.getAnnotationMap();
//            Class clazz = metaConstraint.getClazz();
//
//            List<ConstraintValidator> containConstraint = metaConstraint.getConstraintValidatorList();
//            for (int i = 0; i < containConstraint.size(); i++) {
//                ConstraintValidator constraintValidator = containConstraint.get(i);
//
//                Boolean expression= checkExpressionField(parameterValues,clazz,annotationMap,configuration);
//                if (ObjectUtil.isTrue(expression)){
//                    boolean valid = constraintValidator.isValid(metaConstraint.getAnnotation(), fieldValue, metaConstraint.getFieldType());
//                    if (!valid){
//                        String msg = (String) metaConstraint.getAnnotationMap().get(ValidatedConst.MSG);
//                        violationList.add(Violation.builder().msg(msg).build());
//                        if (failFast){
//                            return violationList;
//                        }
//                    }
//                }
//            }
//        }
//        return violationList;
//    }
//
//    private static Boolean checkExpressionField(Object[] parameterValues,Class clazz,Map<String,Object> annotationMap,Configuration configuration){
//        if (!annotationMap.containsKey(ValidatedConst.EXPRESSION)){
//            return false;
//        }
//        String expression = (String) annotationMap.get(ValidatedConst.EXPRESSION);
//        if (ObjectUtil.isEmpty(expression)){
//            return true;
//        }
//        int initialCapacity = (int) (parameterValues.length+1 / 0.75) + 1;
//        Map<String, Object> rootMap = new HashMap<>(initialCapacity);
//        rootMap.put(ValidatedConst.SPRING_PROFILES_ACTIVE,configuration.getSpringProfilesActive());
//        //TODO 需要验证
//        for (int i = 0; i < parameterValues.length; i++) {
//            String name = getClassParamName(clazz);
//            rootMap.put(name, parameterValues[i]);
//        }
//        return OgnlCache.executeExpression(expression, rootMap);
//    }
//
//
//
//    private static String getClassParamName(Class clazz) {
//        ValidatedParam validatedParam = (ValidatedParam) clazz.getDeclaredAnnotation(ValidatedParam.class);
//        return validatedParam != null ? validatedParam.value() : captureName(clazz.getSimpleName());
//    }
//
//    private static String captureName(String name) {
//        if (name == null || name.isEmpty()) {
//            return name;
//        }
//        char[] cs = name.toCharArray();
//        cs[0] = Character.toLowerCase(cs[0]);
//        return new String(cs);
//    }
//
//
//
//
//
//
//
//    private boolean checkAnnotationGroups(Annotation annotation) {
//        Class<?>[] validatedGroups = this.validatedGroups;
//        if (ObjectUtil.isEmpty(validatedGroups)) {
//            return true;
//        }
//        Class[] annotationGroups = MethodUtil.getAnnotationGroups(annotation);
//        if (ObjectUtil.isNotEmpty(annotationGroups)) {
//            return checkGroup(validatedGroups, annotationGroups);
//        } else {
//            return checkGroup(validatedGroups, DefaultGroup.class);
//        }
//    }
//
//    private boolean checkGroup(Class<?>[] vGroup, Class<?>[] aGroup) {
//        if (ObjectUtil.isEmpty(aGroup)) {
//            return false;
//        }
//        if (log.isDebugEnabled()) {
//            log.debug("@Validated groups:{}  annotation groups:{}", vGroup, aGroup);
//        }
//        for (Class v : vGroup) {
//            if (checkGroup(aGroup, v)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean checkGroup(Class[] groups, Class group) {
//        for (Class g : groups) {
//            if (group.equals(g)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//}
