package com.github.fashionbrot.test;

import lombok.Data;

import java.util.List;

/**
 * @author fashionbrot
 */
@Data
public class MetaClass {
    //1Class 2SuperClass 3filedClass
    private byte type;
    private Class clazz;
    private List<MetaConstraint> metaConstraintList;
}
