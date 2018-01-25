package com.zc.main.controller.main.mobile.after.consultationcommentfabulous;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.consultationcommentfabulous.ConsultationCommentFabulousService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @version 1.0.0
 * @description ：咨询回复评论点赞
 * @author  by  : gaoge
 * @Creation Date ： 2018/1/16 16:56
 */
@RequestMapping("mobile/after/consultationcommentfabulous")
@Controller
public class ConsultationCommentFabulousController {

    @DubboConsumer(version = "1.0.0",timeout = 30000)
    private ConsultationCommentFabulousService consultationCommentFabulousService;

    /**
     * @param commentid 资讯评论id
     * @param type      1点赞   2取消赞
     * @param member    用户
     * @return :
     * @description ：咨询回复评论点赞
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 16:56
     * @version 1.0.0
     */
    // 在需要防爆的方法上加上注解@Explosionproof
    @Explosionproof
    @RequestMapping(value = "fabulous", method = RequestMethod.POST)
    @ResponseBody
    public Result consultationCommentFabulous(
            @RequestParam(value = "commentid") Long commentid,
            @RequestParam(value = "type") Integer type,
            @MemberAnno Member member
    ) {

        return consultationCommentFabulousService.saveConsultationCommentFabulous(commentid, member.getId(), type);

    }
}
