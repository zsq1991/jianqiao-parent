package com.zc.common.core.utils;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-10-29 上午9:29:45
 * 
 */
public class PasswordUtils {

	private PasswordUtils() {
	}

	/**
	 * 根据密码和id加密出密码数据
	 * 
	 * @param password
	 * @param id
	 * @return
	 */
	public static String getUserPassword(final String password) {
		return new StandardPasswordEncoder().encode(password);
	}
}
