package com.zc.main.controller.main.mobile.after.consultationfabulous;


import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Reference;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.result.Result;
import com.zc.main.service.consultationfabulous.ConsultationFabulousService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 咨询内容点赞
 *
 */
@RequestMapping("mobile/after/consultationcfabulous")
@Controller
public class ConsultationfabulousController {
	
	private static Logger logger = LoggerFactory.getLogger(ConsultationfabulousController.class);

	@DubboConsumer(version = "1.0.0",timeout = 30000,check = false)
	private ConsultationFabulousService consultationFabulousService;
	
	@Explosionproof
	@RequestMapping(value="fabulous",method= RequestMethod.POST)
	@ResponseBody
	public Result consultationcfabulous(@RequestParam("id")Long id,
										@RequestParam("type")Integer type,
										@RequestParam("memberId")Long memberId){
		return consultationFabulousService.getConsultationFabulousByIdAndMemberId(id, memberId, type);
		
	}
	
	@RequestMapping(value="fabulous-type",method= RequestMethod.POST)
	@ResponseBody
	public Result getConsultationType(@RequestParam("id")Long id,
									  @RequestParam("memberId")Long memberId){
		
		return consultationFabulousService.getConsultationType(id, memberId);
		 
	}
	
}
