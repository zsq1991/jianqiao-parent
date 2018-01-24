
package com.zc.main.entity.membermsg;


import com.zc.common.core.orm.hibernate.BaseIdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @description ：系统消息
 * @author by  : gaoge
 * @Creation Date ： 2018/1/19 15:05
 * @version 1.0.0
 */
@Table(name = "alq_member_msg")
@Alias("alq_member_msg")
public class MemberMsg extends BaseIdEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//被通知的用户
	@Column(name = "member_id")
	private Long  memberId;
	//通知类型  0高级用户审核通过  1认证驳回  2内容驳回 3内容通过4资讯评论通知5点赞通知
	@Column(name = "type")
	private Integer type;
	//0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
	@Column(name = "count_type")
	private Integer contentType;
	//原因备注
	@Column(name = "content")
	private String content;
	//关联的内容
	@Column(name = "consulatation_id")
	private Long consultationId;
	//0或者null未阅读，1阅读
	@Column(name = "read_type")
	private Integer readType;
	//关联的评论内容
	@Column(name = "consulatation_comment_id")
	private Long consultationCommentId;
	//点赞或者评论者的id
	@Column(name = "member_base_id")
	private Long memberBaseId;
	//消息通知的状态，0或者null是未通知，1是已通知
	@Column(name = "count_type")
	private Integer countType;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getConsultationId() {
		return consultationId;
	}

	public void setConsultationId(Long consultationId) {
		this.consultationId = consultationId;
	}

	public Integer getReadType() {
		return readType;
	}

	public void setReadType(Integer readType) {
		this.readType = readType;
	}

	public Long getConsultationCommentId() {
		return consultationCommentId;
	}

	public void setConsultationCommentId(Long consultationCommentId) {
		this.consultationCommentId = consultationCommentId;
	}

	public Long getMemberBaseId() {
		return memberBaseId;
	}

	public void setMemberBaseId(Long memberBaseId) {
		this.memberBaseId = memberBaseId;
	}

	public Integer getCountType() {
		return countType;
	}

	public void setCountType(Integer countType) {
		this.countType = countType;
	}
}

