package com.zc.main.entity.permission;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zc.common.core.orm.hibernate.IdEntity;

/**
 * @描述： 菜单按钮实体
 */
@Entity
@Table(name = "alq_permission_role_menu")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class MenuBtn extends IdEntity {

    private static final long serialVersionUID = 8355710622359264368L;

    /**
     * 菜单id
     */
    @Column(name = "menuId")
    private Long menuId;

    /**
     * 按钮id
     */
    @Column(name = "btnId")
    private Long btnId;

    /**
     * 角色id
     */
    @Column(name = "roleId")
    private Long roleId;

    /**
     * 排序
     */
    @Column(name = "sequence")
    private Integer sequence;


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getBtnId() {
        return btnId;
    }

    public void setBtnId(Long btnId) {
        this.btnId = btnId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
