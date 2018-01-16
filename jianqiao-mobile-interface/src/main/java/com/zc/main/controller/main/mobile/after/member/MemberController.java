package com.zc.main.controller.main.mobile.after.member;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Reference;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证
 * @author xwolf
 * @date 2017-06-19 9:33
 * @since 1.8
 */
@RestController
@RequestMapping("mobile/after/member")
public class MemberController {

    @DubboConsumer(version="1.0.0")
    private MemberService memberService;


    /**
     *  获取用户认证信息
     * @param member
     * @return
     */
    @RequestMapping(value = "getAuth",method = RequestMethod.POST)
    public Result getMemberAuth(Member member){

        return memberService.getAuthMember(member);
    }
}
