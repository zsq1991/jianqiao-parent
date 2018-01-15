package com.zc.common.core.cxf.webservice;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-7-29 下午3:22:50
 * 
 */
public class WebserviceUtils {
	/**
	 * 返回错误信息
	 * 
	 * @param clazz
	 * @param msg
	 * @return
	 */
	public static <T> WSResult<T> returnError(String msg) {
		WSResult<T> wsResult = new WSResult<T>();
		wsResult.setCode(0);
		wsResult.setMsg(msg);
		return wsResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @param clazz
	 * @param msg
	 * @return
	 */
	public static <T> WSResult<T> returnError(final Set<ConstraintViolation<T>> constraintViolations) {
		WSResult<T> wsResult = new WSResult<T>();
		wsResult.setCode(0);
		wsResult.setMsg(constraintViolations.iterator().next().getMessage());
		return wsResult;
	}

	/**
	 * 成功返回
	 * 
	 * @param clazz
	 * @param msg
	 * @param t
	 * @return
	 */
	public static <T> WSResult<T> returnSuccess(String msg, T t) {
		WSResult<T> wsResult = new WSResult<T>();
		wsResult.setCode(1);
		wsResult.setMsg(msg);
		wsResult.setT(t);
		return wsResult;
	}
}
