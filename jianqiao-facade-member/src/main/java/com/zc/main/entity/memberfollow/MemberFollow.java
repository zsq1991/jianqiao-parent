package com.zc.main.entity.memberfollow;

import com.zc.common.core.orm.hibernate.IdEntity;
import com.zc.main.entity.member.Member;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author : wangxueyang[wxueyanghj@163.com]
 * @version ： 1.0.0
 * @package : com.zc.main.entity.memberfollow
 * @progect : jianqiao-parent
 * @Description : 用户关注记录
 * @Creation Date ：2018年01月17日9:47
 */
@Alias("alq_member_follow")
public class MemberFollow extends IdEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 关注的用户id
     */
    @Column(name = "member_following_id")
    private Long memberFollowingId;

    /**
     * 被关注的会员id
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 0或者null为关注，1是取消关注
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    public Long getMemberFollowingId() {
        return memberFollowingId;
    }

    public void setMemberFollowingId(Long memberFollowingId) {
        this.memberFollowingId = memberFollowingId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
