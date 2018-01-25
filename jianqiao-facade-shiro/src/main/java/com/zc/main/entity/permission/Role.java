package com.zc.main.entity.permission;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zc.common.core.orm.hibernate.BaseIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @描述： 角色实体类
 *
 * @author system
 */
@Entity
@Table(name = "alq_permission_role")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })

public class Role  extends BaseIdEntity{

    private static final long serialVersionUID = 7375175379375246073L;

    /**
     * 角色名称
     */
    @Column(name = "roleName")
    private String roleName;


    /**
     * 角色编号
     */
    @Column(name = "roleCode")
    private Byte roleCode;


    /**
     * 角色状态 1 ：启用 2：停用
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 角色描述
     */
    @Column(name = "description")
    private String description;

    @Column(name = "createrId")
    private long createrId;
    
    @Column(name = "updaterId")
    private long updaterId;
    
    
   
	public long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}

	public long getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(long updaterId) {
		this.updaterId = updaterId;
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

}
