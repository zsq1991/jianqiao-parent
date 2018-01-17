package com.zc.common.core.cxf.inteceptor;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * 对接收到的session信息进行记录的拦截器
 * 
 * @author zhangkaoqin
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-8-24 上午11:15:04
 * 
 */
public class SessionInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	public SessionInterceptor() {
		super(Phase.RECEIVE);
	}

	public SessionInterceptor(String phase) {
		super(phase);
	}

	@Override
    @SuppressWarnings("unchecked")
	public void handleMessage(SoapMessage arg0) throws Fault {
		Sessionvalue sessionvalue = Sessionvalue.getInstance();
		Map<String, List<String>> headers = (Map<String, List<String>>) arg0
				.get(Message.PROTOCOL_HEADERS);
		String result = "";
		if (headers.containsKey("Set-Cookie")) {
			result=StringUtils.substringAfter(
					StringUtils.substringBefore(headers.get("Set-Cookie").get(0), ";"), "=");
			if (StringUtils.isNotBlank(sessionvalue.getSessionId())) {
				sessionvalue.setSessionId(result);
			} else if (StringUtils.isNotBlank(result)) {
				if (!sessionvalue.getSessionId().equals(result)) {
					sessionvalue.setSessionId(result);
				}
			}
		}
	}

}
