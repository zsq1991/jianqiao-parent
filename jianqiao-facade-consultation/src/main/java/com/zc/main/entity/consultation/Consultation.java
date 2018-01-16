package com.zc.main.entity.consultation;


import com.zc.common.core.orm.hibernate.IdEntity;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @description ：咨询信息
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/16 10:53
 * @version 1.0.0
 */
public class Consultation extends IdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "type")
    private Integer type;//0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享

    @Column(name = "model_type")
    private Integer modelType;//模式类型  0无图  1单图 2三图

    @Column(name = "topic_type")
    private Integer topicType;//访谈内容类型  1：图文 2：视频

    @Column(name = "title")
    private String title;//标题

    @Column(name = "member_id")
    private Long memberId;//内容关联的会员

    @Column(name = "status")
    private Integer status;//0未发布  1审核中 2已发布 3驳回

    @Column(name = "audit_user_name")
    private String auditUserName;//审核人员名称

    @Column(name = "num")
    private Integer num;//求助的回答总个数   主题下面的内容总个数

    @Column(name = "comment_num")
    private Long commentNum;//评论的总个数

    @Column(name = "fabulous_num")
    private Long fabulousNum;//赞总个数

    @Column(name = "rejection_reason")
    private String rejectionReason;//最新的被拒绝原因

    @Column(name = "author_info")
    private String authorInfo;//作者信息  猎头名字或者行业协会名字

    @Column(name = "detail_summary")
    private String detailSummary;//摘要信息

    @Column(name = "is_delete")
    private Integer isDelete;//0或null未删，1删除

    @Column(name = "share_num")
    private Long shareNum;//被分享的次数

    @Column(name = "order_date")
    private Date orderDate;//排序时间（已发布则维护审核通过时间   未发布则是更新时间  驳回是驳回时间  审核中提交审核时间 ）

    @Column(name = "consulatation_id")
    private Long consultationId;//口述主题、访谈主题、求助需要被他 的内容以及回答关联

    @Column(name = "collect_num")
    private Long collectNum;//被收藏的次数

    @Column(name = "top")
    private Integer top;//1是置顶，0或者null不是置顶

    @Column(name = "result")
    private Long result;//0或者为null为正式，1是测试


    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }


    public Long getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Long collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Long getShareNum() {
        return shareNum;
    }

    public void setShareNum(Long shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getDetailSummary() {
        return detailSummary;
    }

    public void setDetailSummary(String detailSummary) {
        this.detailSummary = detailSummary;
    }

    public String getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(String authorInfo) {
        this.authorInfo = authorInfo;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
    }

    public Long getFabulousNum() {
        return fabulousNum;
    }

    public void setFabulousNum(Long fabulousNum) {
        this.fabulousNum = fabulousNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Integer getModelType() {
        return modelType;
    }

    public void setModelType(Integer modelType) {
        this.modelType = modelType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }
}
