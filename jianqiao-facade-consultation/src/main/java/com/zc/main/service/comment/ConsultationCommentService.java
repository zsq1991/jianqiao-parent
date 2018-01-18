package com.zc.main.service.comment;


import com.zc.common.core.result.Result;

import java.util.List;
import java.util.Map;

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

	/**
	 * 评论列表,getCommentList获取到的时顶级评论，firt_reply_comment是第一评论的id
	 * @author huangxin
	 * @data 2018/1/18 16:14
	 * @Description: 评论列表,getCommentList获取到的时顶级评论，firt_reply_comment是第一评论的id
	 * @Version: 3.2.0
	 * @param paramMap
	 * @return
	 */
	List<Map<String,Object>> getCommentList(Map<String, Object> paramMap);

	/**
	 * 通过父类id获取子的2个id
	 * @author huangxin
	 * @data 2018/1/18 16:16
	 * @Description: 通过父类id获取子的2个id
	 * @Version: 3.2.0
	 * @param id 资讯id
	 * @return
	 */
	public List<Map> getCommentSonIdByPid(Long id);

	/**
	 * 查询评论
	 * @author huangxin
	 * @data 2018/1/18 16:18
	 * @Description: 查询评论
	 * @Version: 3.2.0
	 * @param id 资讯id
	 * @return
	 */
	Map<String,Object> getSonCommentList(Long id);
}
