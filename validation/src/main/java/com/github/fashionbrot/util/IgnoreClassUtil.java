package com.github.fashionbrot.util;

import java.util.HashSet;
import java.util.Set;

public class IgnoreClassUtil {



    private static Set<String> packageSet =new HashSet<>();

    static {
        packageSet.add("org.springframework.ui");
        packageSet.add("javax.servlet");
    }

    public static void putIgnorePackage(String packageName){
        if (ObjectUtil.isNotBlank(packageName)) {
            packageSet.add(packageName);
        }
    }



    public static boolean checkIgnorePackage(String packageName){
        if (ObjectUtil.isNotEmpty(packageName) && packageSet.contains(packageName)){
            return true;
        }
        return false;
    }

}
