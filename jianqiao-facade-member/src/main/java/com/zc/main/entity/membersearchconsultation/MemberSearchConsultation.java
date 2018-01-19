package com.zc.main.entity.membersearchconsultation;

import com.zc.common.core.orm.hibernate.BaseIdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;

/**
 * @version ： 1.0.0
 * @package : com.zc.main.entity.membersearchconsultation
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : wangxueyang[wxueyanghj@163.com]
 * @Creation Date ：2018年01月17日17:27
 */
@Alias("alq_member_search_consultation")
public class MemberSearchConsultation extends BaseIdEntity {

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "member_id")
    private Long memberId;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}