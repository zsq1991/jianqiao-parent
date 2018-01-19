package com.zc.main.entity.collectionconsultation;


import com.zc.common.core.orm.hibernate.BaseIdEntity;
import com.zc.main.entity.consultation.Consultation;
import com.zc.main.entity.member.Member;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;

/**
 * @description ：收藏内容记录
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/19 15:04
 * @version 1.0.0
 */
@Alias("alq_collection_consultation")
public class CollectionConsultation extends BaseIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "member_id")
	private Long memberId;//谁收藏的
	@Column(name = "consultation_id")
	private Long consultationId;//收藏的那个内容
	@Column(name = "consultation_member_id")
	private Long consultationMemberId;//收藏的内容所属的用户
	@Column(name = "type")
	private Integer type;//0收藏   1取消收藏

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
