package com.zc.common.core.string;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @create-time 2011-12-14 下午:12:54
 * 
 */
public class MyStringUtils {
	/**
	 * 取字符串两个字符中间的值
	 * 
	 * @param str
	 * @param before 前符号
	 * @param after 后符号
	 * @return
	 */
	public static String getMiddleString(String str, String before, String after) {
		return StringUtils.substringBefore(StringUtils.substringAfter(str, before), after);
	}

	/**
	 * 对字符串进行正则验证
	 * 
	 * @param vali 验证的字符串
	 * @param patterns 正则表达式
	 * @return
	 */
	public static Boolean valiRegexp(String vali, String patterns) {
		Pattern pattern = Pattern.compile(patterns);
		Matcher matcher = pattern.matcher(vali);
		return matcher.find() ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * 打印出字符串的本来面目
	 * 
	 * @param s
	 */
	public static String escape(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				boolean chb = false;
				/*if((ch >= '\u0000' && ch <= '\u001F') || (ch >= '\u007F' && ch <= '\u009F')
						|| (ch >= '\u2000' && ch <= '\u20FF')){
					chb = true;
				}*/
				if (ch >= '\u0000' && ch <= '\u001F'){
					chb = true;
				}
				if (ch >= '\u007F' && ch <= '\u009F'){
					chb = true;
				}
				if (ch >= '\u2000' && ch <= '\u20FF'){
					chb = true;
				}
				if (chb) {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					int num = 4;
					for (int k = 0; k < num - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 判断字符串的编码
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncoding(final String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 将类名转换成表名
	 * 
	 * @param prefix
	 * @param clazz
	 * @return
	 */
	public static String classToTablename(final String prefix, final Class<?> clazz) {
		String tablename = null;
		String className = clazz.getSimpleName();
		tablename = stringToTableName(className);
		if (StringUtils.isNotBlank(StringUtils.trim(prefix))) {
			tablename = prefix + "_" + tablename;
		}
		return tablename;
	}

	/**
	 * 将字符串显示为表明的显示格式
	 * 
	 * @param name
	 * @return
	 */
	public static String stringToTableName(final String name) {
		char[] cs = name.toCharArray();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] >= 65 && cs[i] <= 90) {
				if (i == 0) {
					char c = (char) (cs[i] + 32);
					stringBuffer.append(c);
				} else {
					char c = (char) (cs[i] + 32);
					stringBuffer.append("_");
					stringBuffer.append(c);
				}
			} else {
				stringBuffer.append(cs[i]);
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 字符串占位符工具类
	 * 
	 * 
	 * @param map 键值为相关的占位符名称
	 * @param placeholder 带有${name} 类型的占位符字符串
	 * @return
	 */
	public static String placeholderString(Map<String, String> map, String placeholder) {
		StrSubstitutor sub = new StrSubstitutor(map);
		return sub.replace(placeholder);
	}
}
