package com.zc.main.controller.main.mobile.after.consultationcomment;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.comment.ConsultationCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 咨询评论
 * @author Administrator
 *
 */
@RequestMapping("mobile/after/consultationcomment")
@Controller
public class ConsultationCommentController {

	private static Logger logger = LoggerFactory.getLogger(ConsultationCommentController.class);

	@DubboConsumer(version="1.0.0")
	private ConsultationCommentService consultationCommentService;


	/**
	 * 用户相互回复评论
	 * @param type          如果是给咨询评论者回复是0，给咨询评论者回复的人进行回复是1
	 * @param parentid		回复评论的id	
	 * @param content		回复内容
	 * @param member			
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value="replyconsultationcomment",method= RequestMethod.POST)
	@ResponseBody
	public Result replyConsultationComment(
			@RequestParam(value="type") Integer type,
			@RequestParam(value="parentid") Long parentid,
			@RequestParam(value="content") String content,
			@MemberAnno Member member){
		
		return consultationCommentService.saveReplyconsultationCommentService(member.getId(),type,parentid,content);
	}

}
