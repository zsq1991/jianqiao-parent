package com.zc.main.vo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @description 角色VO
 * @author system
 * @date 2018-01-25 17:47
 * @version 1.0.0
 */
@Alias("RoleVO")
public class RoleVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5645594700043186105L;

	private Long id;
	  /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编号
     */
    private Byte roleCode;

	/**
	 * 角色描述
	 */
	private String description;

    /**
     * 角色状态
     */
    private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Byte getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(Byte roleCode) {
		this.roleCode = roleCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "RoleVO{" +
				"id=" + id +
				", roleName='" + roleName + '\'' +
				", roleCode=" + roleCode +
				", description='" + description + '\'' +
				", status=" + status +
				'}';
	}
}
