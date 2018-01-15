/**
 * Project Name:zte-buyer-pc-interface
 * Package Name:com.alqsoft.dao
 * File Name:AvoidDuplicateSubmission.java
 * Create Date:2014-12-3 下午3:48:01
 * SVN:$Id$
 * Copyright © 2014 厦门卓讯信息技术有限公司 All rights reserved.
 */
package com.zc.common.core.interceptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 防止重复提交注解，用于方法上<br/>
 * 在新建页面方法上，设置needSaveToken()为true，此时拦截器会在Session中保存一个token，
 * 同时需要在新建的页面中添加
 * <input type="hidden" name="token" value="${token}">
 * <br/>
 * 保存方法需要验证重复提交的，设置needRemoveToken为true
 * 此时会在拦截器中验证是否重复提交
 * </p>
 * @author: chenzhenbing
 * @date: 2014-12-03
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidDuplicateSubmission {
    boolean needRemoveToken() default false;
}
