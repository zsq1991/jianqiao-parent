package com.zc.main.entity.memberattachment;

import com.zc.common.core.orm.hibernate.IdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;

/**
 * @author
 * @package : com.zc.main.entity.memberattachment
 * @progect : jianqiao-parent
 * @Description : 用户实名认证的附件表
 * @Created by : 三只小金
 * @Creation Date ：2018年01月19日17:05
 */
@Alias("alq_member_attachment")
public class MemberAttachment extends IdEntity {

    /**
     * 附件名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 附件地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 图片头
     */
    @Column(name = "header")
    private String header;

    /**
     * 关联的用户信息
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 关联图片信息
      */
    @Column(name = "attachment_id")
    private Long attachmentId;

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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }
}
