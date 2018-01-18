package com.zc.main.controller.main.mobile.after.membermsg;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.consultation.ConsultationService;
import com.zc.main.service.membermsg.MemberMsgService;
import org.springframework.web.bind.annotation.*;

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
	/**
	 *
	 * @param members 用户
	 * @param type 审核通知，2赞通知，3评论通知
	 * @pram  msgId MemberMsg表中的id只有type为1的时候是必填
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value="readtype",method = RequestMethod.POST)
	@ResponseBody
	public Result getReadInformList(@MemberAnno Member members,
									@RequestParam(value ="msgId",defaultValue="0",required=false)Long msgId,
									@RequestParam(value ="type") Integer type){
		return memberMsgService.getReadInformList(members,msgId,type);

	}
	/**
	 *
	 * @param members
	 * @param page
	 * @param rows
	 * @param type 1审核通知，2赞通知，3评论通知
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value="notification",method = RequestMethod.POST)
	@ResponseBody
	public Result getInformList(@MemberAnno Member members,
								@RequestParam(value="page",defaultValue="1",required=false)Integer page,
								@RequestParam(value="rows",defaultValue="10",required=false)Integer rows,
								@RequestParam(value ="type") Integer type){

		return memberMsgService.getInformList(members,page,rows,type);
	}
}
