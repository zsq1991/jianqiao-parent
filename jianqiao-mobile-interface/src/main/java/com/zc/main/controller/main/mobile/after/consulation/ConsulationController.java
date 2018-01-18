package com.zc.main.controller.main.mobile.after.consulation;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.consultation.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package : com.zc.main.controller.main.mobile.after.consulation
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : asus 王鑫涛
 * @Creation Date ：2018年01月17日16:31
 */
@RequestMapping("mobile/after/consultation")
@RestController
public class ConsulationController {
	@DubboConsumer(version = "1.0.0",timeout = 30000)
	private ConsultationService consultationService;


	@Explosionproof
	@RequestMapping(value="deleteconsultation",method= RequestMethod.POST)
	public Result deleteConsultationById(@RequestParam("id")Long id,
										 @MemberAnno Member member){

		return this.consultationService.deleteConsultationById(id, member);
	}

	/**
	 * 获取父级主题
	 * @param type
	 * @param member
	 * @return
	 */
	@RequestMapping(value="getParentConsultation",method=RequestMethod.POST)
	public Result getParentConsultation(@RequestParam("type")String type,
										@MemberAnno Member member,
										@RequestParam(value = "page",defaultValue ="1",required = false)Integer page,
										@RequestParam(value="size",defaultValue ="10",required = false)Integer size){

		return consultationService.getParentConsultation(type, member,page,size);
	}
}
