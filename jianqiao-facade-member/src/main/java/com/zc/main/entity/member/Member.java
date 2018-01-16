
package com.zc.main.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zc.common.core.orm.hibernate.IdEntity;
import com.zc.main.entity.attachment.Attachment;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sunhuijie 用户表
 * @date 2017年6月12日
 */
@Alias("alq_member")
public class Member extends IdEntity implements Serializable {


    private static final long serialVersionUID = 1L;


    @Column(name = "phone")
    private String phone;// 手机号

    @Column(name = "password")
    private String password;// 密码

    @Column(name = "logo_attachment_id")
    private Long logoAttachmentId;// 上传头像

    @Column(name = "nickname")
    private String nickname;// 昵称

    @Column(name = "consulation_num")
    private Long consulationNum;// 收藏内容数量

    @Column(name = "focus_num")
    private Long focusNum;// 关注的用户数量

    @Column(name = "following_num")
    private Long followingNum;// 粉丝数量

    @Column(name = "uuid")
    private String uuid;//

    @Column(name = "name")
    private String name;// 真实姓名

    @Column(name = "card")
    private String card;// 身份证号

    @Column(name = "user_type")
    private Integer userType;// 0普通 1认证后用户可以发布访谈 口述 2:认证中

    @Column(name = "label_num")
    private Integer labelNum;// 用户拥有的标签个数

    @Column(name = "is_delete")
    private Integer isDelete;// 0或null未禁用，1禁用

    @Column(name = "sex")
    private Integer sex;// 性别

    @Column(name = "audit_user_name")
    private String auditUserName;// 审核人员名称

    @Column(name = "status")
    private Integer status;//0 未申请  1审核中 2已发布 3驳回

    @Column(name = "audittime")
    private Date audittime;//认证审核的时间

    //阅读咨询积分
    @Column(name = "read_num")
    private Integer readNum;
    //发布咨询积分
    @Column(name = "release_num")
    private Integer releaseNum;
    //邀请好友积分
    @Column(name = "invite_num")
    private Integer inviteNum;
    //分享咨询积分
    @Column(name = "share_num")
    private Integer shareNum;
    //认证积分
    @Column(name = "authentication_num")
    private Integer authenticationNum;
    //总积分
    @Column(name = "integral_num")
    private Integer integralNum;

    public Long getLogoAttachmentId() {
        return logoAttachmentId;
    }

    public void setLogoAttachmentId(Long logoAttachmentId) {
        this.logoAttachmentId = logoAttachmentId;
    }

    public Integer getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(Integer integralNum) {
        this.integralNum = integralNum;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Integer getReleaseNum() {
        return releaseNum;
    }

    public void setReleaseNum(Integer releaseNum) {
        this.releaseNum = releaseNum;
    }

    public Integer getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(Integer inviteNum) {
        this.inviteNum = inviteNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getAuthenticationNum() {
        return authenticationNum;
    }

    public void setAuthenticationNum(Integer authenticationNum) {
        this.authenticationNum = authenticationNum;
    }

    public Date getAudittime() {
        return audittime;
    }

    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getConsulationNum() {
        return consulationNum;
    }

    public void setConsulationNum(Long consulationNum) {
        this.consulationNum = consulationNum;
    }

    public Long getFocusNum() {
        return focusNum;
    }

    public void setFocusNum(Long focusNum) {
        this.focusNum = focusNum;
    }

    public Long getFollowingNum() {
        return followingNum;
    }

    public void setFollowingNum(Long followingNum) {
        this.followingNum = followingNum;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getLabelNum() {
        return labelNum;
    }

    public void setLabelNum(Integer labelNum) {
        this.labelNum = labelNum;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }


}
