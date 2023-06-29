package com.github.fashionbrot.util;

import com.github.fashionbrot.consts.ValidatedConst;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class ValidatorUtil{

    public static final String BEAN_NAME = "marsValidatorUtil";

    private static Map<String,ResourceBundle> MSG_MAP=new ConcurrentHashMap<>();


    public static String filterMsg(String msg,String fileName,String language) {
        if (ObjectUtil.isEmpty(msg)){
            return "";
        }
        boolean isDefaultMsg = msg.startsWith("validated.");
        if (isDefaultMsg) {
            msg = getMsg(msg,fileName,language);
        }
        return msg;
    }

    private static String getMsg(String msg,String fileName,String language) {
        String currentFileName = fileName;
        if (ObjectUtil.isEmpty(currentFileName)){
            currentFileName = ValidatedConst.FILE_NAME_PREFIX;
        }
        String currentLanguage = language;
        if (ObjectUtil.isEmpty(language)){
            currentLanguage = ValidatedConst.DEFAULT_LANGUAGE;
        }
        ResourceBundle resourceBundle = getResourceBundle(currentFileName+"_"+currentLanguage);
        if (resourceBundle==null){
            throw new MissingResourceException(fileName+"_"+language+" does not exist", "EnableValidatedConfig","language");
        }
        if (resourceBundle.containsKey(msg)){
            msg = resourceBundle.getString(msg);
        }
        return msg;
    }



    public static ResourceBundle getResourceBundle(final String fileName){
        ResourceBundle resourceBundle = MSG_MAP.get(fileName);
        if (resourceBundle==null){
            resourceBundle = ResourceBundle.getBundle(fileName);
            if (resourceBundle!=null){
                MSG_MAP.put(fileName,resourceBundle);
            }
        }
        return resourceBundle;
    }


}
