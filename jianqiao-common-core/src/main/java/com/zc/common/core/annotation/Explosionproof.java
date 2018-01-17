package com.zc.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 防爆注释
 * @author whl
 * @date 2018-01-16 14:23
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD) 
public @interface Explosionproof {

}
