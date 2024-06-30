package com.github.fashionbrot.test;

import com.github.fashionbrot.constraint.ConstraintValidator;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * @author fashionbrot
 */
@Data
public class MetaConstraint <A extends Annotation>{

    private PropertyType propertyType;

    private Class clazz;

    private String fieldName;

    private Class fieldType;

    private A annotation;

    private Map<String,Object> annotationMap;

    private List<ConstraintValidator> constraintValidatorList;

}
