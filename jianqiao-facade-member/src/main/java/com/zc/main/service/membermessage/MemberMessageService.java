package com.zc.main.service.membermessage;

import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberhelp.MemberHelp;

import java.util.Map;

/**
 * @package : com.zc.main.service.membermessage
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月17日17:57
 */
public interface MemberMessageService {

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
}
