package com.github.fashionbrot.util;


import com.github.fashionbrot.config.GlobalValidatedProperties;
import com.github.fashionbrot.config.ValidatedMethodPostProcessor;
import com.github.fashionbrot.intercept.ValidatedMethodIntercept;
import com.github.fashionbrot.validator.ValidatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.PropertyResolver;
import java.lang.reflect.Constructor;
import java.util.Map;


import static org.springframework.beans.factory.BeanFactoryUtils.beanNamesForTypeIncludingAncestors;


@Slf4j
public class BeanUtil {


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
            registerSingleton(registry, beanName, validatedProperties);
        } else {
            GlobalValidatedProperties validatedProperties = GlobalValidatedProperties.builder().build();
            if (propertyResolver.containsProperty("validated.locale-param-name")) {
                validatedProperties.setLocaleParamName(propertyResolver.getProperty("validated.locale-param-name"));
            }
            registerSingleton(registry, beanName, validatedProperties);
        }

    }


    public static void registerValidatedMethodPostProcessor(BeanDefinitionRegistry registry) {
        registerInfrastructureBeanIfAbsent(registry, ValidatedMethodPostProcessor.BEAN_NAME, ValidatedMethodPostProcessor.class);
    }


    public static void registerValidatedMethodInterceptor(BeanDefinitionRegistry registry) {
        registerInfrastructureBeanIfAbsent(registry, ValidatedMethodIntercept.BEAN_NAME, ValidatedMethodIntercept.class);
    }

    public static void registerValidated(BeanDefinitionRegistry registry) {

        registerInfrastructureBeanIfAbsent(registry, ValidatorImpl.BEAN_NAME, ValidatorImpl.class);

        registerInfrastructureBeanIfAbsent(registry, ValidatorUtil.BEAN_NAME, ValidatorUtil.class);

    }



    /**
     * Register an object to be Singleton Bean
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        bean name
     * @param singletonObject singleton object
     */
    public static void registerSingleton(BeanDefinitionRegistry registry, String beanName, Object singletonObject) {
        SingletonBeanRegistry beanRegistry = null;
        if (registry instanceof SingletonBeanRegistry) {
            beanRegistry = (SingletonBeanRegistry) registry;
        } else if (registry instanceof AbstractApplicationContext) {
            // Maybe AbstractApplicationContext or its sub-classes
            beanRegistry = ((AbstractApplicationContext) registry).getBeanFactory();
        }
        // Register Singleton Object if possible
        if (beanRegistry != null) {
            beanRegistry.registerSingleton(beanName, singletonObject);
        }
    }


    /**
     * Register Infrastructure Bean if absent
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        the name of bean
     * @param beanClass       the class of bean
     * @param constructorArgs the arguments of {@link Constructor}
     */
    public static void registerInfrastructureBeanIfAbsent(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass,
                                                          Object... constructorArgs) {
        if (!isBeanDefinitionPresent(registry, beanName, beanClass) && !registry.containsBeanDefinition(beanName)) {
            registerInfrastructureBean(registry, beanName, beanClass, constructorArgs);
        }
    }

    /**
     * Register Infrastructure Bean
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        the name of bean
     * @param beanClass       the class of bean
     * @param constructorArgs the arguments of {@link Constructor}
     */
    public static void registerInfrastructureBean(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass,
                                                  Object... constructorArgs) {
        // Build a BeanDefinition for serviceFactory class
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(beanClass);
        for (Object constructorArg : constructorArgs) {
            beanDefinitionBuilder.addConstructorArgValue(constructorArg);
        }
        // ROLE_INFRASTRUCTURE
        beanDefinitionBuilder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        // Register
        registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
    }


    /**
     * Is {@link BeanDefinition} present in {@link BeanDefinitionRegistry}
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        the name of bean
     * @param targetBeanClass the type of bean
     * @return If Present , return <code>true</code>
     */
    public static boolean isBeanDefinitionPresent(BeanDefinitionRegistry registry, String beanName, Class<?> targetBeanClass) {
        String[] beanNames = BeanUtil.getBeanNames((ListableBeanFactory) registry, targetBeanClass);
        return ObjectUtil.contains(beanNames, beanName);
    }


    /**
     * Get Bean Names from {@link ListableBeanFactory} by type.
     *
     * @param beanFactory {@link ListableBeanFactory}
     * @param beanClass   The  {@link Class} of Bean
     * @return If found , return the array of Bean Names , or empty array.
     */
    public static String[] getBeanNames(ListableBeanFactory beanFactory, Class<?> beanClass) {
        return getBeanNames(beanFactory, beanClass, false);
    }


    public static String[] getBeanNames(ListableBeanFactory beanFactory, Class<?> beanClass,
                                        boolean includingAncestors) {
        // Issue : https://github.com/alibaba/spring-context-support/issues/22
        if (includingAncestors) {
            return beanNamesForTypeIncludingAncestors(beanFactory, beanClass, true, false);
        } else {
            return beanFactory.getBeanNamesForType(beanClass, true, false);
        }
    }


}
