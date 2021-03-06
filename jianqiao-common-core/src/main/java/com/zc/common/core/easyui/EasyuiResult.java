package com.zc.common.core.easyui;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.io.Serializable;


/**
 * jqueryeasyui返回值
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-7-26 下午8:24:27
 * 
 */
public class EasyuiResult<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5692458681120354788L;
	/**
	 * 总数
	 */
	private Long total;
	/**
	 * 类型
	 */
	@JsonProperty("rows")
	private T t;
	/**
	 * 底部统计
	 */
	@JsonUnwrapped
	private Object footer;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public Object getFooter() {
		return footer;
	}

	public void setFooter(Object footer) {
		this.footer = footer;
	}

}
