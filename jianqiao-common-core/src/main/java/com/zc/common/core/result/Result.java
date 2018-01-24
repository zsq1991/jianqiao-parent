package com.zc.common.core.result;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.io.Serializable;


/**
 * 接口返回值定义类
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-6-22 上午10:26:48
 * 
 */
public class Result implements Serializable{

	private static final long serialVersionUID = 262888242269244717L;
	/**
	 * 状态，1为成功，0为失败，其他自定义
	 */
	private Integer code = 1;
	/**
	 * 状态说明信息
	 */
	private String msg;
	/**
	 * 内容
	 */
	@JsonUnwrapped
	private Object content;
	/**
	 * 会话ID
	 */
	@JsonUnwrapped
	private String sessionId;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
