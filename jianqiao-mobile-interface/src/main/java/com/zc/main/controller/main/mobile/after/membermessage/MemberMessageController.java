package com.zc.main.controller.main.mobile.after.membermessage;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberhelp.MemberHelp;
import com.zc.main.service.member.MemberService;
import com.zc.main.service.membermessage.MemberMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @package : com.zc.main.controller.main.mobile.after.membermessage
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月17日17:48
 */
@Controller
@RequestMapping("mobile/after/membermessage")
public class MemberMessageController {

    @DubboConsumer(version = "1.0.0",timeout = 30000)
    private MemberMessageService messageService;
    @DubboConsumer(version = "1.0.0",timeout = 30000)
    private MemberService memberService;

    /**求助信息发布 需要标识type--来源  0是访谈 1是口述 2是求助 3是分享
     * @description:
     * @author:  ZhaoJunBiao
     * @date:  2018/1/17 17:50
     * @version: 1.0.0
     * @param member
     * @param memberhelp
     * @param caseId
     * @param imgId
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "save-member-help", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMemberHelp(@MemberAnno Member member,
                                 MemberHelp memberhelp, String caseId, String imgId, String helpPhone){
        memberhelp.setPhone(helpPhone);
        try {
            Result result = messageService.saveMemberHelp(member,memberhelp,caseId,imgId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.returnError("求助信息提交异常");
        }
    }

}
