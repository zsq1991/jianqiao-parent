package com.zc.main.entity.consultationfabulous;


import com.zc.common.core.orm.hibernate.BaseIdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 内容 点赞、取消赞
 * @author sunhuijie
 *
 * @date 2017年3月1日
 *
 */
@Table(name = "alq_consultation_fabulous")
@Alias("alq_consultation_fabulous")
public class ConsultationFabulous extends BaseIdEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	//点赞 1   取消赞2
	@Column(name = "type")
	private Integer type;
	//赞的用户
	@Column(name = "member_id")
	private Long memberId;
	//关联的内容
	@Column(name = "consultation_id")
	private Long consultationId;

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

	public Long getConsultationId() {
		return consultationId;
	}

	public void setConsultationId(Long consultationId) {
		this.consultationId = consultationId;
	}
}
