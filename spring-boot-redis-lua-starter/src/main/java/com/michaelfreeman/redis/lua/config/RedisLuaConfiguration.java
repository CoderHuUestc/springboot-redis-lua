package com.michaelfreeman.redis.lua.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Configuration
@Import(RedisLuaBeanDefinitionRegistry.class)
public @interface RedisLuaConfiguration {

    /**
     * 需要扫描的包路径
     */
    String[] packages() default {"com.michaelfreeman.redis.lua"};

    /**
     * RedisTemplate bean名称
     */
    String redisTemplateName() default "redisTemplate";
}
