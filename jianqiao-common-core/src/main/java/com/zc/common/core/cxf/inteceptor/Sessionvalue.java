package com.zc.common.core.cxf.inteceptor;

/**
 * 全局单列类，存储服务器session的有关信息
 * 
 * @author zhangkaoqin
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-8-24 下午10:54:30
 * 
 */
public class Sessionvalue {
	private static final Sessionvalue SESSIONVALUE = new Sessionvalue();

	private Sessionvalue() {
	}

	private String sessionId = "";

	public static Sessionvalue getInstance() {
		return SESSIONVALUE;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
