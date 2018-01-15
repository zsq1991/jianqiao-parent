package com.zc.common.core.xml.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

/**
 * xstream单例工具默认采用dom4j工具
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-24 上午11:34:20
 * 
 */
public class XStreamUtils {
	private XStreamUtils() {
	}

	private static XStreamUtils X_STREAM_UTILS;
	private static XStream xStream;
	static {
		X_STREAM_UTILS = new XStreamUtils();
		xStream = new XStream(new Dom4JDriver());
		xStream.autodetectAnnotations(true);
	}

	public static XStreamUtils getStreamUtils() {
		return X_STREAM_UTILS;
	}

	public XStream getXStream() {
		return xStream;
	}
}
