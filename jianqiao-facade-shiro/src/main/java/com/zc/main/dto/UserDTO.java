package com.zc.main.dto;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-permission
 * @描述：用户列表查询dto
 * @author ： Mr.Shu
 * @创建时间：2017-05-12
 * @Copyright @2017 by Mr.Shu
 */
public class UserDTO implements Serializable{

    private static final long serialVersionUID = -7565212674308307997L;
    
    /**
     * 用户id
     */
    private Long id ;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String telphone;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String msgCode;

    /**
     * 用户状态
     */
    private Integer isable;

    /**
     * 创建人
     */
    private Long createrId;

    /**
     * 修改人
     */
    private Long updaterId;


    /**
	 * @return the telphone
	 */
	public String getTelphone() {
		return telphone;
	}

	/**
	 * @param telphone the telphone to set
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public Integer getIsable() {
        return isable;
    }

    public void setIsable(Integer isable) {
        this.isable = isable;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        msgCode = msgCode;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", telphone='" + telphone + '\'' +
                ", password='" + password + '\'' +
                ", msgCode='" + msgCode + '\'' +
                ", isable=" + isable +
                ", createrId=" + createrId +
                ", updaterId=" + updaterId +
                '}';
    }
}
