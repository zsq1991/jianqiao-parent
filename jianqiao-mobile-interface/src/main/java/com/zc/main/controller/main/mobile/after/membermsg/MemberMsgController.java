package com.zc.main.controller.main.mobile.after.membermsg;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.consultation.ConsultationService;
import com.zc.main.service.membermsg.MemberMsgService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package : com.zc.main.controller.main.mobile.after.membermsg
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : asus 王鑫涛
 * @Creation Date ：2018年01月18日10:37
 */
@RequestMapping("mobile/after/membermsg")
@RestController
public class MemberMsgController {

	@DubboConsumer(version = "1.0.0",timeout = 30000)
	private MemberMsgService memberMsgService;

	/**
	 * 读取通知信息的接口
	 */
	@RequestMapping(value="readInform",method = RequestMethod.POST)
	@ResponseBody
	public Result getMemberMsgReadInform(@MemberAnno Member members){
		return memberMsgService.getMemberMsgReadInform(members);
	}
}
