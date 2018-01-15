package com.zc.common.core.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * @author zhangkaoqin
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-6-25 下午05:09:48
 * 
 */
public class MyIOUtils {
	private MyIOUtils() {
	}

	/**
	 * 读取流对象内容
	 * 
	 * @param inputStream
	 * @param encoding 可以有也可以没
	 * @return
	 */
	public static List<String> readLines(final InputStream inputStream, final String encoding) {
		List<String> list = null;
		try {
			if (StringUtils.isNotBlank(encoding)) {
				list = IOUtils.readLines(inputStream, encoding);
			} else {
				list = IOUtils.readLines(inputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return list;
	}

	/**
	 * 将流转换成字符串
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String read(final InputStream inputStream) throws IOException {
		String result = null;
		result = IOUtils.toString(inputStream);
		IOUtils.closeQuietly(inputStream);
		return result;
	}

	/**
	 * 将字符串转变成流的形式
	 * 
	 * @param string
	 * @param ecoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static InputStream stringToStream(String string, String ecoding)
			throws UnsupportedEncodingException {
		return new ByteArrayInputStream(string.getBytes(ecoding));
	}
}
