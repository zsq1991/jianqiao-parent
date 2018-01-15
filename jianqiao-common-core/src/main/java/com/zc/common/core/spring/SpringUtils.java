package com.zc.common.core.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * spring的基本工具类
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-18 上午9:32:50
 * 
 */
public class SpringUtils {
	private SpringUtils() {
	}

	/**
	 * 获取web的Applicationcontext对象进行应用
	 * 
	 * @return
	 */
	public static WebApplicationContext getApplicationContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}

	public static Object getBean(String beanName) {
		ApplicationContext ctx = getApplicationContext();
		Object obj = ctx.getBean(beanName);
		return obj;
	}

	public static <T> T getBean(Class<T> clazz) {
		ApplicationContext ctx = getApplicationContext();
		T obj = ctx.getBean(clazz);
		return obj;
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		ApplicationContext ctx = getApplicationContext();
		T obj = ctx.getBean(beanName, clazz);
		return obj;
	}
}
