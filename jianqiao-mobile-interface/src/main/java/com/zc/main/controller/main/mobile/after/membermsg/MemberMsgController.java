package com.zc.main.controller.main.mobile.after.membermsg;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.membermsg.MemberMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger = LoggerFactory.getLogger(MemberMsgController.class);

	@DubboConsumer(version = "1.0.0",timeout = 30000)
	private MemberMsgService memberMsgService;

	/**
	 * @description方法说明  读取通知信息的接口
	 * @author 王鑫涛
	 * @date  13:35  2018/1/19
	 * @version 版本号
	 * @param members
	 * @return
	 */
	@RequestMapping(value="readInform",method = RequestMethod.POST)
	@ResponseBody
	public Result getMemberMsgReadInform(@MemberAnno Member members){
		logger.info("-----------进入读取通知信息的接口----------------");
		return memberMsgService.getMemberMsgReadInform(members);
	}
	/**
	 *@description方法说明 系统消息读取
	 * @author 王鑫涛
	 * @date  13:35  2018/1/19
	 * @version 版本号
	 * @param members 用户
	 * @param type 审核通知，2赞通知，3评论通知
	 * @pram msgId  MemberMsg表中的id只有type为1的时候是必填
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value="readtype",method = RequestMethod.POST)
	@ResponseBody
	public Result getReadInformList(@MemberAnno Member members,
									@RequestParam(value ="msgId",defaultValue="0",required=false)Long msgId,
									@RequestParam(value ="type") Integer type){
		logger.info("-----------进入系统消息读取的接口----------------");
		return memberMsgService.getReadInformList(members,msgId,type);

	}
	/**
	 *@description方法说明 我的消息通知
	 * @author 王鑫涛
	 * @date  13:38  2018/1/19
	 * @version 版本号
	 * @param members  用户
	 * @param page 当前页数
	 * @param rows	每页影响的行数
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
		logger.info("-----------进入我的消息通知的接口----------------");
		return memberMsgService.getInformList(members,page,rows,type);
	}
}
