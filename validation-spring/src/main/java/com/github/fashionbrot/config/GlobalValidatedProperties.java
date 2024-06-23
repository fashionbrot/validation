package com.github.fashionbrot.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalValidatedProperties {


    public static final String BEAN_NAME = "defaultGlobalValidatedProperties";
    public static final String LOCALE_PARAM_NAME="localeParamName";
    public static final String SPRING_PROFILES_ACTIVE="springProfilesActive";

    private String localeParamName;

    private String springProfilesActive;
}
