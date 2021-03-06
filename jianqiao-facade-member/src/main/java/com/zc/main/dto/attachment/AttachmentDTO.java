package com.zc.main.dto.attachment;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 系统附件dto
 * @author:  ZhaoJunBiao
 * @date:  2018/1/18 11:05
 * @version: 1.0.0

 */
public class AttachmentDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 建立的用户
     */
    private String createUser;
    /**
     * 建立的时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 建立的ip
     */
    private String createdIp ;
    /**
     * 附件名称
     */
    private String name;
    /**
     * 附件地址
     */
    private String address;
    /**
     * 记录文件大小；
     */
    private Double memory;
    /**
     * 尺寸信息
     */
    private String sizeInfo;
    /**
     * 备注
     */
    private String commentInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getMemory() {
        return memory;
    }

    public void setMemory(Double memory) {
        this.memory = memory;
    }

    public String getSizeInfo() {
        return sizeInfo;
    }

    public void setSizeInfo(String sizeInfo) {
        this.sizeInfo = sizeInfo;
    }

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
                "id=" + id +
                ", createUser='" + createUser + '\'' +
                ", createdTime=" + createdTime +
                ", updateTime=" + updateTime +
                ", createdIp='" + createdIp + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", memory=" + memory +
                ", sizeInfo='" + sizeInfo + '\'' +
                ", commentInfo='" + commentInfo + '\'' +
                '}';
    }
}
