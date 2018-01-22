package com.zc.service.impl.membermessageservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.dto.attachment.AttachmentDTO;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberhelp.MemberHelp;
import com.zc.main.entity.memberhelpcase.MemberHelpCase;
import com.zc.main.entity.memberhelpimage.MemberHelpImage;
import com.zc.main.service.membermessage.MemberMessageService;
import com.zc.mybatis.dao.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @package : com.zc.service.impl.membermessageService
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月17日17:58
 */
@Service(version = "1.0.0",interfaceClass = MemberMessageService.class)
@Component
@Transactional(readOnly = true,rollbackFor=Exception.class)
public class MemberMessageServiceImpl implements MemberMessageService{

    private  static Logger logger = LoggerFactory.getLogger(MemberMessageServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberMessageMapper megMapper;
    @Autowired
    private MemberMsgMapper memberMessageMapper;
    @Autowired
    private MemberHelpMapper memberHelpMapper;
    @Autowired
    private  MemberHelpCaseMapper memberHelpCaseMapper;
    @Autowired
    private MemberHelpImageMapper memberHelpImageMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
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
        Integer type0 = 0;
        Integer type1 = 1;
        Integer type2 = 2;
        Integer type3 = 3;
        if(!type.equals(type0) || !type.equals(type1) || !type.equals(type2) || !type.equals(type3)){
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

    /**
     * 修改头像
     * @author zhaoshuaiqi
     * @data 2018/1/18
     * @param attachmentId
     * @param member
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result updateMemberLogo(Long attachmentId, Member member) {
        //1、实例化一个result
        Result result = new Result();
        logger.info("=========获取头像的传入参数=======");
        //2、判断所传的参数
        if(null==attachmentId){
            logger.info("=========上传图片=======");
            return ResultUtils.returnError("对不起,请上传您的图片");
        }
        if(null==member){
            logger.info("=========您不是会员,请先申请=======");
            return ResultUtils.returnError("对不起,您不是会员,请先申请");
        }
        //3、查询当前所要修改的会员logo
        logger.info("=========查询要修改的头像=======");
        Member memberdb = memberMapper.checkMemberById(member.getId());
        if(null==memberdb){
            return ResultUtils.returnError("对不起,您不是会员,请先申请");
        }
        try {
            this.megMapper.updateMemberLogoById(attachmentId,memberdb.getId());
            result.setCode(1);
            result.setMsg("头像修改成功");
            logger.info("=========头像修改成功=======");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            result.setCode(0);
            result.setMsg("头像修改失败");
            logger.info("=========头像修改失败=======");
        }
        return result;
    }

    /**
     * 修改昵称
     * @author zhaoshuaiqi
     * @data 2018/1/18
     * @param nickname
     * @param member
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result updateMemberNickname(String nickname, Member member) {
        logger.info("=========进入修改昵称=======");
        Result result = new Result();
        if(null==member){
            return ResultUtils.returnError("对不起,您不是会员,请先申请");
        }
        // 除去空格
        if (nickname != null) {
            nickname = nickname.replaceAll(" ", "");
        }
        // 判断是否为空
        if(com.alibaba.dubbo.common.utils.StringUtils.isBlank(nickname)){
            logger.info("=========昵称不能为空=======");
            return ResultUtils.returnError("昵称不能为空");
        }
        // 判断字符长度&修改昵称：支持2-8个字符。只支持汉字、字母。提示语：昵称格式不正确
        if (nickname.length() < 2 || nickname.length() > 8) {
            logger.info("=========昵称支持2-8个字符=======");
            return ResultUtils.returnError("昵称支持2-8个字符");
        }
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].·<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(nickname);
        boolean find = m.find();
        if(find){
            logger.info("=========昵称格式不正确=======");
            return ResultUtils.returnError("昵称格式不正确");
        }
        //查询当前所要修改的会员昵称
        Member memberdb = memberMapper.checkMemberById(member.getId());
        //将传过来的昵称放到memberdb中
        memberdb.setNickname(nickname);
        try{
            this.megMapper.updateMemberNickNameById(nickname,memberdb.getId());
            result.setCode(1);
            result.setMsg("昵称修改成功");
            logger.info("=========昵称修改成功=======");
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            result.setCode(0);
            result.setMsg("昵称修改失败");
            logger.info("=========昵称修改失败=======");
        }
        return result;
    }

    /**
     * 修改性别
     * @author zhaoshuaiqi
     * @data 2018/1/18
     * @param sexId
     * @param member
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result updateMembersex(Integer sexId, Member member) {
        logger.info("=========昵称修改性别=======");
        //不使用result，使用公司的ResultUtils工具类
        if(null==member){
            return ResultUtils.returnError("对不起,您不是会员,请先申请");
        }
        if(null==sexId){
            logger.info("=========性别标识不能为空=======");
            return ResultUtils.returnError("性别标识不能为空");
        }
        if(sexId != 1 && sexId != 2 ){
            logger.info("=========性别标识只能是1或2（1：男；2：女)=======");
            return ResultUtils.returnError("性别标识只能是1或2（1：男；2：女）");
        }
        Member memberdb = memberMapper.checkMemberById(member.getId());
        memberdb.setSex(sexId);
        try{
            this.megMapper.updateMemberSexById(sexId,memberdb.getId());
            logger.info("=========性别修改成功=======");
            return ResultUtils.returnSuccess("性别修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            logger.info("=========性别修改失败=======");
            return ResultUtils.returnError("性别修改失败");
        }
    }

    /**
     * 用户个人信息获取
     * @author huangxin
     * @data 2018/1/18
     * @param member
     * @return
     */
    @Override
    public Result memberMessageList(Member member) {
        logger.info("=======判断用户信息========");
        // 判断member是否为空
        if (member == null) {
            logger.info("=======用户信息为空========");
            return ResultUtils.returnError("没有对应的会员信息");
        }

        Long memberId = member.getId();
        logger.info("=======获取用户信息========");
        List<Map<String, Object>> membermessageList = megMapper.getMemberMessageList(memberId);

        logger.info("=======用户个人信息获取成功========");
        return ResultUtils.returnSuccess("用户个人信息获取成功：", membermessageList);
    }
}
