package com.zc.service.impl.membermessageService;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.dto.attachment.AttachmentDTO;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberhelp.MemberHelp;
import com.zc.main.entity.memberhelpcase.MemberHelpCase;
import com.zc.main.entity.memberhelpimage.MemberHelpImage;
import com.zc.main.service.membermessage.MemberMessageService;
import com.zc.mybatis.dao.MemberHelpCaseMapper;
import com.zc.mybatis.dao.MemberHelpImageMapper;
import com.zc.mybatis.dao.MemberHelpMapper;
import com.zc.mybatis.dao.MemberMsgMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Map;

/**
 * @package : com.zc.service.impl.membermessageService
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月17日17:58
 */
@Service(version = "1.0.0",interfaceClass = MemberMessageService.class)
@Component
@Transactional(readOnly = true)
public class MemberMessageServiceImpl implements MemberMessageService{

    private  static Logger logger = LoggerFactory.getLogger(MemberMessageServiceImpl.class);

    @Autowired
    private MemberMsgMapper memberMessageMapper;
    @Autowired
    private MemberHelpMapper memberHelpMapper;
    @Autowired
    private  MemberHelpCaseMapper memberHelpCaseMapper;
    @Autowired
    private MemberHelpImageMapper memberHelpImageMapper;

    @Override
    @Transactional(readOnly = false)
    public Result saveMemberHelp(Member member, MemberHelp memberhelp, String caseId, String imgId) {
        logger.info("求助信息发布接口调用，方法入参{"+"病例id："+caseId +"，影像id："+imgId+"}");
        Result result = new Result();
        try{
        if(null==member){
            return ResultUtils.returnError("对不起,您不是会员,请先申请");
        }
        //1、判断姓名是否为空
        String name = memberhelp.getName();
        if(null==name||"".equals(name)){
            return ResultUtils.returnError("姓名不能为空");
        }
        String helpPhone = memberhelp.getPhone();
        if(com.mysql.jdbc.StringUtils.isEmptyOrWhitespaceOnly(helpPhone)){
            return ResultUtils.returnError("手机号不能为空");
        }
        String regex="^1[34578]\\d{9}$";
        logger.info("手机验证："+helpPhone.matches(regex));
        if(!helpPhone.matches(regex)){
            return ResultUtils.returnError("请输入正确格式的手机号码");
        }
        //3、地址
        String address = memberhelp.getAddress();
        if(null==address||"".equals(address)){
            return ResultUtils.returnError("地址不能为空");
        }
        //4、病情
        String content = memberhelp.getContent();
        if(null==content||"".equals(content)){
            return ResultUtils.returnError("病情介绍不能为空");
        }
        //需要标识type--来源  0是访谈 1是口述 2是求助 3是分享
        Integer type = memberhelp.getType();
        if(type==null){
            return ResultUtils.returnError("来源标识不能为空");
        }
        if(type!=0 && type!=1 && type!=2 && type !=3){
            return ResultUtils.returnError("参数非法");
        }
        //处理病例的id
        if(StringUtils.isNotBlank(caseId)){
            memberhelp.setMemberId(member.getId());
            //memberhelp.setPhone(phone1);
            memberhelp.setPhone(helpPhone);
            memberHelpMapper.insert(memberhelp);
            // 查看caseId图片
            String[] split = caseId.split(",");
            for (int i=0;split.length>i;i++) {
                    AttachmentDTO attachment = memberMessageMapper.getAttamentById(split[i]);
                if(null==attachment){
                    return ResultUtils.returnError("没有此病历图片！");
                }
                MemberHelpCase memberHelpCase = new MemberHelpCase();
                AttachmentDTO attachmentcase = new AttachmentDTO();
                attachmentcase.setId(Long.valueOf(split[i]));
                memberHelpCase.setAttachmentId(attachment.getId());
                memberHelpCase.setMemberHelpId(memberhelp.getId());
                memberHelpCase.setMemberId(member.getId());
                memberHelpCaseMapper.insert(memberHelpCase);
            }
        }
        if(StringUtils.isNotBlank(imgId)){
            // 查看imgId图片
            String[] split2 = imgId.split(",");
            for (int i=0;split2.length>i;i++) {
                AttachmentDTO attachment2 = memberMessageMapper.getAttamentById(split2[i]);
                if(null==attachment2){
                    return ResultUtils.returnError("没有此影像图片！");
                }
                //处理影像资料的id
                MemberHelpImage memberHelpImage = new MemberHelpImage();
                AttachmentDTO attachmentImg = new AttachmentDTO();
                attachmentImg.setId(Long.valueOf(split2[i]));
                memberHelpImage.setAttachmentId(attachmentImg.getId());
                memberHelpImage.setMemberHelpId(memberhelp.getId());
                memberHelpImage.setMemberId(member.getId());
                memberHelpImageMapper.insert(memberHelpImage);
            }
        }

            return ResultUtils.returnSuccess("求助信息保存成功");
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            return ResultUtils.returnError("求助信息保存失败");
        }
    }

    /**
     * 查询啊驳回原因
     * @author huangxin
     * @data 2018/1/18 16:30
     * @Description: 查询啊驳回原因
     * @Version: 3.2.0
     * @param conid 资讯id
     * @return
     */
    @Override
    public Map<String, Object> getContentById(Long conid) {
        return memberMessageMapper.getContentById(conid);
    }
}
