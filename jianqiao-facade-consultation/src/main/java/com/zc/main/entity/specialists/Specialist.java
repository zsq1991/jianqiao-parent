package com.zc.main.entity.specialists;

import com.zc.common.core.orm.hibernate.BaseIdEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @package : com.alqsoft.entity.specialist
 * @className : Specialist
 * @description ：专家实体类
 * @Created by  : 朱军
 * @Creation Date ： 2018/1/9 10:23
 * @version
 */
public class Specialist extends BaseIdEntity implements Serializable{

	private String headUrl;//头像
	private String phone;//专家账号
	private String name;//专家姓名
	private Integer sex;//性别
	private String office;//科室
	private String position;//职位
	private String employer;//单位
	private String detail;//详情
	private Integer isDelete;//0:正常 1:删除

	/**
	 * 用于返回数据
	 */
	private String title;//咨询标题

	private List<SpecialistDTO> addressList;

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<SpecialistDTO> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<SpecialistDTO> addressList) {
		this.addressList = addressList;
	}
}
