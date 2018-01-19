package com.zc.main.controller.main.mobile.after.member;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.member.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证
 * @author 王鑫涛
 * @date 2017-06-19 9:33
 * @since 1.8
 */
@RestController
@RequestMapping("mobile/after/member")
public class MemberController {

    @DubboConsumer(version = "1.0.0",timeout = 300000)
    private MemberService memberService;


    /**
     *
     *  @description方法说明 获取用户认证信息
     * @author 王鑫涛
     * @date  16:16  2018/1/19
     * @version 版本号
     * @param member
     * @return
     */
    @RequestMapping(value = "getAuth")
    @ResponseBody
    public Result getMemberAuth(@MemberAnno Member member){

        return memberService.getAuthMember(member);
    }
}
