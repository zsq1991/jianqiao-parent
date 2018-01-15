package com.zc.common.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.zc.common.core.encrypt.SecurityUtils;

/**
 * 唯一数据获取工具
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-3-5 下午4:31:58
 * 
 */
public class UniqueUtils {
	private UniqueUtils() {
	}

	/**
	 * 订单生成
	 * 
	 * @return
	 */
	public synchronized static String getOrder() {
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
				+ RandomStringUtils.randomNumeric(6);
	}

	/**
	 * 获取uuid
	 * 
	 * @return
	 */
	public synchronized static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 工程拦截器加密
	 * 
	 * @param projectName
	 * @param projectCode
	 * @param sign
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean getInterceptorSign(final String projectName, final String projectCode,
			final String currentTime, final String sign) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		return StringUtils.equals(
				StringUtils.substring(
						SecurityUtils.getMD5(StringUtils.substring(
								SecurityUtils.getMD5(projectName + projectCode), 0, 16)
								+ currentTime), 0, 16), sign);
	}

	/**
	 * 工程拦截器加密结果的获取
	 * 
	 * @param projectName
	 * @param projectCode
	 * @param sign
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getInterceptorSign(final String projectName, final String projectCode,
			final String currentTime) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return StringUtils.substring(
				SecurityUtils.getMD5(StringUtils.substring(
						SecurityUtils.getMD5(projectName + projectCode), 0, 16)
						+ currentTime), 0, 16);
	}
}
