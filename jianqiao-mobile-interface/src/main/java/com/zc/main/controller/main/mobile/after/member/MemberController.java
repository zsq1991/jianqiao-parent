package com.zc.main.controller.main.mobile.after.member;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.member.MemberService;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证
 * @author xwolf
 * @date 2017-06-19 9:33
 * @since 1.8
 */
@RestController
@RequestMapping("mobile/after/member")
public class MemberController {

    @DubboConsumer(version = "1.0.0",timeout = 300000)
    private MemberService memberService;


    /**
     *  获取用户认证信息
     * @param member
     * @return
     */
    @RequestMapping(value = "getAuth")
    @ResponseBody
    public Result getMemberAuth(@MemberAnno Member member){

        return memberService.getAuthMember(member);
    }

    /**
     * 用户认证
     * @author huangxin
     * @data 2018/1/19 16:34
     * @Description: 用户认证
     * @Version: 3.2.0
     * @param name 姓名
     * @param no 身份证号
     * @param phone 手机号
     * @param member 用户信息
     * @param code 验证码
     * @param pics 附件ID ,多个ID用,拼接
     * @param type 修改认证信息时传入此参数
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "auth",method = RequestMethod.POST)
    public Result memberAuth(@RequestParam("name")String name , @RequestParam("card")String no,
                             @RequestParam("mobile")String phone,
                             @RequestParam("code")String code,
                             @RequestParam("pics")String pics,
                             @RequestParam(value = "type",required = false)String type,
                             @MemberAnno Member member){

        return memberService.authMember(name,no,phone,code,pics,member,type);
    }








}
