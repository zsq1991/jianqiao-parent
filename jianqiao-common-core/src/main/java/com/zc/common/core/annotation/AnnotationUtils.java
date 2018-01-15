package com.zc.common.core.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 判断annotation的方法
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-10 下午4:35:57
 * 
 */
public class AnnotationUtils {
	private AnnotationUtils() {
	}

	/**
	 * 获取标识了annotationClass的field属性
	 * 
	 * @param clazz 目标类
	 * @param annotationClass 注解
	 * @return 如果没有数据返回list的长度为0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Field> getHaveAnnotationByField(final Class clazz,
			final Class annotationClass) {
		List<Field> returnfields = new ArrayList<Field>();
		// 获取这个类所有的有效field
		Field[] fields = clazz.getDeclaredFields();
		// 获取有带注释的Field
		for (Field field : fields) {
			if (field.getAnnotation(annotationClass) != null) {
				returnfields.add(field);
			}
		}
		return returnfields;
	}

	/**
	 * 获取标识了annotationClass的field属性 紧紧只匹配第一个
	 * 
	 * @param clazz 目标类
	 * @param annotationClass 注解
	 * @return 如果没有返回null
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Field getHaveAnnotationByFirstField(final Class clazz, final Class annotationClass) {
		// 获取这个类所有的有效field
		Field[] fields = clazz.getDeclaredFields();
		// 获取有带注释的Field
		for (Field field : fields) {
			if (field.getAnnotation(annotationClass) != null) {
				return field;
			}
		}
		return null;
	}

	/**
	 * 获取标识了annotationClass的field属性
	 * 
	 * @param clazz 目标类
	 * @param annotationClass 注解
	 * @return 如果没有数据返回list的长度为0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Method> getHaveAnnotationByMethod(final Class clazz,
			final Class annotationClass) {
		List<Method> returnmethods = new ArrayList<Method>();
		// 获取这个类所有的有效field
		Method[] methods = clazz.getDeclaredMethods();
		// 获取有带注释的Field
		for (Method method : methods) {
			if (method.getAnnotation(annotationClass) != null) {
				returnmethods.add(method);
			}
		}
		return returnmethods;
	}

	/**
	 * 获取标识了annotationClass的field属性 紧紧只匹配第一个
	 * 
	 * @param clazz 目标类
	 * @param annotationClass 注解
	 * @return 如果没有返回null
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getHaveAnnotationByFirstMethod(final Class clazz,
			final Class annotationClass) {
		// 获取这个类所有的有效field
		Method[] methods = clazz.getDeclaredMethods();
		// 获取有带注释的Field
		for (Method method : methods) {
			if (method.getAnnotation(annotationClass) != null) {
				return method;
			}
		}
		return null;
	}

	
}
