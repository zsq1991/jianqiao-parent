package com.zc.main.service.consultationcommentfabulous;


import com.zc.common.core.result.Result;

public interface ConsultationCommentFabulousService {

	/**
	 * @description ：给咨询的评论点赞
	 * @Created by  : gaoge
	 * @Creation Date ： 2018/1/16 16:53
	 * @version 1.0.0
	 * @param : type 1点赞   2取消赞
	 * @param :commentid 咨询id
	 * @param :memberid 用户id
	 * @return :
	 */
	public Result saveConsultationCommentFabulous(Long commentid, Long memberid, Integer type);

}
