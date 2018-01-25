package com.zc.mybatis.dao;


import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.consultationcommentfabulous.ConsultationCommentFabulous;
import org.apache.ibatis.annotations.Param;

/**
 * @description 咨询点赞
 * @author gaoge
 * @date 2018/1/22 16:08
 * @version 1.0.0
 */
@MyBatisRepository
public interface ConsultationCommentFabulousMapper extends BasicMapper<ConsultationCommentFabulous> {

    /**
     * 查询咨询评论是否被当前登录用户点赞
     * @description 查询咨询评论是否被当前登录用户点赞
     * @author gaoge
     * @date 2018-01-25 16:06
     * @version 1.0.0
     * @param consultationCommentId
     * @param memberid
     * @return
     */
    public ConsultationCommentFabulous getConsultationCommentFabulousByCommentIdAndMemberId(@Param("consultationcommentid") Long consultationCommentId,
                                                                                            @Param("memberid") Long memberid);
}
