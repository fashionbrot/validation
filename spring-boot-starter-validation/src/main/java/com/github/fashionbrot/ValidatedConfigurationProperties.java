package com.github.fashionbrot;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "validated")
public class ValidatedConfigurationProperties {

    /**
     * request locale param name
     * @return
     */
    String localeParamName ;

    public String getLocaleParamName() {
        return localeParamName;
    }

    public void setLocaleParamName(String localeParamName) {
        this.localeParamName = localeParamName;
    }
}
