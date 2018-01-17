package com.zc.common.core.bean;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.zc.common.core.reflection.ReflectionUtils;
import com.zc.common.core.utils.MyObjectUtils;

/**
 * 实体工具类
 * 
 * @author zhangkaoqin
 * @e-mail 67658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-11-7 下午07:22:54
 * 
 */
public class MyBeanUtils {
	private MyBeanUtils() {
	}

	/**
	 * 对象属性拷贝，当属性值为空时，不拷贝
	 * 
	 * @param dest
	 * @param src
	 */
	public static void propertyUtils(final Object dest, final Object src) {
		Field[] fields = src.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (!"serialVersionUID".equals(field.getName())) {
				if (!"lockversion".equals(field.getName())) {
					if (field.getAnnotation(XStreamOmitField.class) == null) {
						Object object = ReflectionUtils.invokeGetterMethod(src, field.getName());
						if (object != null
								|| StringUtils.isNotBlank(MyObjectUtils.toString(object))) {
							ReflectionUtils.invokeSetterMethod(dest, field.getName(), object,
									field.getType());
						}
					}
				}
			}
		}
	}

	public static void entityToMongoCopy(final Object dest, final Object src)
			throws NoSuchMethodException, SecurityException {
		Field[] fields = src.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (!"serialVersionUID".equals(field.getName())) {
				if (!"lockversion".equals(field.getName())) {
					if (!"id".equals(field.getName())) {
						if (field.getAnnotation(Transient.class) == null) {
							Object object = ReflectionUtils
									.invokeGetterMethod(src, field.getName());
							if (object != null
									|| StringUtils.isNotBlank(MyObjectUtils.toString(object))) {
								ReflectionUtils.invokeSetterMethod(dest, field.getName(), object,
										field.getType());
							}
						}
					}
				}
			}
		}
	}
}
