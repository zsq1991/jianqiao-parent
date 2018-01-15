package com.zc.common.core.interceptor;
/*package com.org.alqframework.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.org.alqframework.interceptor.MobileAppInteceptor;
import com.org.alqframework.result.ResultUtils;
import com.org.alqframework.utils.UniqueUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alqsoft.init.InitParam;

*//**
 * 手机端接口拦截器
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月25日 下午6:21:11
 * 
 *//*
public class MobileAppInteceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(MobileAppInteceptor.class);

	@Autowired
	private InitParam initParam;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		Map<String, String> map=initParam.getConstantMap();
		String MOBILE_APP_PROJECT_CODE =map.get("MOBILE_APP_PROJECT_CODE");//项目编号
		String MOBILE_APP_PROJECT_NAME=map.get("MOBILE_APP_PROJECT_NAME");//项目名称
		String MOBILE_APP_ISOPEN=map.get("MOBILE_APP_ISOPEN");//是否启用
		String MOBILE_APP_TIMES=map.get("MOBILE_APP_TIMES");//间隔时间
		//验证是否启用接口拦截器
		if (StringUtils.equals(MOBILE_APP_ISOPEN, "true")) {
			//获取客户端头部参数
			String mobile_currentTime=request.getHeader("mobileCurrentTime");//获取客户端时间戳(前10位，即：秒 s)
			String mobile_sign=request.getHeader("mobileSign");//获取客户端验签
			
			//获取当前时间戳 前10 位 s单位
			Long now_currentTime=Long.parseLong(StringUtils.substring(String.valueOf(System.currentTimeMillis()), 0, 10));
			
			//获取时间戳的绝对值，并把秒转单位为分钟
			Long minute_times=Math.abs(now_currentTime-Long.parseLong(mobile_currentTime))/60;
			
			//获取本地验签
			String sign=UniqueUtils.getInterceptorSign(MOBILE_APP_PROJECT_NAME, MOBILE_APP_PROJECT_CODE, mobile_currentTime);//获取本地验签
			
			//判断客户端接口的参数验签是否跟服务器端一致 并且时间戳是否小于等于MOBILE_APP_TIMES（常量中设定的参数值）
			if (StringUtils.equals(sign, mobile_sign) && minute_times<=Long.parseLong(MOBILE_APP_TIMES)) {
				logger.info("--------接口请求允许访问--------");
				return true;
			}else{
				logger.error("--------禁止访问接口--------");
				response.getWriter().write(JSON.toJSONString(ResultUtils.returnError("禁止访问接口", 509)));
				return false;
			}
			
		}else{
			//关闭拦截器也是可以访问
			return true;
		}
	}

}
*/