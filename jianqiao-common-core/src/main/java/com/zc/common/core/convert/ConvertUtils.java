package com.zc.common.core.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.zc.common.core.reflection.ReflectionUtils;

/**
 * 
 * @author zhangkaoqin
 * 
 * 
 */
public class ConvertUtils {

	static {
		registerDateConverter();
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名. String工具类
	 * 
	 * @author 张靠勤
	 * @e-mail 627658539@qq.com
	 * @version v1.0
	 * @create-time 2011-1-5 下午:20:50
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List convertElementPropertyToList(final Collection<?> collection,
			final String propertyName) {
		List list = Lists.newArrayList();
		collection.parallelStream().forEach(t -> {
			if (t != null) {
				try {
					list.add(PropertyUtils.getProperty(t, propertyName));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		});
		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String convertElementPropertyToString(final Collection collection,
			final String propertyName, final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 将map对象转化成实体对象
	 * 
	 * @param bean
	 * @param properties
	 */
	public static void mapToBean(final Object bean, final Map<String, ? extends Object> properties) {
		try {
			BeanUtils.populate(bean, properties);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将实体对象转变成map
	 * 
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map beanToMap(final Object bean) {
		try {
			return BeanUtils.describe(bean);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 定义日期Converter的格式: "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
	 * "yyyy/MM/dd","yyyy/MM/dd HH:mm:ss"
	 */
	public static void registerDateConverter() {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd",
				"yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS" });
		org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
	}
}
