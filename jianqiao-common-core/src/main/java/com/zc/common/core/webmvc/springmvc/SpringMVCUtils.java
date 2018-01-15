package com.zc.common.core.webmvc.springmvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * SpringMVC工具类
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-4-13 下午01:56:17
 * 
 */
public class SpringMVCUtils {
	private SpringMVCUtils() {
	}

	/**
	 * 获取request对象
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
	}

	/**
	 * 获取session对象
	 * 
	 * @return
	 */
	public static HttpSession getSession(boolean b) {
		return getRequest().getSession(b);
	}

	/**
	 * 取得HttpRequest中Parameter的简化方法.
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * 将filter_开头的属性参数的值 带回到页面
	 * 
	 * @param request
	 */
	public static void addAttributeHaveFilter(final HttpServletRequest request) {
		Map<String, String[]> attributeName = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : attributeName.entrySet()) {
			if (StringUtils.indexOf(entry.getKey(), "filter_") != -1) {
				request.setAttribute(StringUtils.substringBefore(entry.getKey(), "."),
						entry.getValue()[0]);
			}
		}
	}

	/**
	 * 返回的信息
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static Result returnResult(final Integer code, final String msg) {
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

	/**
	 * 返回成功信息
	 * 
	 * @param msg
	 * @return
	 */
	public static Result returnSuccess(final String msg) {
		return returnResult(1, msg);
	}

	/**
	 * 返回错误信息
	 * 
	 * @param msg
	 * @return
	 */
	public static Result returnError(final String msg) {
		return returnResult(0, msg);
	}
	
	/**
	 * 返回验证的错误信息
	 * @param bindingResult
	 * @return
	 */
	public static Result returnError(final BindingResult bindingResult){
		return returnError(bindingResult.getFieldError().getDefaultMessage());
	}
}