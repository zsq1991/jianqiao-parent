package com.zc.main.entity.permission;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zc.common.core.orm.hibernate.IdEntity;

/**
 * @描述： 角色菜单实体
 */
@Entity
@Table(name = "alq_permission_role_menu")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class RoleMenu extends IdEntity{

    private static final long serialVersionUID = 8355710622359264368L;

    /**
     * 角色id
     */
    @Column(name = "roleId")
    private Long roleId;

    /**
     * 菜单id
     */
    @Column(name = "menuId")
    private Long menuId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}
