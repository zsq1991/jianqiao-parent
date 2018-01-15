package com.zc.common.core.net.html;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-11-4 下午3:07:48
 * 
 */
public class HttpUtils {

	private HttpUtils() {
	}
	/**
	 * 
	 * @param url
	 * @param httpMethod
	 * @param multiValueMap
	 * @param headerRequest
	 * @return
	 */
	public static ResponseEntity<String> getHttp(final String url, HttpMethod httpMethod,
		MultiValueMap<String, String> multiValueMap, HttpHeaders headerRequest) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		// 进行头部参数和一般参数集合
		HttpEntity<MultiValueMap<String, String>> bodyAndHeader = new HttpEntity<MultiValueMap<String, String>>(
				multiValueMap, headerRequest);
		responseEntity = restTemplate.exchange(url, httpMethod, bodyAndHeader, String.class);
		return responseEntity;
	}
}
