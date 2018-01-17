package com.zc.common.core.webmvc.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 在servlet中结合Spring
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-5-3 下午04:37:30
 * 
 */
public class DelegatingServletProxy extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2430785308415168690L;

	private String targetBean;
	private Servlet proxy;

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException,
			IOException {
		proxy.service(req, res);
	}

	@Override
    public void init() throws ServletException {
		this.targetBean = getServletName();
		getServletBean();
		proxy.init(getServletConfig());
	}

	private void getServletBean() {
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		this.proxy = (Servlet) wac.getBean(targetBean);
	}
}
