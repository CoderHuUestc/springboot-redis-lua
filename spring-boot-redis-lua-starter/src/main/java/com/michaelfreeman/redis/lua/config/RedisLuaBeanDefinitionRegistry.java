package com.michaelfreeman.redis.lua.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.regex.Pattern;


/**
 * @author michalefreeman
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RedisLuaBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

    private static final Logger logger = LoggerFactory.getLogger(RedisLuaBeanDefinitionRegistry.class);

    private static final String BEAN_NAME = "redisLuaBean";

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
        }
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (this.beanFactory == null) {
            logger.warn("It is better to use RedisLuaConfiguration without any xml configuration file, injected by @RedisLuaConfiguration or Spring Boot purely");
        }
        registerAnnotationBean(importingClassMetadata, registry);
    }

    private void registerAnnotationBean(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setLazyInit(false);
        beanDefinition.setBeanClass(RedisLuaBean.class);

        boolean isAnnotated = importingClassMetadata.isAnnotated(RedisLuaConfiguration.class.getName());

        if (isAnnotated) {
            Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(RedisLuaConfiguration.class.getName());
            if (attributes != null && !attributes.isEmpty()) {
                String[] packages = (String[]) attributes.get("packages");
                if (ArrayUtils.isNotEmpty(packages)) {
                    beanDefinition.getPropertyValues().add("package", packages);
                }
            }
        }

        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, BEAN_NAME);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }
}