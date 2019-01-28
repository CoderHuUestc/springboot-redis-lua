package com.michaelfreeman.redis.lua;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 参数的顺序必须是key在前 arg在后
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RedisLua {
	
	/**
	 * lua脚本 注意不需要加eval
	 */
	String lua() default "";
	
	/**
	 * key的参数个数
	 */
	int keysCount() default 0;
	
	/**
	 * value的参数个数
	 */
	int argsCount() default 0;
	
	
}
