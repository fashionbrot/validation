package com.github.fashionbrot.intercept;


import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.config.GlobalValidatedProperties;
import com.github.fashionbrot.validator.Validator;
import com.github.fashionbrot.validator.ValidatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
public class ValidatedMethodIntercept implements MethodInterceptor, BeanFactoryAware, InitializingBean {

    public static final String BEAN_NAME = "defaultValidatedMethodIntercept";

    private BeanFactory beanFactory;
    private Validator validator;
    private GlobalValidatedProperties globalValidatedProperties;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Object[] params=methodInvocation.getArguments();
        Method method=methodInvocation.getMethod();
        Validated validated=method.getDeclaredAnnotation(Validated.class);
        String language = null;
        if (validated!=null) {
            language = getLanguage();
            validator.validParameter(method, params,language);
        }

        Object object =  methodInvocation.proceed();
        if (validated.validReturnValue()) {
            validator.validReturnValue(method, object,language);
        }
        return object;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory  = beanFactory;
        if (beanFactory!=null){
            this.validator = (ValidatorImpl) beanFactory.getBean(ValidatorImpl.BEAN_NAME);
        }
    }



    private  String getLanguage() {
        if (ObjectUtil.isEmpty(globalValidatedProperties.getLocaleParamName())){
            return null;
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes!=null){
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            if (request!=null){
                return request.getParameter(globalValidatedProperties.getLocaleParamName());
            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet()  {
        initGlobalValidatedProperties();
    }

    private void initGlobalValidatedProperties(){
        SingletonBeanRegistry beanRegistry = null;
        if (beanFactory instanceof SingletonBeanRegistry) {
            beanRegistry = (SingletonBeanRegistry) beanFactory;
        } else if (beanFactory instanceof AbstractApplicationContext) {
            // Maybe AbstractApplicationContext or its sub-classes
            beanRegistry = ((AbstractApplicationContext) beanFactory).getBeanFactory();
        }
        if (beanFactory.containsBean(GlobalValidatedProperties.BEAN_NAME)) {
            GlobalValidatedProperties properties = (GlobalValidatedProperties) beanRegistry.getSingleton(GlobalValidatedProperties.BEAN_NAME);
            if (properties != null) {
                globalValidatedProperties = properties;
            }
        }
    }

}
