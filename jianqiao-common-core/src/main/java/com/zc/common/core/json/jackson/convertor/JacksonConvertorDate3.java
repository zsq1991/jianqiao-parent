package com.zc.common.core.json.jackson.convertor;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * long类型转换成yyyy-MM-dd HH:mm:ss字符串
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-10-23 上午2:19:38
 * 
 */
public class JacksonConvertorDate3 extends JsonSerializer<Long>{

	@Override
	public void serialize(Long arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		arg1.writeString(DateFormatUtils.format(new Date(arg0), "yyyy-MM-dd HH:mm:ss"));
	}

}
