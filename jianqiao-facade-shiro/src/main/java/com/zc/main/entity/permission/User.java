package com.zc.main.entity.permission;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zc.common.core.orm.hibernate.BaseIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * @描述：公共登录用户实体
 * @author system
 */
@Entity
@Table(name = "alq_permission_common_user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })

public class User  extends BaseIdEntity{
    private static final long serialVersionUID = -9056436515111782366L;


    /**
     * 用户名
     */
    @Column(name = "userName")
    private String userName;

    /**
     * 电话号码
     */
    @Column(name = "telphone")
    private String telphone;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 是否可用 1 可用；2 不可用
     */
    @Column(name = "isable")
    private Byte isable;

    /**
     * 登录时间
     */
    @Column(name = "loginTime")
    private Date loginTime;
    
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

	public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getIsable() {
        return isable;
    }

    public void setIsable(Byte isable) {
        this.isable = isable;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

}
