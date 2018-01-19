package com.zc.main.entity.consultationattachment;

import com.zc.common.core.orm.hibernate.BaseIdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author
 * @package : com.zc.main.entity.consultationattachment
 * @progect : jianqiao-parent
 * @Description : 内容信息附件表
 * @Created by : 三只小金
 * @Creation Date ：2018年01月16日16:21
 */
@Alias("alq_consultation_attachment")
public class ConsultationAttachment extends BaseIdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 详情内容
     */
    @Column(name = "detail_content")
    private String detailContent;

    /**
     * 图片头
     */
    @Column(name = "header")
    private String header;

    /**
     * 关联的咨询信息
     */
    @Column(name = "consultation_id")
    private Long consultation;

    /**
     * 0文本  1分割线  2图片 3视频
     */
    @Column(name = "type")
    private Integer type;
    /**
     * 关联附件id
     */
    @Column(name = "attachment_id")
    private Long attachment;

    /**
     * 显示顺序的字段
     */
    @Column(name = "sort_num")
    private Integer sortNum;

    /**
     * 是否封面 1：是   2：视频的封面图
     */
    @Column(name = "cover")
    private Integer cover;

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

    public String getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Long getConsultation() {
        return consultation;
    }

    public void setConsultation(Long consultation) {
        this.consultation = consultation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAttachment() {
        return attachment;
    }

    public void setAttachment(Long attachment) {
        this.attachment = attachment;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getCover() {
        return cover;
    }

    public void setCover(Integer cover) {
        this.cover = cover;
    }
}
