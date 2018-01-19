package com.zc.main.entity.collectionconsultation;


import com.zc.common.core.orm.hibernate.IdEntity;
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
public class CollectionConsultation extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "member_id")
	private Member member;//谁收藏的
	@Column(name = "consultation_id")
	private Consultation consultation;//收藏的那个内容
	@Column(name = "consultation_member_id")
	private Member consultationMember;//收藏的内容所属的用户
	@Column(name = "type")
	private Integer type;//0收藏   1取消收藏
	
	
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	 

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

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
	
}
