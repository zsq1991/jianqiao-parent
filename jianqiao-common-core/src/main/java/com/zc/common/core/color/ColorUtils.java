package com.zc.common.core.color;

import java.awt.Color;

import org.apache.commons.lang.StringUtils;

/**
 * 颜色工具类
 * 
 * @author zhangkaoqin
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-5-23 下午09:34:14
 * 
 */
public class ColorUtils {
	private ColorUtils() {
	}

	/**
	 * 将颜色的RGB转换成16进制的颜色显示
	 * 
	 * @param color
	 * @return
	 */
	public static String rGB2Hex(final Color color) {
		String r = Integer.toHexString(color.getRed());
		r = StringUtils.rightPad(r, 2, '0');
		String g = Integer.toHexString(color.getGreen());
		g = StringUtils.rightPad(g, 2, '0');
		String b = Integer.toHexString(color.getBlue());
		b = StringUtils.rightPad(b, 2, '0');
		return r + g + b;
	}

	/**
	 * 将16进制的颜色转换成RGB对象
	 * 
	 * @param hex
	 * @return
	 */
	public static Color hex2RGB(final String hex) {
		String r = StringUtils.substring(hex, 0, 2);
		String g = StringUtils.substring(hex, 2, 4);
		String b = StringUtils.substring(hex, 4, 6);
		return new Color(Integer.parseInt(r, 16), Integer.parseInt(g, 16), Integer.parseInt(b, 16));
	}
}
