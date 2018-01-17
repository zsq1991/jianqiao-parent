package com.zc.common.core.security.springsecurity.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 权限过滤器
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-2-20 下午3:43:19
 * 
 */
public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {

	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	private boolean observeOncePerRequest = true;

	// ~ Methods
	// ========================================================================================================

	/**
	 * Not used (we rely on IoC container lifecycle services instead)
	 * 
	 * @param arg0 ignored
	 * 
	 * @throws ServletException never thrown
	 */
	public MySecurityFilter() {
		super();
	}

	@Override
    public void init(FilterConfig arg0) throws ServletException {
	}

	/**
	 * Not used (we rely on IoC container lifecycle services instead)
	 */
	@Override
    public void destroy() {
	}

	/**
	 * Method that is actually called by the filter chain. Simply delegates to
	 * the {@link #invoke(FilterInvocation)} method.
	 * 
	 * @param request the servlet request
	 * @param response the servlet response
	 * @param chain the filter chain
	 * 
	 * @throws IOException if the filter chain fails
	 * @throws ServletException if the filter chain fails
	 */
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}

	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	@Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
		this.securityMetadataSource = newSource;
	}

	@Override
    public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	public void invoke(FilterInvocation fi) throws IOException, ServletException {

		InterceptorStatusToken token = super.beforeInvocation(fi);

		fi.getChain().doFilter(fi.getRequest(), fi.getResponse());

		super.afterInvocation(token, null);

	}

	/**
	 * Indicates whether once-per-request handling will be observed. By default
	 * this is <code>true</code>, meaning the
	 * <code>FilterSecurityInterceptor</code> will only execute
	 * once-per-request. Sometimes users may wish it to execute more than once
	 * per request, such as when JSP forwards are being used and filter security
	 * is desired on each included fragment of the HTTP request.
	 * 
	 * @return <code>true</code> (the default) if once-per-request is honoured,
	 *         otherwise <code>false</code> if
	 *         <code>FilterSecurityInterceptor</code> will enforce
	 *         authorizations for each and every fragment of the HTTP request.
	 */
	public boolean isObserveOncePerRequest() {
		return observeOncePerRequest;
	}

	public void setObserveOncePerRequest(boolean observeOncePerRequest) {
		this.observeOncePerRequest = observeOncePerRequest;
	}

}
