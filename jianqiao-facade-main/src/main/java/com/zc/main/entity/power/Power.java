package com.zc.main.entity.power;


import com.zc.common.core.orm.hibernate.BaseIdEntity;
import com.zc.main.entity.consultation.Consultation;
import com.zc.main.entity.member.Member;

import java.io.Serializable;

/**
 * 权重值详情记录
 *@entity
 */
public class Power extends BaseIdEntity implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//咨询类型
	private Consultation consultation;
	//操作用户
	private Member member;
	//类型1-赞 2-收藏 3-阅读 4-分享 5-评论  6-置顶
	private Integer type;
	//当前操作类型0-增加 1-减少
	private Integer status;
	public Consultation getConsultation() {
		return consultation;
	}
	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
