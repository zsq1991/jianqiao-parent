package com.common.util.securitycode;

import com.alibaba.fastjson.JSONObject;
import com.common.util.http.HttpClientUtils;
import com.common.util.http.HttpResult;
import com.mysql.jdbc.StringUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description : 验证码
 * @author  by : tenghui
 * @Creation Date ：2018/1/17 10:00
 */
public class TencentSms {
	
	private static Logger logger = LoggerFactory.getLogger(TencentSms.class);

	private final static String SEND_MSG_URL="http://123.207.175.228:9999/four/mobile/view/check/sendMsg";
	private final static String CHECK_MSG_URL="http://123.207.175.228:9999/four/mobile/view/check/checkMsg";
	private final static String TWO_ELEMENT_URL="http://123.207.175.228:5200/mobile/view/checkInfoCard/checkUserInfo";

	/**
     * @description ：发送验证码
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/17 10:00
     * @version : 1.0.0
     * @param phone 手机号
     * @param codeType 验证码格式
     * @return
     */
	public Result sendMsg(String phone, String codeType) {
        logger.info("===============进入连接pay发送验证码方法=================");
		Result result = new Result();
        logger.info("=======检验手机号是否合法=========");
		String reg = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(phone);
		if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
			return ResultUtils.returnError("手机号不能为空");
		} else if (!m.matches()) {
			return ResultUtils.returnError("手机号不符合要求");
		}
		try {
			//发送短信路径
			String msgUrl = SEND_MSG_URL;
            logger.info("发送路径:"+msgUrl);
			Map<String, String> params = new HashedMap();
            params.put("phone",phone);
            params.put("codeType",codeType);
            logger.info("=======开始请求=========");
            HttpResult httpResult = HttpClientUtils.sendGet(msgUrl, params);
            logger.info("=======请求结束,即将返回响应=========");
            JSONObject json = JSONObject.parseObject(httpResult.getResponseContent());
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
            logger.info("===============连接pay发送验证码方法结束,响应已返回=================");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("连接pay发送验证码接口异常"+e.getMessage());
			return ResultUtils.returnError("发送失败");
		}
	}
    /**
     * @description ：验证验证码
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/17 10:02
     * @version : 1.0.0
     * @param phone 手机号
     * @param code 验证码
     * @param codeType 验证码格式
     * @return
     */
	public Result checkMsg(String phone, String code, String codeType) {
        logger.info("===============进入连接pay验证验证码方法=================");
		Result result = new Result();
		try {
			//发送短信路径
			String msgUrl =CHECK_MSG_URL;
            logger.info("发送路径:"+msgUrl);
            Map<String, String> params = new HashedMap();
            params.put("phone",phone);
            params.put("code",code);
            params.put("codeType",codeType);
            logger.info("=======开始请求=========");
            HttpResult httpResult = HttpClientUtils.sendGet(msgUrl, params);
            logger.info("=======请求结束,即将返回响应=========");
            JSONObject json = JSONObject.parseObject(httpResult.getResponseContent());
            result.setCode(Integer.parseInt(json.get("code").toString()));
            result.setMsg(json.get("msg").toString());
            logger.info("===============连接pay验证验证码方法结束,响应已返回=================");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("连接pay验证验证码接口异常"+e.getMessage());
			return ResultUtils.returnError("调用验证验证码接口异常");
		}
	}

	/**
	 * 二要素验证（身份证和姓名）
	 * @author huangxin
	 * @data 2018/1/19 16:57
	 * @Description: 二要素验证（身份证和姓名）
	 * @Version: 3.2.0
	 * @param name 姓名
	 * @param carId 身份证号
	 * @return
	 */
	public Result checkCarId(String name, String carId){
		logger.info("=================进入二要素信息验证=================");
		Result result = new Result();
		try{
			//二要素地址
			String element = TWO_ELEMENT_URL;
			logger.info("二要素地址："+element);
			Map<String, String> params = new HashedMap();
			params.put("name",name);
			params.put("carId",carId);
			logger.info("=======开始请求=========");
			HttpResult httpResult = HttpClientUtils.sendGet(element, params);
			logger.info("=======请求结束,即将返回响应=========");
			JSONObject json = JSONObject.parseObject(httpResult.getResponseContent());
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			logger.info("===============连接pay验证二要素方法结束,响应已返回=================");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("连接pay验证二要素接口异常"+e.getMessage());
			return ResultUtils.returnError("调用二要素接口异常");
		}
	}


}
