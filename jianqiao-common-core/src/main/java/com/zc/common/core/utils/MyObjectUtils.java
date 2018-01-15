package com.zc.common.core.utils;

/**
 * 对象转换成字符串
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月8日 下午1:00:45
 * 
 */
public class MyObjectUtils {

	public static String toString(final Object obj) {
		return obj == null ? "" : obj.toString();
	}
}
