
package com.zc.main.entity.memberhelpcase;


import com.zc.common.core.orm.hibernate.BaseIdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description:  用户找他求助病例附件表
 * @author:  ZhaoJunBiao
 * @date:  2018/1/18 9:51
 * @version: 1.0.0
 */
@Table(name = "alq_member_help_case")
@Alias("alq_member_help_case")
public class MemberHelpCase extends BaseIdEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//关联的信息
	@Column(name = "member_help_id")
	private Long  memberHelpId;
	//附件
	@Column(name = "attachment_id")
	private Long attachmentId;
	//会员id
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

