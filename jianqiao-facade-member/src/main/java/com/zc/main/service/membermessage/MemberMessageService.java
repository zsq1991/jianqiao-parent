package com.zc.main.service.membermessage;

import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberhelp.MemberHelp;

/**
 * @package : com.zc.main.service.membermessage
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月17日17:57
 */
public interface MemberMessageService {

    Result saveMemberHelp(Member member, MemberHelp memberhelp, String caseId, String imgId);
}
