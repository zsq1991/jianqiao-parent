package com.zc.main.entity.consultationcommentfabulous;

import com.zc.common.core.orm.hibernate.BaseIdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @version 1.0.0
 * @description ：咨询评论 回复 点赞、取消赞
 * @author  by  : gaoge
 * @Creation Date ： 2018/1/17 9:44
 */
@Table(name = "alq_consultation_comment_fabulous")
@Alias("alq_consultation_comment_fabulous")
@MappedSuperclass
public class ConsultationCommentFabulous extends BaseIdEntity implements Serializable {

    //点赞 1   取消赞2
    @Column(name = "type")
    private Integer type;
    //赞的用户
    @Column(name = "member_id")
    private Long memberId;
    //关联的评论
    @Column(name = "consultation_comment_id")
    private Long consultationCommentId;

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
