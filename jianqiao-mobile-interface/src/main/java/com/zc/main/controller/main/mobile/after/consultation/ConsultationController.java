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

    /**
     * 信息详情 分页加载专题下信息
     * @author huangxin
     * @data 2018/1/18 14:41
     * @Description: 信息详情 分页加载专题下信息
     * @Version: 1.0.0
     * @param cid 专题ID或求助ID
     * @param member 用户
     * @param page
     * @param row
     * @return
     */
    @RequestMapping(value="getSubjectByPage",method=RequestMethod.POST)
    public @ResponseBody Result consultationTypePage (
            @RequestParam("id")String cid,@MemberAnno Member member,
            @RequestParam(value = "page",defaultValue = "1",required = false)int page,
            @RequestParam(value = "size",defaultValue = "5",required = false)int row){

        return consultationService.getConsultationSubjectByPage(cid,page,row,member);
    }

    /**
     * 信息详情<访谈、口述、求助、分享>
     * @author huangxin
     * @data 2018/1/18 15:28
     * @Description: 信息详情<访谈、口述、求助、分享>
     * @Version: 3.2.0
     * @param cid 信息ID
     * @param member 用户信息
     * @param row
     * @param type
     * @return
     */
    @RequestMapping(value="detail",method=RequestMethod.POST)
    public Result consultationDetail (@RequestParam("id")String cid,@MemberAnno Member member,
                                      @RequestParam(value = "row",defaultValue = "1",required = false)int row,
                                      @RequestParam(value = "type",defaultValue = "0",required = false)String type){

        return consultationService.getConsultationDetail(cid, member,row,type);
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
    /**
     * 修改咨询
     * @param content
     * @param member
     * @return
     */
    @Explosionproof
    @RequestMapping(value="updateConsultation",method=RequestMethod.POST)
    public Result updateConsultation(@RequestParam("json")String content,
                                     @MemberAnno Member member){

        return consultationService.updateConsultation(content, member);
    }
}
