package com.zc.mybatis.dao;


import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.consultationcommentfabulous.ConsultationCommentFabulous;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface ConsultationCommentFabulousMapper extends BasicMapper<ConsultationCommentFabulous> {

    /**
     * 咨询点赞，查询咨询评论是否被当前登录用户点赞
     *
     * @param consultationCommentId
     * @param memberid
     * @return
     */
    public ConsultationCommentFabulous getConsultationCommentFabulousByCommentIdAndMemberId(@Param("consultationcommentid") Long consultationCommentId,
                                                                                            @Param("memberid") Long memberid);
}
