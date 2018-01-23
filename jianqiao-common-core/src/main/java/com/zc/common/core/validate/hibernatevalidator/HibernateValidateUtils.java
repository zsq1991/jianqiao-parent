package com.zc.common.core.validate.hibernatevalidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 关于hibernatevalidator框架的封装,是对Bean对象做验证
 * validateProperty() 和 validateValue() 会忽略被验证属性上定义的@Valid.
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-10 下午1:12:29
 * 
 */
public class HibernateValidateUtils {
	// 验证对象
	private static Validator validator;
	static {
		// 获取validator工厂方法
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		// 获取验证对象
		validator = validatorFactory.getValidator();
	}

	/**
	 * 获取对象验证的结果
	 * 
	 * @param t
	 * @return
	 */
	public static <T> Set<ConstraintViolation<T>> getValidator(final T t) {
		return validator.validate(t);
	}

	/**
	 * 获取对象某个属性验证的结果
	 * 
	 * @param t
	 * @param property
	 * @return
	 */
	public static <T> Set<ConstraintViolation<T>> getValidatorProperty(final T t,
			final String property) {
		return validator.validateProperty(t, property);
	}
}
