package com.zc.main.entity.permission;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zc.common.core.orm.hibernate.BaseIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @描述： 用户角色实体
 * @author system
 */
@Entity
@Table(name = "alq_permission_user_role")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class UserRole extends BaseIdEntity{

    private static final long serialVersionUID = -6782858999602872500L;
    /**
     * 角色id
     */
    @Column(name = "roleId")
    private Long roleId;

    /**
     * 用户id
     */
    @Column(name = "userId")
    private Long userId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
