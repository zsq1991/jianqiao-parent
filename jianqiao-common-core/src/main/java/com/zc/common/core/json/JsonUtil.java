package com.zc.common.core.json;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Title: JsonUtil.java
 * @Description: json解析工具类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月12日 下午4:57:55 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 *
 */
public class JsonUtil {

	private JsonUtil() {
	};

	/**
	 * 
	 * 把json字符串中解析成java对象
	 * 
	 * @param jsonStr json格式字符串
	 * @param clazz java类
	 * @return clazz的实例
	 */
	public static <T> T jsonToObject(final String jsonStr, final Class<T> clazz) {
		return JSON.parseObject(jsonStr, clazz);
	}

	/**
	 * 实体/列表/HashMap... 转换成 JSON
	 * 
	 * @param object
	 * @return
	 */
	public String objectToJson(final Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 把字符串转为list集合
	 * 
	 * @param jsonString json格式的字符串[{},{}]
	 * @param nodes 要转换的节点名称
	 * @param class1 实体类的名称
	 * @return
	 */
	public static <T> List<T> stringToList(final String jsonString, final String nodeName,
			final Class<T> class1) {
		JSONObject jsonObject = JSON.parseObject(jsonString);
		JSONArray jsonArray = jsonObject.getJSONArray(nodeName);
		List<T> lists = JSON.parseArray(jsonArray.toJSONString(), class1);
		return lists;
	}

	/**
	 * 把字符串转换为实体类
	 * 
	 * @param jsonString json格式的字符串
	 * @param nodes 要转换的节点名称
	 * @param class1 实体类的名称
	 * @return
	 */
	public static <T> T stringToEntity(final String jsonString, final String nodeName,
			final Class<T> class1) {
		// 把字符串转化为jsonObjecty
		JSONObject jsonObject = JSON.parseObject(jsonString);
		// 把节点转换为字符串
		String jsonStr = jsonObject.getJSONObject(nodeName).toJSONString();
		return JSON.parseObject(jsonStr, class1);

	}

}
