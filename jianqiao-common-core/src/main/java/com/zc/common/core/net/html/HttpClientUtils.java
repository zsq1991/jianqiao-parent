package com.zc.common.core.net.html;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.zc.common.core.utils.DebugUtils;

/**
 * 
 * 获取http请求
 * @author czb
 * @e-mail chengzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-5-23 下午11:41:58
 * 
 */

public class HttpClientUtils {

	public HttpClientUtils(){};
	
	
	/**
	 * GET
	 * 方法的作用:GET 请求http.<br/>
	 * 
	 * @param url
	 * @return
	 * 
	 * @author czb
	 * @create-time 2013-10-16 上午9:11:40
	 */
	public static String getHttpsToGet(String url){
		RestTemplate restTemplate=new RestTemplate();
		String result=restTemplate.getForObject(url, String.class);
		return result;
		
	}
	
	
	/**
	 * POST
	 * 方法的作用:RestTemplate POST 请求获取http.<br/>
	 * 
	 * @param url 请求的地址
	 * @param map 请求的参数
	 * @return
	 * 
	 * @author czb
	 * @create-time 2013-10-16 上午9:10:33
	 */
    public static String getHttpsToPost(String url,MultiValueMap<String, String> map ){
        RestTemplate restTemplate=new RestTemplate();
        String result=restTemplate.postForObject(url, map, String.class);
        return result;
        
    }
    

    /**
     * 
     * 方法的作用:带头部的get或post 请求.<br/>
     * 
     * @param url 请求的路径
     * @param multiValueMap 请求的参数
     * @param headerRequest 设置头部参数
     * @param type 请求方式get/post
     * @return String
     *  
     * @author czb
     * @create-time 2013-10-21 下午4:42:39
     */
    public static String geHttpsAll(String url,MultiValueMap<String, String> multiValueMap,HttpHeaders headerRequest,String type){
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=null;
        //进行头部参数和一般参数集合
        HttpEntity<MultiValueMap<String, String>> bodyAndHeader=new HttpEntity<MultiValueMap<String, String>>(multiValueMap,headerRequest);
        
        if ("GET".equals(StringUtils.upperCase(type))) {
          responseEntity = restTemplate.exchange(url, HttpMethod.GET, bodyAndHeader, String.class);
        }else if("POST".equals(StringUtils.upperCase(type))){
          responseEntity = restTemplate.exchange(url, HttpMethod.POST, bodyAndHeader, String.class);
        }
        String jsonResult = responseEntity.getBody();
        return jsonResult;
    }
    
    
    /**
     * 
     * 方法的作用:获取头部信息.<br/>
     * 
     * @param url 请求的路径
     * @param multiValueMap 请求的参数
     * @param type 请求方式get/post
     * @return HttpHeaders
     *  
     * @author czb
     * @create-time 2013-10-21 下午4:42:39
     */
    public static ResponseEntity<String> getHttpHeaders(String url,MultiValueMap<String, String> multiValueMap,HttpHeaders headerRequest,String type){
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=null;
        //进行头部参数和一般参数集合
        HttpEntity<MultiValueMap<String, String>> bodyAndHeader=new HttpEntity<MultiValueMap<String, String>>(multiValueMap,headerRequest);
        if ("GET".equals(StringUtils.upperCase(type))) {
          responseEntity = restTemplate.exchange(url, HttpMethod.GET, bodyAndHeader, String.class);
        }else if("POST".equals(StringUtils.upperCase(type))){
          responseEntity = restTemplate.exchange(url, HttpMethod.POST, bodyAndHeader, String.class);
        }
        return responseEntity;
    }
	
	
	/**
	 * 判断远程url是否存在
	 * @param uRLName url地址
	 * @return
	 */
	public static boolean exists(String uRLName) {
		try {
			// 设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）
			HttpURLConnection.setFollowRedirects(false);
			// 到 URL 所引用的远程对象的连接
			HttpURLConnection con = (HttpURLConnection) new URL(uRLName).openConnection();
			// 设置 URL 请求的方法,GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的,具体取决于协议的限制
			con.setRequestMethod("HEAD");
			// 从 HTTP 响应消息获取状态码
			DebugUtils.println("从 HTTP 响应消息获取状态码  :con.getResponseCode():"+con.getResponseCode());
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 判断链接类型是否为文档等内容
	 * @param uRL 链接url地址
	 * @return
	 */
	public static Boolean linkType(String uRL) {
		Boolean result = false;
		String linkType = "";
		if (uRL.contains(".")) {
			linkType = uRL.substring(uRL.lastIndexOf("."));
		}
		DebugUtils.println("linkType:" + linkType);
		if (linkType.contains("doc") || linkType.contains("xls") || linkType.contains("ppt") || linkType.contains("pdf") || linkType.contains("rar")) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
}
