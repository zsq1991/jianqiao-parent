package com.zc.common.core.result;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.validation.BindingResult;
/**
 * 
 * 
 * @author 张灿
 * @e-mail 1023059764@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-7-31 下午4:53:32
 * 
 */

public class ResultUtils {
	/**
	 * 返回错误信息
	 * 
	 * @return
	 */
	public static Result returnError(final String msg) {
		Result result = new Result();
		result.setCode(0);
		result.setMsg(msg);
		return result;
	}

	/**
	 * 返回错误信息带其他序号的
	 * 
	 * @return
	 */
	public static Result returnError(final String msg, final Integer code) {
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

	/**
	 * 返回验证的错误信息
	 * 
	 * @return
	 */
	public static Result returnError(final BindingResult bindingResult) {
		Result result = new Result();
		result.setCode(0);
		result.setMsg(bindingResult.getFieldError().getDefaultMessage());
		return result;
	}
	
	/**
	 * 返回验证的错误信息
	 * 
	 * @return
	 */
	public static <T> Result returnError(final Set<ConstraintViolation<T>> constraintViolations) {
		Result result = new Result();
		result.setCode(0);
		result.setMsg(constraintViolations.iterator().next().getMessage());
		return result;
	}


	/**
	 * 返回成功提示
	 * 
	 * @param msg
	 * @return
	 */
	public static Result returnSuccess(final String msg) {
		Result result = new Result();
		result.setMsg(msg);
		return result;
	}

	/**
	 * 返回带内容的成功提示
	 * 
	 * @param msg
	 * @return
	 */
	public static Result returnSuccess(final String msg, final Object object) {
		Result result = new Result();
		result.setMsg(msg);
		result.setContent(object);
		return result;
	}
	/**
	 * 返回带session的成功信息
	 * @param msg
	 * @param object
	 * @param sessionId
	 * @return
	 */
	public static Result returnSuccess(final String msg,final Object object,String sessionId){
		Result result=new Result();
		result.setContent(object);
		result.setMsg(msg);
		result.setSessionId(sessionId);
		return result;
	}
}
