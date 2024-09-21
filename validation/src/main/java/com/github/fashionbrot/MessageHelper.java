package com.github.fashionbrot;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.consts.ValidatedConst;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class MessageHelper {


    private static final Map<String, ResourceBundle> RESOURCE_BUNDLE_CACHE = new ConcurrentHashMap<>();

    public static String filterMessage(String message, String language) {
        if (ObjectUtil.isEmpty(message)) {
            return "";
        }

        boolean isDefaultMessage = message.startsWith(ValidatedConst.OPEN_TOKEN) && message.endsWith(ValidatedConst.CLOSE_TOKEN);
        if (isDefaultMessage) {
            message = resolveMessage(message, language);
        }
        return message;
    }

    private static String resolveMessage(String messageKey, String language) {
        String key = extractVariables(messageKey);
        String currentLanguage = language;
        if (ObjectUtil.isEmpty(currentLanguage)){
            currentLanguage = ValidatedConst.DEFAULT_LANGUAGE;
        }
        ResourceBundle resourceBundle = getResourceBundle(ValidatedConst.FILE_NAME_PREFIX + currentLanguage);
        if (resourceBundle == null || !resourceBundle.containsKey(key)) {
            throw new MissingResourceException("Message for key '" + key + "' not found in " + ValidatedConst.FILE_NAME_PREFIX + currentLanguage, MessageHelper.class.getName(), messageKey);
        }
        return resourceBundle.getString(key);
    }

    public static ResourceBundle getResourceBundle(final String fileName) {
        return RESOURCE_BUNDLE_CACHE.computeIfAbsent(fileName, ResourceBundle::getBundle);
    }


    public static String extractVariables(String input) {
        if (ObjectUtil.isEmpty(input)){
            return input;
        }
        int startIndex = input.indexOf("${");
        int endIndex = input.lastIndexOf("}");
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return input.substring(startIndex + 2, endIndex);
        }
        return input;
    }

}
