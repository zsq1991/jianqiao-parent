package com.zc.common.core.easyui;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * easyui工具
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-7-26 下午11:11:33
 * 
 */
public class EasyUtils {

	/**
	 * 正常返回easyui数据
	 * 
	 * @param t
	 * @param page
	 * @return
	 */
	public static <T> EasyuiResult<List<T>> returnPage(Class<T> clazz, Page<T> page) {
		EasyuiResult<List<T>> easyuiResult = new EasyuiResult<List<T>>();
		easyuiResult.setTotal(page.getTotalElements());
		easyuiResult.setT(page.getContent());
		return easyuiResult;
	}

	/**
	 * 正常返回easyui数据带上footer
	 * 
	 * @param t
	 * @param page
	 * @return
	 */
	public static <T> EasyuiResult<List<T>> returnPageByfooter(Class<T> clazz, Page<T> page, Object object) {
		EasyuiResult<List<T>> easyuiResult = new EasyuiResult<List<T>>();
		easyuiResult.setTotal(page.getTotalElements());
		easyuiResult.setT(page.getContent());
		easyuiResult.setFooter(object);
		return easyuiResult;
	}
}
