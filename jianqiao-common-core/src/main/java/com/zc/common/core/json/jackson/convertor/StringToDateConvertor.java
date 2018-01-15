package com.zc.common.core.json.jackson.convertor;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.zc.common.core.date.DateUtils;

/**
 * 字符串转换成日期类型
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-13 上午11:42:27
 * 
 */
public class StringToDateConvertor implements Converter<String, Date> {

	@Override
	public Date convert(String arg0) {
		if (StringUtils.isNotBlank(arg0)) {
			return DateUtils.dateFormat(arg0, "yyyy-MM-dd HH:mm:ss");
		} else {
			return null;
		}
	}

}
