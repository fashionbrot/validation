package com.github.fashionbrot.annotation;

import com.github.fashionbrot.config.ValidatedConfigBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ValidatedConfigBeanDefinitionRegistrar.class)
public @interface EnableValidatedConfig {

    /**
     * language
     * @return String
     */
    String language() default "";
    /**
     * request locale param name
     * @return String
     */
    String localeParamName() default "";

}
