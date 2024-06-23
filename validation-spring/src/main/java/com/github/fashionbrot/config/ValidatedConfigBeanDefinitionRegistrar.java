package com.github.fashionbrot.config;


import com.github.fashionbrot.annotation.EnableValidatedConfig;
import com.github.fashionbrot.common.util.BeanUtil;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.intercept.ValidatedMethodIntercept;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

import static org.springframework.core.annotation.AnnotationAttributes.fromMap;

public class ValidatedConfigBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = fromMap(metadata.getAnnotationAttributes(EnableValidatedConfig.class.getName()));

        registerGlobalValidatedProperties(attributes,registry,environment, GlobalValidatedProperties.BEAN_NAME);

        registerValidatedMethodInterceptor(registry);

        registerValidatedMethodPostProcessor(registry);
    }


    public static void registerGlobalValidatedProperties(AnnotationAttributes attributes, BeanDefinitionRegistry registry, PropertyResolver propertyResolver, String beanName) {

        registerGlobalProperties(attributes, registry, propertyResolver, beanName);
    }


    public static void registerGlobalProperties(Map<?, ?> globalProperties,
                                                BeanDefinitionRegistry registry,
                                                PropertyResolver propertyResolver,
                                                String beanName) {
        if (ObjectUtil.isNotEmpty(globalProperties)) {
            GlobalValidatedProperties validatedProperties = GlobalValidatedProperties.builder()
                .localeParamName((String) globalProperties.get(GlobalValidatedProperties.LOCALE_PARAM_NAME))
                .build();
            if (propertyResolver.containsProperty("validated.locale-param-name")) {
                validatedProperties.setLocaleParamName(propertyResolver.getProperty("validated.locale-param-name"));
            }
            validatedProperties.setSpringProfilesActive(propertyResolver.getProperty("spring.profiles.active","default"));
            BeanUtil.registerSingleton(registry, beanName, validatedProperties);
        } else {
            GlobalValidatedProperties validatedProperties = GlobalValidatedProperties.builder().build();
            if (propertyResolver.containsProperty("validated.locale-param-name")) {
                validatedProperties.setLocaleParamName(propertyResolver.getProperty("validated.locale-param-name"));
            }
            validatedProperties.setSpringProfilesActive(propertyResolver.getProperty("spring.profiles.active","default"));
            BeanUtil.registerSingleton(registry, beanName, validatedProperties);
        }

    }



    public static void registerValidatedMethodPostProcessor(BeanDefinitionRegistry registry) {
        BeanUtil.registerInfrastructureBeanIfAbsent(registry, ValidatedMethodPostProcessor.BEAN_NAME, ValidatedMethodPostProcessor.class);
    }


    public static void registerValidatedMethodInterceptor(BeanDefinitionRegistry registry) {
        BeanUtil.registerInfrastructureBeanIfAbsent(registry, ValidatedMethodIntercept.BEAN_NAME, ValidatedMethodIntercept.class);
    }





}
