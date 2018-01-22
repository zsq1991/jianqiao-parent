package com.zc.common.core.utils;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.alibaba.fastjson.JSONObject;
import com.zc.common.core.net.html.HttpClientUtils;

/**
 * 
 * @Title: LocationUtil.java
 * @Description: 根据IP获取地理位置工具类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014年12月23日 下午4:08:32
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class LocationUtilTest {
	private static String ak = "16f2cd19e8f5cf23dcf656e5f53e7b45";// 百度web服务API密钥AK
	private static String url = "http://api.map.baidu.com/location/ip";// 获取地理位置API接口

	/**
	 * 
	* @Title: getLocation 
	* @Description: 根据IP获取地理位置 （省份+城市）
	* @param @param ip 外网ip
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getLocation(String ip) {
		String address = null;
		try {
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("ak", ak);
			map.add("ip", ip);
			String result = HttpClientUtils.getHttpsToPost(url, map);
			JSONObject resultJson = JSONObject.parseObject(result);
			String content = resultJson.getString("content");
			JSONObject contentJson = JSONObject.parseObject(content);
			address = contentJson.getString("address");
		} catch (Exception e) {
			address = "获取地理位置失败";
		}
		return address;
	}
	
	
	@Test
	public void test(){
		System.out.println("ip所属地区："+getLocation("117.29.170.130"));
	}

}
