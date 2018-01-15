package com.zc.common.core.json.jackson.convertor;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 通过jackson将字符串限制在一定的长度之内
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月25日 下午4:19:00
 * 
 */
public class StringToSubStringConvertor extends JsonSerializer<String>{

	@Override
	public void serialize(String arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		arg1.writeString(StringUtils.abbreviate(arg0, 14));
	}

}
