package com.zc.main.service.membermessage;

import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberhelp.MemberHelp;

import java.util.Map;

/**
 * @author
 * @package : com.zc.main.service.membermessage
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月17日17:57
 */
public interface MemberMessageService {

    /**
     * 求助发布
     * @author
     * @data 2018/1/18
     * @param member
     * @param memberhelp
     * @param caseId
     * @param imgId
     * @return
     */
    Result saveMemberHelp(Member member, MemberHelp memberhelp, String caseId, String imgId);

    /**
     * 查询啊驳回原因
     * @author huangxin
     * @data 2018/1/18 16:29
     * @Description:
     * @Version: 3.2.0
     * @param conid 资讯id
     * @return
     */
    Map<String,Object> getContentById(Long conid);

    /**
     * 修改头像
     * @author zhaoshuaiqi
     * @data 2018/1/18
     * @param attachmentId  头像对应的id
     * @param member 用户
     * @return
     */
    Result updateMemberLogo(Long attachmentId, Member member);

    /**
     * 修改昵称
     * @author zhaoshuaiqi
     * @data 2018/1/18
     * @param nickname 昵称
     * @param member 用户
     * @return
     */
    Result updateMemberNickname(String nickname, Member member);

    /**
     * 修改性别
     * @author zhaoshuaiqi
     * @data 2018/1/18
     * @param sexId 性别对应的值
     * @param member 用户
     * @return
     */
    Result updateMembersex(Integer sexId, Member member);

    /**
     * 用户个人信息获取
     * @author zhaoshuaiqi
     * @data 2018/1/18
     * @param member
     * @return
     */
    Result memberMessageList(Member member);
}
