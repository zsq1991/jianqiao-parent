package com.zc.main.entity.attachment;

import com.zc.common.core.orm.hibernate.BaseIdEntity;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @version 1.0.0
 * @description ：系统附件
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/16 9:39
 */
@Table(name = "alq_attachment")
@Alias("alq_attachment")
public class Attachment extends BaseIdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //附件名称
    @Column(name = "name")
    @NotBlank(message = "附件名称不能为空")
    private String name;

    //附件地址
    @Column(name = "address")
    @NotBlank(message = "附件地址不能为空")
    private String address;

    //记录文件大小；
    @Column(name = "memory")
    private Double memory;

    //尺寸信息
    @Column(name = "size_info")
    private String sizeInfo;

    //备注
    @Column(name = "comment_info")
    private String commentInfo;


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

    public Double getMemory() {
        return memory;
    }

    public void setMemory(Double memory) {
        this.memory = memory;
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


}
