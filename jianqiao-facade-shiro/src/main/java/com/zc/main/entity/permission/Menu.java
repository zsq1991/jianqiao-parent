package com.zc.main.entity.permission;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zc.common.core.orm.hibernate.BaseIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @描述： 菜单实体表
 *
 * @author system
 */
@Entity
@Table(name = "alq_permission_menu")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })

public class Menu  extends BaseIdEntity{

    private static final long serialVersionUID = 3409139139798172232L;

    /**
     * 菜单名称
     */
    @Column(name = "menuName")
    private String menuName;

    /**
     * 菜单地址
     */
    @Column(name = "menuUrl")
    private String menuUrl;

    /**
     * 菜单父id
     */
    @Column(name = "parentID")
    private Long parentID;

    /**
     * 菜单顺序
     */
    @Column(name = "sequence")
    private Integer sequence;

    /**
     * 菜单图标
     */
    @Column(name = "icon")
    private String icon;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
