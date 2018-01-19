
package com.zc.main.entity.memberhelp;


import com.zc.common.core.orm.hibernate.BaseIdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author sunhuijie
 * 用户找他求助表
 * @date 2017年6月12日
 *
 */
@Alias("alq_member_help")
public class MemberHelp extends BaseIdEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "phone")
	private String phone;//手机号

	@Column(name = "address")
	private String address;//地址

	@Column(name = "name")
	private String name;//真实姓名

	@Column(name = "content")
	private String content;//病情简介

	@Column(name = "member_id")
	private Long  memberId;//

	@Column(name = "type")
	private Integer type;//来源  0是访谈 1是口述 2是求助 3是分享

	@Column(name = "for_id")
	private Long forId;//对应type的编号

	@Column(name = "is_delete")
	private Integer isDelete;//0或null未禁用，1禁用

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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

	public Long getForId() {
		return forId;
	}

	public void setForId(Long forId) {
		this.forId = forId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}

