package com.zc.main.service.comment;


import com.zc.common.core.result.Result;

/**
 * 咨询评论
 * @author Administrator
 *
 */
public interface ConsultationCommentService {
	
	/**
	 * 直接评论咨询
	 * @param memberid
	 * @param consultationid
	 * @param content
	 * @return
	public Result saveDirectConsultationComment(Long memberid, Long consultationid, String content);
	
	/**
	 * @description ：用户相互回复评论
	 * @Created by  : 朱军
	 * @version
	 * @Creation Date ： 2018/1/17 15:39
	 * @param memberid
	 * @param type			如果是给咨询评论者回复是0，给咨询评论者回复的人进行回复是1
	 * @param parentid		回复评论的id
	 * @param content		回复内容
	 * @return
	 */
	public Result saveReplyconsultationCommentService(Long memberid, Integer type, Long parentid, String content);
	
	/**
	 * @description ：查询咨询评论是否存在，判断删除字段
	 * @Created by  : gaoge
	 * @Creation Date ： 2018/1/16 17:03
	 * @version 1.0.0
	 * @param : commentid 咨询id
	 * @return :
	 */
	 public int findHasConsultationCommentById(Long commentid);
	 
	 /**
	  * 删除资讯评论
	  * @param commentid
	  * @param memberid
	  * @return
	  *//*
	 public Result deleteConsultationComment(Long commentid, Long memberid);
	 *//**
	  * 访谈评论列表，通过访谈id获取
	  * @param id     访谈id
	  * @param page   当前页
	  * @param size   当前页的数据
	  * @return
	  *//*
	 public Result getConsultationDetailInfo(Long id, int page, int size);*/
}
