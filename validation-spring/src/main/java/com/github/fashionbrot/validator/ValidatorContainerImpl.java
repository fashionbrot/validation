package com.github.fashionbrot.validator;

import com.github.fashionbrot.common.util.BeanUtil;
import com.github.fashionbrot.constraint.ConstraintHelper;
import com.github.fashionbrot.constraint.ConstraintValidator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.lang.annotation.Annotation;

/**
 * @author fashionbrot
 */
public class ValidatorContainerImpl implements ValidatorContainer, BeanFactoryAware, InitializingBean {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public ConstraintValidator injectContainer(Class<? extends ConstraintValidator<? extends Annotation, ?>> clazz) {
        BeanUtil.registerInfrastructureBeanIfAbsent((BeanDefinitionRegistry) beanFactory, clazz.getName(), clazz);
        ConstraintValidator<? extends Annotation, ?> bean = beanFactory.getBean(clazz);
        if (bean != null) {
            return bean;
        }
        return null;
    }

    @Override
    public void afterPropertiesSet()  {
        ConstraintHelper.setValidatorContainer(this);
    }
}
