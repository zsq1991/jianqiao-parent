package com.zc.common.core.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月9日 上午2:40:25
 * 
 */
public class XssAndHtmlInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		Map<String, String[]> map = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			if (values != null) {
				if (!key.startsWith("no_")) {
					for (int i = 0; i < values.length; i++) {
						PolicyFactory policyFactory = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
						values[i] = policyFactory.sanitize(values[i]);
					}
					request.setAttribute(key, values);
				}else{
					request.setAttribute(StringUtils.substringAfter(key, "no_"), values);
				}
			}
		}
		return true;
	}
}
