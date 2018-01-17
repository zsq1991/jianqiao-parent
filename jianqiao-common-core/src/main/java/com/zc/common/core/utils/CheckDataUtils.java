package com.zc.common.core.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: CheckDataUtils
 * @Description: 验证工具类
 * @author czb
 * @e-mail chengzhenbing@139.com
 * @version v1.0
 * @copyright 2014-2016
 * @date 2014-5-23 下午12:53:59
 * 
 */
public class CheckDataUtils {
	public CheckDataUtils() {
	}

	public static String checkString(String start) {
		String end = null;
		if (start == null) {
            return "";
        }
		int length = start.length();
		int sub = start.indexOf("'");
		if (sub == -1) {
            end = start;
        } else if (length == sub) {
            end = (new StringBuilder(String.valueOf(start))).append("'")
                    .toString();
        } else {
            end = (new StringBuilder(
                    String.valueOf(start.substring(0, sub + 1)))).append("'")
                    .append(checkString(start.substring(sub + 1, length)))
                    .toString();
        }
		return end;
	}

	/***
	 * 判断是否为中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCn(String str) {

		if (str == null || str.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]+$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;

		}
		return true;
	}

	/***
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumer(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/***
	 * 判断是否为字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLetters(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("[a-zA-Z]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}


	/****************** 数据类型转换 ********************/
	public static Long getLong(String str) {
		if (str != null && !str.equals("")) {
			return Long.parseLong(str);
		} else {
			return null;
		}
	}
	
	public static Integer getInteger(String str) {
		if (str != null && !str.equals("")) {
			return Integer.parseInt(str);
		} else {
			return null;
		}
	}

	public static String getDouble(Double dob) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(dob);
	}

	/**
	 * 获取客户端IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	
	/**
	 * 
	* @Title: utf8Togb2312 
	* @Description: 编码转换
	* @param @param str
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String utf8Togb2312(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(
							str.substring(i + 1, i + 3), 16));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				}
				i += 2;
				break;
			default:
				sb.append(c);
				break;
			}
		}
		// Undo conversion to external encoding
		String result = sb.toString();
		String res = null;
		try {
			byte[] inputBytes = result.getBytes("8859_1");
			res = new String(inputBytes, "UTF-8");
		} catch (Exception e) {
		}
		return res;
	}

}
