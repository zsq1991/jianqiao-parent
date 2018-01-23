
package com.zc.main.entity.memberhelpimage;


import com.zc.common.core.orm.hibernate.BaseIdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description: 用户找他求助影像附件表
 * @author:  ZhaoJunBiao
 * @date:  2018/1/18 10:11
 * @version: 1.0.0
 */
@Table(name = "alq_member_help_image")
@Alias("alq_member_help_image")
public class MemberHelpImage extends BaseIdEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//关联的信息
	@Column(name = "member_help_id")
	private Long  memberHelpId;
	//附件
	@Column(name = "attachment_id")
	private Long attachmentId;
	//用户ID
	@Column(name = "member_id")
	private Long  memberId;

	public Long getMemberHelpId() {
		return memberHelpId;
	}

	public void setMemberHelpId(Long memberHelpId) {
		this.memberHelpId = memberHelpId;
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}

