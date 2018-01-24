package com.zc.main.entity.collectionconsultation;


import com.zc.common.core.orm.hibernate.BaseIdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @description ：收藏内容记录
 * @author by  : gaoge
 * @Creation Date ： 2018/1/19 15:04
 * @version 1.0.0
 */
@Table(name = "alq_collection_consultation")
@Alias("alq_collection_consultation")
public class CollectionConsultation extends BaseIdEntity {

	private static final long serialVersionUID = 1L;
	//谁收藏的
	@Column(name = "member_id")
	private Long memberId;
	//收藏的那个内容
	@Column(name = "consultation_id")
	private Long consultationId;
	//收藏的内容所属的用户
	@Column(name = "consultation_member_id")
	private Long consultationMemberId;
	//0收藏   1取消收藏
	@Column(name = "type")
	private Integer type;

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
