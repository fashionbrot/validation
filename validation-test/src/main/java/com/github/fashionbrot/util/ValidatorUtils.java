package com.github.fashionbrot.util;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;

public class ValidatorUtils {

    private static Validator validatorFast = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();
    private static Validator validatorAll = Validation.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();

    /**
     * 校验遇到第一个不合法的字段直接返回不合法字段，后续字段不再校验
     * @Time 2020年6月22日 上午11:36:13
     * @param <T>
     * @param domain
     * @return
     * @throws Exception
     */
    public static <T> Set<ConstraintViolation<T>> validateFast(T domain) throws Exception {
        Set<ConstraintViolation<T>> validateResult = validatorFast.validate(domain);
        if(validateResult.size()>0) {
            System.out.println(validateResult.iterator().next().getPropertyPath() +"："+ validateResult.iterator().next().getMessage());
        }
        return validateResult;
    }

    /**
     * 校验所有字段并返回不合法字段
     * @Time 2020年6月22日 上午11:36:55
     * @param <T>
     * @param domain
     * @return
     * @throws Exception
     */
//    public static <T> Set<ConstraintViolation<T>> validateAll(T domain) throws Exception {
//        Set<ConstraintViolation<T>> validateResult = validatorAll.validate(domain);
//        if(validateResult.size()>0) {
//            Iterator<ConstraintViolation<T>> it = validateResult.iterator();
//            while(it.hasNext()) {
//                ConstraintViolation<T> cv = it.next();
//                System.out.println(cv.getPropertyPath()+"："+cv.getMessage());
//            }
//        }
//        return validateResult;
//    }

    public static <T> Set<ConstraintViolation<T>> validateAll(T domain){
        return validatorAll.validate(domain);
    }

}
