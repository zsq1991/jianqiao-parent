package com.zc.main.vo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @description 菜单树VO
 * @author system
 * @date 2018-01-25 17:39
 * @version 1.0.0
 */
@Alias("MenuTreeVO")
public class MenuTreeVO implements Serializable {

	private static final long serialVersionUID = -2240177336781489378L;

	private Long id;
	private Long pId;
	private String name;
    private Long btnId;
    private Boolean checked = false;
	private Boolean open = true;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Long getBtnId() {
        return btnId;
    }

    public void setBtnId(Long btnId) {
        this.btnId = btnId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return "MenuTreeVO{" +
				"id=" + id +
				", pId=" + pId +
				", name='" + name + '\'' +
				", btnId=" + btnId +
				", checked=" + checked +
				", open=" + open +
				'}';
	}
}
