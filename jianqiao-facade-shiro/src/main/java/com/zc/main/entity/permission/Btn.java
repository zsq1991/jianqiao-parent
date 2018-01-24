package com.zc.main.entity.permission;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zc.common.core.orm.hibernate.BaseIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @描述： 按钮实体表
 * @author system
 */
@Entity
@Table(name = "alq_permission_btn")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Btn  extends BaseIdEntity{

    private static final long serialVersionUID = 3409139139798172232L;

    /**
     * 按钮名称
     */
    @Column(name = "btnName")
    private String btnName;

    /**
     * 按钮地址
     */
    @Column(name = "btnUrl")
    private String btnUrl;

    /**
     * 按钮code
     */
    @Column(name = "btnCode")
    private String btnCode;

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getBtnUrl() {
        return btnUrl;
    }

    public void setBtnUrl(String btnUrl) {
        this.btnUrl = btnUrl;
    }

    public String getBtnCode() {
        return btnCode;
    }

    public void setBtnCode(String btnCode) {
        this.btnCode = btnCode;
    }
}
