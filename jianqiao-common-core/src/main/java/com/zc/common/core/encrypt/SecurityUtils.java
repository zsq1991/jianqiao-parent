package com.zc.common.core.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.zc.common.core.encode.EncodeUtils;


/**
 * 
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-3-30 上午10:51:22
 * 
 */
public class SecurityUtils {
	private SecurityUtils() {
	}

	/**
	 * MD5加密后经过Hex加密
	 * 
	 * @param buff
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws CloneNotSupportedException
	 */
	public static String getMD5(String str) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		return EncodeUtils.hexEncode(digest.digest(str.getBytes()));
	}

	/**
	 * SHA加密
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getSHA(String str) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA");
		return digest.digest(str.getBytes());
	}
}
