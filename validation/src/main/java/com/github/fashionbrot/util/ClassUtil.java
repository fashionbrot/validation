package com.github.fashionbrot.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

public class ClassUtil {


    public static boolean isCollection(Class type) {
        return type!=null && Collection.class.isAssignableFrom(type);
    }

    public static boolean isNotCollection(Class type){
        return !isCollection(type);
    }




    public static boolean isMap(Class type) {
        return type!=null && Map.class.isAssignableFrom(type);
    }

    public static boolean isNotMap(Class type){
        return !isMap(type);
    }



    public static boolean isObject(Class type) {
        return type!=null && type.isAssignableFrom(Object.class);
    }



    public static boolean isNotObject(Class type){
        if (type==null){
            return false;
        }
        return !isObject(type);
    }


    public static boolean isArray(Class type) {
        return type!=null && type.isArray();
    }

    public static boolean isNotArray(Class type){
        return !isArray(type);
    }



    public static boolean isBoolean(Class type){
        return type!=null && (type == boolean.class ||type == Boolean.class);
    }

    public static boolean isNotBoolean(Class type){
        return !isBoolean(type);
    }

    public static boolean isByte(Class type){
        return type!=null && (type == byte.class ||type == Byte.class);
    }

    public static boolean isNotByte(Class type){
        return !isByte(type);
    }

    public static boolean isChar(Class type){
        return type!=null && (type == char.class ||type == Character.class);
    }

    public static boolean isNotChar(Class type){
        return !isChar(type);
    }

    public static boolean isString(Class type){
        return type!=null && (type == String.class ||type == CharSequence.class);
    }

    public static boolean isNotString(Class type){
        return !isString(type);
    }

    public static boolean isFloat(Class type) {
        return type != null && (type == float.class || type == Float.class);
    }

    public static boolean isNotFloat(Class type){
        return !isFloat(type);
    }

    public static boolean isInt(Class type) {
        return type != null && (type == int.class || type == Integer.class);
    }

    public static boolean isNotInt(Class type){
        return !isInt(type);
    }

    public static boolean isLong(Class type) {
        return type != null && (type == long.class || type == Long.class);
    }

    public static boolean isNotLong(Class type){
        return !isLong(type);
    }

    public static boolean isShort(Class type) {
        return type != null && (type == short.class || type == Short.class);
    }

    public static boolean isNotShort(Class type){
        return !isShort(type);
    }

    public static boolean isDouble(Class type) {
        return type != null && (type == double.class || type == Double.class);
    }

    public static boolean isNotDouble(Class type){
        return !isDouble(type);
    }

    public static boolean isBigDecimal(Class type) {
        return type != null && type == BigDecimal.class;
    }

    public static boolean isNotBigDecimal(Class type){
        return !isBigDecimal(type);
    }

    public static boolean isBigInteger(Class type) {
        return type != null && type == BigInteger.class;
    }

    public static boolean isNotBigInteger(Class type){
        return !isBigInteger(type);
    }

    public static boolean isDate(Class type){
        return type!=null && type == Date.class;
    }

    public static boolean isNotDate(Class type){
        return !isDate(type);
    }




}
