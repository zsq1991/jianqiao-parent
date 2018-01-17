package com.zc.common.core.cxf.inteceptor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * CXF的发出信息拦截器，主要是为发出到服务器的信息，加上cookie的header
 * 
 * @author zhangkaoqin
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-8-24 下午06:10:05
 * 
 */
public class SessionOutInterceptor extends AbstractPhaseInterceptor<SoapMessage>{
	public SessionOutInterceptor() {
		super(Phase.WRITE);
	}
	public SessionOutInterceptor(String phase) {
		super(phase);
	}
	@Override
    @SuppressWarnings("unchecked")
	public void handleMessage(SoapMessage arg0) throws Fault {
		Map<String, List<String>> headers = (Map<String, List<String>>) arg0.get(Message.PROTOCOL_HEADERS);
		headers.put("Cookie", Collections.singletonList("JSESSIONID="+Sessionvalue.getInstance().getSessionId()));
	}

}
