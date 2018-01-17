
package com.zc.main.entity.consultationcomment;

import com.zc.common.core.orm.hibernate.IdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author sunhuijie
 *         内容评论 回复记录表
 * @date 2017年5月9日
 */
@Alias("alq_consultation_comment")
public class ConsultationComment extends IdEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Column(name = "consultation_id")
    private Long consultationId;//评论的咨询

    @Column(name = "consultation_member_id")
    private Long consultationMemberId;//咨询关联的会员

    @Column(name = "member_id")
    private Long memberId;//那个会员评论的、回复的

    @Column(name = "content")
    private String content;//评论的内容

    @Column(name = "comment_info_id")
    private Long commentInfoId;//顶级评论  如果为评论的话  则可以为空,空为顶级

    @Column(name = "parent_id")
    private Long parentId;//父级的回复 评论

    @Column(name = "reply_num")
    private Long replyNum;//回复数量

    @Column(name = "fabulous_num")
    private Long fabulousNum;//赞的数量

    @Column(name = "is_delete")
    private Integer isDelete;//0或null未删，1删除

    @Column(name = "first_reply_comment")
    private Long firstReplyCommentId;//这条评论回复最早的评论

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public Long getConsultationMemberId() {
        return consultationMemberId;
    }

    public void setConsultationMemberId(Long consultationMemberId) {
        this.consultationMemberId = consultationMemberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCommentInfoId() {
        return commentInfoId;
    }

    public void setCommentInfoId(Long commentInfoId) {
        this.commentInfoId = commentInfoId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Long replyNum) {
        this.replyNum = replyNum;
    }

    public Long getFabulousNum() {
        return fabulousNum;
    }

    public void setFabulousNum(Long fabulousNum) {
        this.fabulousNum = fabulousNum;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Long getFirstReplyCommentId() {
        return firstReplyCommentId;
    }

    public void setFirstReplyCommentId(Long firstReplyCommentId) {
        this.firstReplyCommentId = firstReplyCommentId;
    }
}
