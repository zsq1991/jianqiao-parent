package com.zc.common.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.common.core.utils.MD5Util;
import com.zc.common.core.utils.SignUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @description 验签拦截器
 * @author system
 * @date 2018-01-23 16:42
 * @version 1.0.0
 */
public class SpringValidateSignatureInterceptor extends HandlerInterceptorAdapter {

	private static Log logger = LogFactory
			.getLog(SpringValidateSignatureInterceptor.class);

	private String mark;

	private String[] publicurl;

	public SpringValidateSignatureInterceptor(String mark, String[] publicurl){
		this.mark = mark;
		this.publicurl = publicurl;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
		Result result=new Result();
		Object objSign=request.getParameter("sign");
		Object objType=request.getParameter("client_type");
		Object objTimestamp=request.getParameter("timestamp");
		//获取请求路径
		String requestURI = request.getRequestURI();
		if(publicurl!=null && publicurl.length>0) {
			for (String url : publicurl) {
				if (url.equals(requestURI)) {
					return true;
				}
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String iType="I";
		String aType="A";
		if(objType == null){
			PrintWriter out= response.getWriter();
			result.setCode(201);
			result.setMsg("客户端为空");
			logger.info(JSON.toJSON(result).toString());
			out.print(JSON.toJSON(result).toString());
		}else if(objTimestamp == null){
			PrintWriter out= response.getWriter();
			result.setCode(202);
			result.setMsg("时间戳为空");
			logger.info(JSON.toJSON(result).toString());
			out.print(JSON.toJSON(result).toString());
		}else{
			if(!(iType.equals(objType.toString()) || aType.equals(objType.toString()))){
				PrintWriter out= response.getWriter();
				result.setCode(203);
				result.setMsg("客户端异常");
				logger.info(JSON.toJSON(result).toString());
				out.print(JSON.toJSON(result).toString());
			}else{
				Map<String, String[]> map = request.getParameterMap();
				Map<String, String[]> map1 = Maps.newHashMap();
				map1.putAll(map);
				String[] url = {requestURI};
				map1.put("url", url);
				String signData = SignUtils.mapToLinkString2(map1);
				signData = StringEscapeUtils.unescapeXml(signData);
				String sign = "";
				String md5 = MD5Util.getMD5Encode(signData,"utf-8")+mark+objType.toString();
				byte[] b = null;
			    try {
			         b = (md5).getBytes("utf-8");
			    } catch (Exception e) {
			         e.printStackTrace();
			         logger.info(signData+":签名加密异常");
			    }
			    if (b != null) {
			        sign = Base64Utils.encodeToString(b);
			    }
				if(null!=objSign)
				{
					logger.info(objSign+"===================="+sign);
					String signApp = objSign.toString();
					if((signApp.trim()).equals(sign.trim())){
						result.setCode(0);
						result.setMsg("验签通过");
						logger.info(signData+":签名=="+ JSON.toJSON(result).toString());
						return true;
					}else{
						PrintWriter out= response.getWriter();
						result.setCode(204);
						result.setMsg("验签失败");
						logger.info(signData+":签名=="+ JSON.toJSON(result).toString());
						out.print(JSON.toJSON(result).toString());
					}
				}else{
					PrintWriter out= response.getWriter();
					result.setCode(205);
					result.setMsg("签名为空");
					logger.info(signData+":签名=="+ JSON.toJSON(result).toString());
					out.print(JSON.toJSON(result).toString());
				}
			}
		}
		return false;
	}
}
