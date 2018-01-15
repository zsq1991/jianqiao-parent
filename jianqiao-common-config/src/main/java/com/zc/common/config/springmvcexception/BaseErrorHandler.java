/**
 * Project Name:cc-web-main
 * File Name:BaseErrorHandler.java
 * Package Name:com.zc.main.config.springmvcexception
 * Date:2017年9月3日下午10:04:55
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.zc.common.config.springmvcexception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * ClassName:BaseErrorHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年9月3日 下午10:04:55 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@ControllerAdvice 
public class BaseErrorHandler {
	
	private static Logger logger=LoggerFactory.getLogger(BaseErrorHandler.class);
	
	/**
	 * @Title: defaultErrorHandler
	 * @Description: 500异常处理 ddd
	 * @param: @param request
	 * @param: @param e
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(HttpServletRequest request, Exception e,Model model){
			logger.error("BaseErrorController Exception >> ",e);
			logger.error("request error url >> " + request.getRequestURL());
			model.addAttribute("code", 500);
			model.addAttribute("msg", "非常抱歉，系统发生错误");
			return "error/500";

	} 
	
	/**
	 * @Title: pageNoFoundHandler   
	 * @Description: 404异常处理   
	 * @param: @param request
	 * @param: @param exception
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@ExceptionHandler(value = NoHandlerFoundException.class)
	public String pageNoFoundHandler(HttpServletRequest request,NoHandlerFoundException exception, Model model){
		logger.error("BaseErrorController NoHandlerFoundException >> ",exception);
		logger.error("request error url >> " + request.getRequestURL());
		model.addAttribute("code", 404);
		model.addAttribute("msg", "您访问的页面暂时找不到了，有可能链接错误或者网络问题");
		return "error/404";
	}
  /**
   *@Author: Yaowei
   *@Description: 
   *@Date: 2017/11/28 21:10
   */
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public String disableResourcesHandler(HttpServletRequest request,HttpRequestMethodNotSupportedException exception, Model model) {
		logger.error("BaseErrorController NoHandlerFoundException >> ", exception);
		logger.error("request error url >> " + request.getRequestURL());
		model.addAttribute("code", 405);
		model.addAttribute("msg", " 缺少请求参数");
		return "error/405";
	}
	
	 /**
	  *@Author: Yaowei
	  *@Description: 
	  *@Date: 2017/11/28 21:10
	  */
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
    public String handleHttpMessageNotReadableHandler(HttpServletRequest request,MissingServletRequestParameterException exception, Model model){
	logger.error("BaseErrorController MissingServletRequestParameterException >> ",exception);
	logger.error("request error url >> " + request.getRequestURL());
	model.addAttribute("code", 400);
	model.addAttribute("msg", " 参数解析失败");
	return "error/400";
    }
	 /**
	  *@Author: Yaowei
	  *@Description:
	  *@Date: 2017/11/28 21:10
	  */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidHandler(HttpServletRequest request,NoHandlerFoundException exception, Model model){
	logger.error("BaseErrorController MethodArgumentNotValidException >> ",exception);
	logger.error("request error url >> " + request.getRequestURL());
	model.addAttribute("code", 400);
	model.addAttribute("msg", " 参数验证失败");
	return "error/400";
    }
	/* *//**
	  *@Author: Yaowei
	  *@Description:
	  *@Date: 2017/11/28 21:10
	  *//*
	@ExceptionHandler(value = BindException.class)
    public String handleBindException(HttpServletRequest request,BindException exception, Model model){
	logger.error("BaseErrorController BindException >> ",exception);
	logger.error("request error url >> " + request.getRequestURL());
	model.addAttribute("code", 400);
	model.addAttribute("msg", " 参数绑定失败");
	return "error/400";
    }*/
	 /**
	  *@Author: Yaowei
	  *@Description:
	  *@Date: 2017/11/28 21:10
	  */
	/*@ExceptionHandler(value = ConstraintViolationException.class)
    public String handleServiceException(HttpServletRequest request,ConstraintViolationException exception, Model model){
	logger.error("BaseErrorController ConstraintViolationException >> ",exception);
	logger.error("request error url >> " + request.getRequestURL());
	model.addAttribute("code", 400);
	model.addAttribute("msg", " 参数验证失败");
	return "error/400";
    }*/
	 /**
	  *@Author: Yaowei
	  *@Description:
	  *@Date: 2017/11/28 21:10
	  */
	/*@ExceptionHandler(value = ValidationException.class)
    public String handleValidationException(HttpServletRequest request,ValidationException exception, Model model){
	logger.error("BaseErrorController ValidationException >> ",exception);
	logger.error("request error url >> " + request.getRequestURL());
	model.addAttribute("code", 400);
	model.addAttribute("msg", " 参数验证失败");
	return "error/400";
    }*/
}
