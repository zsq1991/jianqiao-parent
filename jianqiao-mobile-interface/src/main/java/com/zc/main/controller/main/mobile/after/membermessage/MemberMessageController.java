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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param caseId   病例id
     * @param imgId    影像id
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

    /**用户个人信息获取【已关注或未关注】
     * @author:  Zhaoshuaiqi
     * @date:  2018/1/17
     * @param member
     * @param member_id
     * @return
     */
    @RequestMapping(value = "member-info",method = RequestMethod.POST)
    @ResponseBody
    public Result memberInfo(@MemberAnno Member member,
                             @RequestParam(value ="member_id") Long member_id){
        Result rs = memberService.memberInfo(member,member_id);
        return rs;
    }

    /**用户个人信息获取
     * @author:  Zhaoshuaiqi
     * @date:  2018/1/17
     * @param member
     * @return
     */
    @RequestMapping(value = "membermessage-list", method = RequestMethod.POST)
    @ResponseBody
    public Result memberMessageList(@MemberAnno Member member) {
        Result rs = messageService.memberMessageList(member);
        return rs;
    }

    /**用户资料编辑--修改头像 昵称 性别
     * 修改头像
     * @author:  Zhaoshuaiqi
     * @date:  2018/1/17
     * @param attachmentId
     * @param member
     * @return
     */
    @Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
    @RequestMapping(value = "update-member-logo",method = RequestMethod.POST)
    @ResponseBody
    public Result updatememberlogo(@RequestParam(value = "attachmentId") Long attachmentId,
                                   @MemberAnno Member member) {
        try {
            Result result = this.messageService.updateMemberLogo(attachmentId, member);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.returnError("会员LOGO修改异常");
        }
    }

    /**修改昵称
     * @author:  Zhaoshuaiqi
     * @date:  2018/1/17
     * @param nickname
     * @param member
     * @return
     */
    @Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
    @RequestMapping(value = "update-member-nickname", method = RequestMethod.POST)
    @ResponseBody
    public Result updateNickname(@RequestParam(value = "nickname") String nickname,
                                 @MemberAnno Member member) {
       /*检验昵称是否已存在
         List<Map<String, Object>> nicknameIsExist = messageMapper.getMemberByNickname(nickname);
        if(nicknameIsExist.size()>0){
            return ResultUtils.returnError("昵称不能重复");
        }*/

        try {
            Result result = this.messageService.updateMemberNickname( nickname, member);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.returnError("昵称修改异常");
        }
    }

    /**修改性别
     * @author:  Zhaoshuaiqi
     * @date:  2018/1/17
     * @param member
     * @param sexid
     * @return
     */
    @Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
    @RequestMapping(value = "update-member-sex", method = RequestMethod.POST)
    @ResponseBody
    public Result updatemembersex(@MemberAnno Member member,
                                  @RequestParam(value = "sexid") Integer sexid){
        try {
            Result result = this.messageService.updateMembersex( sexid, member);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.returnError("性别修改异常");
        }
    }
}
