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

    private String localeParamName;

    private String springProfilesActive;
}
