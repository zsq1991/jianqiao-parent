package com.zc.common.core.word.poi;

import java.io.IOException;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

/**
 * 
 * 
 * @author zhangkaoqin
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-12-8 上午10:09:28
 * 
 */
public class PoiWordUtils {
	/**
	 * 获取2007版本以上的word编辑对象
	 * 
	 * @param fileName
	 * @return
	 * @throws XmlException
	 * @throws OpenXML4JException
	 * @throws IOException
	 */
	public static XWPFWordExtractor getDocument2007(final String fileName) throws XmlException,
			OpenXML4JException, IOException {
		return new XWPFWordExtractor(POIXMLDocument.openPackage(fileName));
	}

	/**
	 * 得到word的内容
	 * 
	 * @param xwpfWordExtractor
	 * @return
	 */
	public static String getContent2007(final XWPFWordExtractor xwpfWordExtractor) {
		return xwpfWordExtractor.getText();
	}
}
