package com.zc.main.controller.main.mobile.after.consultationfabulous;


import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Reference;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.consultationfabulous.ConsultationFabulousService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 咨询内容点赞取消赞
 * @author:  ZhaoJunBiao
 * @date:  2018/1/16 11:54
 * @version: 1.0.0
 */
@RequestMapping("mobile/after/consultationcfabulous")
@Controller
public class ConsultationfabulousController {

    private static Logger logger = LoggerFactory.getLogger(ConsultationfabulousController.class);

    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private ConsultationFabulousService consultationFabulousService;


    /**
     * @param member 用户信息
     * @param id     咨询id
     * @param type   点赞 1   取消赞2
     * @return
     * @description:对4种内容进行点赞
     * @author: ZhaoJunBiao
     * @date: 2018/1/16 13:51
     * @version: 1.0.0
     */
    @Explosionproof
    @RequestMapping(value = "fabulous", method = RequestMethod.POST)
    @ResponseBody
    public Result consultationcfabulous(@RequestParam("id") Long id,
                                        @RequestParam("type") Integer type,
                                        @MemberAnno Member member) {
        return consultationFabulousService.getConsultationFabulousByIdAndMemberId(id, member.getId(), type);

    }

    /**
     * @param member 用户信息
     * @param id     咨询id
     * @return
     * @description: 根据咨询id和会员id查询点赞
     * @author: ZhaoJunBiao
     * @date: 2018/1/16 14:51
     * @version: 1.0.0
     */
    @RequestMapping(value = "fabulous-type", method = RequestMethod.POST)
    @ResponseBody
    public Result getConsultationType(@RequestParam("id") Long id,
                                      @MemberAnno Member member) {

        return consultationFabulousService.getConsultationType(id, member.getId());

    }

}
