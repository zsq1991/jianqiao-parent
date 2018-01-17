
package com.zc.main.entity.consultationcomment;

import com.zc.common.core.orm.hibernate.IdEntity;
import com.zc.main.entity.consultation.Consultation;
import com.zc.main.entity.member.Member;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;

/**
 * @package : com.zc.main.entity.consultationcomment
 * @className : ConsultationComment
 * @description ：内容评论 回复记录表
 * @Created by  : 朱军
 * @Creation Date ： 2018/1/17 16:02
 * @version
 */
@Alias("alq_consultation_comment")
public class ConsultationComment extends IdEntity{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "consultation_id")
	private Consultation consultation;//评论的咨询

	@Column(name = "consultation_member_id")
	private Member consultationMember;//咨询关联的会员

	@Column(name = "member_id")
	private Member member;//那个会员评论的、回复的

	@Column(name = "content")
	private String content;//评论的内容

	@Column(name = "comment_info_id")
	private ConsultationComment commentInfo;//顶级评论  如果为评论的话  则可以为空,空为顶级

	@Column(name = "parent_id")
	private ConsultationComment parent;//父级的回复 评论

	@Column(name = "reply_num")
	private Long replyNum;//回复数量

	@Column(name = "fabulous_num")
	private Long fabulousNum;//赞的数量

	@Column(name = "is_delete")
	private Integer isDelete;//0或null未删，1删除

	@Column(name = "first_reply_comment")
	private ConsultationComment firstReplyComment;//这条评论回复最早的评论

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public Member getConsultationMember() {
		return consultationMember;
	}

	public void setConsultationMember(Member consultationMember) {
		this.consultationMember = consultationMember;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ConsultationComment getCommentInfo() {
		return commentInfo;
	}

	public void setCommentInfo(ConsultationComment commentInfo) {
		this.commentInfo = commentInfo;
	}

	public ConsultationComment getParent() {
		return parent;
	}

	public void setParent(ConsultationComment parent) {
		this.parent = parent;
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

	public ConsultationComment getFirstReplyComment() {
		return firstReplyComment;
	}

	public void setFirstReplyComment(ConsultationComment firstReplyComment) {
		this.firstReplyComment = firstReplyComment;
	}
}

