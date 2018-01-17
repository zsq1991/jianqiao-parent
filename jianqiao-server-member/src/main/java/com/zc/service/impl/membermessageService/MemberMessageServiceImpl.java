package com.zc.service.impl.membermessageService;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberhelp.MemberHelp;
import com.zc.main.service.membermessage.MemberMessageService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @package : com.zc.service.impl.membermessageService
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月17日17:58
 */
@Service(version = "1.0.0")
@Component
@Transactional(readOnly = true)
public class MemberMessageServiceImpl implements MemberMessageService{

    @Override
    public Result saveMemberHelp(Member member, MemberHelp memberhelp, String caseId, String imgId) {
        return null;
    }
}
