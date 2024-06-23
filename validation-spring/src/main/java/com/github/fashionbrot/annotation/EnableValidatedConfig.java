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
     * request locale param name
     * @return String
     */
    String localeParamName() default "";

    /**
     * springboot ${spring.profiles.active}
     * @return String
     */
    String springProfilesActive() default "";

}
