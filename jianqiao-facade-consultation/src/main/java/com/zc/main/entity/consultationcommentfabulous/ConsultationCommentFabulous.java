package com.zc.main.entity.consultationcommentfabulous;

import com.zc.common.core.orm.hibernate.IdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @version 1.0.0
 * @description ：咨询评论 回复 点赞、取消赞
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/17 9:44
 */
@Alias("alq_consultation_comment_fabulous")
public class ConsultationCommentFabulous extends IdEntity implements Serializable {


    @Column(name = "type")
    private Integer type;//点赞 1   取消赞2

    @Column(name = "member_id")
    private Long memberId;//赞的用户

    @Column(name = "consultation_comment_id")
    private Long consultationCommentId;//关联的评论

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getConsultationCommentId() {
        return consultationCommentId;
    }

    public void setConsultationCommentId(Long consultationCommentId) {
        this.consultationCommentId = consultationCommentId;
    }
}
