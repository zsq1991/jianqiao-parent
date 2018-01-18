package com.zc.main.controller.main.mobile.after.consultation;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.consultation.ConsultationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @package : com.zc.main.controller.main.mobile.after.consultation
 * @progect : jianqiao-parent
 * @Description : 资讯信息
 * @Created by : 三只小金
 * @Creation Date ：2018年01月17日10:22
 */
@RequestMapping("mobile/after/consultation")
@RestController
public class ConsultationController {

    private  static Logger logger = LoggerFactory.getLogger(ConsultationController.class);

    @DubboConsumer(version = "1.0.0",timeout = 30000)
    private ConsultationService consultationService;

    /**
     *
     * @author huangxin
     * @data 2018/1/17 18:23
     * @Description: 用户个人中心   我的发布
     * @Version: 1.0.0
     * @param page
     * @param rows
     * @param member
     * @param checktype 1 访谈  2 口述   3 求助   4 分享
     * @return
     */
    @RequestMapping(value="findconsultationalltypebymember",method=RequestMethod.POST)
    @ResponseBody
    public Result findconsultationalltypebymember(
            @RequestParam(value="page",defaultValue="1",required=false)Integer page,
            @RequestParam(value="rows",defaultValue="10",required=false)Integer rows,
            @MemberAnno Member member,
            @RequestParam(value="checktype",defaultValue="1",required=false)String checktype){
        return consultationService.findConsultationAllByMember(page,rows,member,checktype);
    }

    @Explosionproof
    @RequestMapping(value="deleteconsultation",method= RequestMethod.POST)
    public Result deleteConsultationById(@RequestParam("id")Long id,
                                         @MemberAnno Member member){

        return this.consultationService.deleteConsultationById(id, member);
    }

    /**
     * 获取父级主题
     * @param type
     * @param member
     * @return
     */
    @RequestMapping(value="getParentConsultation",method=RequestMethod.POST)
    public Result getParentConsultation(@RequestParam("type")String type,
                                        @MemberAnno Member member,
                                        @RequestParam(value = "page",defaultValue ="1",required = false)Integer page,
                                        @RequestParam(value="size",defaultValue ="10",required = false)Integer size){

        return consultationService.getParentConsultation(type, member,page,size);
    }

    /**
     * 添加咨询
     * @param content
     * @param member
     * @return
     */
    @Explosionproof
    @RequestMapping(value="addConsultation",method=RequestMethod.POST)
    public Result addConsultation(@RequestParam("json")String content,
                                  @MemberAnno Member member){

        return consultationService.addConsultation(content, member);
    }
}
