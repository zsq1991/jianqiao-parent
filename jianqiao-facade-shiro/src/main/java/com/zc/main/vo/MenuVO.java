package com.zc.main.vo;


import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;


@Alias("MenuVO")
public class MenuVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2267431338902973275L;

	/**
	 * id
	 */
	private Long id;

    /**
     * 按钮专用Id
     */
    private Long btnId;

	/**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单地址
     */
    private String menuUrl;
    /**
     * 菜单父id
     */
    private Long parentId;

	/**
	 * 是否选择 1：选择 2：未选择(默认)
	 */
	private Integer isChecked = 2;

	/**
	 * 顺序
	 */
	private Integer sequence;

    /**
	 * 子菜单
     */
    private List<MenuVO> child;

    /**
     * 菜单图标
     */
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public List<MenuVO> getChild() {
        return child;
	}

    public void setChild(List<MenuVO> child) {
        this.child = child;
	}

	public Integer getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

	public Integer getSequence() {
		return sequence;
	}

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getBtnId() {
        return btnId;
    }

    public void setBtnId(Long btnId) {
        this.btnId = btnId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

	@Override
	public String toString() {
		return "MenuVO{" +
				"id=" + id +
				", btnId=" + btnId +
				", menuName='" + menuName + '\'' +
				", menuUrl='" + menuUrl + '\'' +
				", parentId=" + parentId +
				", isChecked=" + isChecked +
				", sequence=" + sequence +
				", child=" + child +
				", icon='" + icon + '\'' +
				'}';
	}
}
