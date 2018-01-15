package com.zc.common.core.net.html;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-12 下午1:11:44
 * 
 */
public class JsoupUtils {
	/**
	 * 过滤Html标签,只留纯文本，清除xss代码
	 * 
	 * @param bodyHtml 内容
	 * @return
	 */
	public static String cleanHtmlToSimpleText(String bodyHtml) {
		String content = Jsoup.clean(bodyHtml, Whitelist.basic());
		return content;
	}
}
