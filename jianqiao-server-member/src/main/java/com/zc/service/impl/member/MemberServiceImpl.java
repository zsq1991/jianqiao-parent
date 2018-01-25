package com.zc.service.impl.member;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.common.util.securitycode.TencentSms;
import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.attachment.Attachment;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberattachment.MemberAttachment;
import com.zc.main.service.member.MemberService;
import com.zc.mybatis.dao.MemberAttachmentMapper;
import com.zc.mybatis.dao.MemberMapper;
import com.zc.mybatis.dao.MemberMessageMapper;
import com.zc.mybatis.dao.MemberMsgMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Service(version = "1.0.0",interfaceClass =MemberService.class )
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {

    private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberAttachmentMapper memberAttachmentMapper;
    @Autowired
    private MemberMsgMapper memberMsgMapper;
    @Autowired
    private MemberMessageMapper messageMapper;


    /**
     * @description 接口说明 修改收藏内容数量
     * @author 王鑫涛
     * @date 8:28 2018/1/18
     * @version 版本号
     * @param member 用户
     * @return
     */
    @Override
    public int updateByConNum(Long id,Long num) {
        int i = memberMapper.updateByConNum(id,num);
        return i;
    }

    /**
     * @description 接口说明 修改用户信息
     * @author 王鑫涛
     * @date 17:14 2018/1/17
     * @version 版本号
     * @param member 用户
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public int updateById(Member member) {
        int i = memberMapper.updateByPrimaryKeySelective(member);
        return i;
    }
    /**
     * @description 接口说明 根据id获取用户信息
     * @author 王鑫涛
     * @date 17:08 2018/1/17
     * @version 版本号
     * @param id 用户id
     * @return
     */
    @Override
    public Member findOne(Long id) {
        Member one = memberMapper.findOne(id);
        return one;
    }

    @Override
    public Member getMerberById(Long memberId) {
        logger.info("----------查询用户开始----------");
        Member member = memberMapper.selectByPrimaryKey(memberId);
        logger.info("----------查询用户结束----------");
        return member;
    }

    /**
     * @description方法说明 获取用户认证
     * @author 王鑫涛
     * @date  13:34  2018/1/19
     * @version 版本号
     * @param member 用户
     * @return
     */
    @Override
    public Result getAuthMember(Member member) {
        logger.info("==============进入获取用户认证方法===============");
        Map<String,Object> map = Maps.newHashMap();
        map.put("uuid",member.getUuid());
        map.put("phone",member.getPhone());
        logger.info("==============获得用户信息通过id和uuid================");
        Map<String,Object> result = memberMapper.getMemberByIdAndUuid(map);
        if (!Objects.isNull(result)){
            logger.info("=================根据id获取用户附件================");
            List<Map<String,Object>> resultAttachment = memberAttachmentMapper.getMemberAttachment(member.getId());
            result.put("pics",resultAttachment);
        }
        logger.info("==============获取用户认证方法结束===============");
        return ResultUtils.returnSuccess("成功",result);
    }


    /**
     * @description ：检测是否是高级用户
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/17 13:46
     * @param memberId
     * @return
     */
    @Override
    public Member checkMemberById(Long memberId) {

        return memberMapper.checkMemberById(memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Member getMemberByPhoneAndUuid(Map<String, Object> params) {
        logger.info("============根据手机号和UUID查询用户开始,params={}", JSON.toJSONString(params));
        Member member = memberMapper.getMemberByPhoneAndUuid(params);
        logger.info("============根据手机号和UUID查询用户结束,member={}", JSON.toJSONString(member));
        return member;
    }
    @Override
    public Member getMemberByIdAndUuid(Map<String, Object> params) {
        Member member = new Member();
        Map<String,Object> map = memberMapper.getMemberByIdAndUuid(params);
        if (!Objects.isNull(map)){
            Object id = map.get("id");
            if (Objects.isNull(id)){
                return member;
            }
            member.setId(Long.valueOf(String.valueOf(id)));
            Object nickname = map.get("nickname");
            if (!Objects.isNull(nickname)){
                member.setNickname(String.valueOf(nickname));
            }
            Object name = map.get("name");
            if (!Objects.isNull(name)){
                member.setName(String.valueOf(name));
            }
            Object userType = map.get("user_type");
            if (!Objects.isNull(userType)){
                member.setUserType(Integer.valueOf(String.valueOf(userType)));
            }
            Object phone = map.get("phone");
            if (!Objects.isNull(phone)){
                member.setPhone(String.valueOf(phone));
            }

            Object card = map.get("card");
            if (!Objects.isNull(card)){
                member.setCard(String.valueOf(card));
            }

            Object status = map.get("status");
            if (!Objects.isNull(status)){
                member.setStatus(Integer.valueOf(status.toString()));
            }
        }
        return member;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Member getLockOne(Long memberId) {
        return memberMapper.getLockOne(memberId);
    }

    /**
     * 用户认证
     * @author huangxin
     * @data 2018/1/19 16:37
     * @Description: 用户认证
     * @Version: 3.2.0
     * @param name 姓名
     * @param card 身份证号
     * @param phone 手机号
     * @param code 验证码
     * @param pics 附件ID ,多个ID用,拼接
     * @param member 用户信息
     * @param type 修改认证信息时传入此参数
     * @return
     */
    @Override
    public Result authMember(String name, String card, String phone, String code, String pics, Member member, String type) {
        logger.info("****authMember**name={},card={},pics={},type={}**",name,card,pics,type);
        // 验证
        if (StringUtils.isBlank(name)) {
            return ResultUtils.returnError("姓名不能为空");
        }
        if (StringUtils.isBlank(card)) {
            return ResultUtils.returnError("身份证号不能为空");
        }
        if (StringUtils.isBlank(phone)) {
            return ResultUtils.returnError("电话不能为空");
        }
        if (StringUtils.isBlank(code)) {
            return ResultUtils.returnError("验证码不能为空.");
        }
        if (StringUtils.isBlank(pics)) {
            return ResultUtils.returnError("附件不能为空");
        }
        try {
            if (!"0".equals(String.valueOf(member.getStatus())) && !"3".equals(String.valueOf(member.getStatus()))){
                return ResultUtils.returnError("非待认证状态,无法认证");
            }
            if (!"0".equals(String.valueOf(member.getUserType()))){
                return ResultUtils.returnError("非待认证状态,无法认证");
            }
            String[] picsAry = pics.split(",");
            if (!Objects.isNull(picsAry) && picsAry.length!=4){
                return ResultUtils.returnError("图片只能上传四张");
            }
            TencentSms sms = new TencentSms();
            String codeType ="JQ2017613";
            Result msgResult =  sms.checkMsg(phone,code,codeType);
            if (!Objects.isNull(msgResult)){
                Integer msgCode = msgResult.getCode();
                if ("0".equals(String.valueOf(msgCode))){
                    return ResultUtils.returnError("验证码错误");
                }
            } else {
                return ResultUtils.returnError("验证码验证失败");
            }

            // 调用接口验证二要素

            Result elementResult = sms.checkCarId(name,card);
            logger.info("用户ID={},返回参数={}",member.getId(),elementResult.toString());

            if (("0").equals(elementResult.getCode())) {
                if ("1".equals(type)){
                    memberAttachmentMapper.deleteAttachment(member.getId());
                }
                String[] pic = pics.split(",");
                Arrays.stream(pic).forEach(e->{
                    if (StringUtils.isNotBlank(e)){
                        Attachment attachment = memberAttachmentMapper.getAttachmentById(Long.parseLong(e));
                        if (!Objects.isNull(attachment)){
                            MemberAttachment memberAttachment = new MemberAttachment();
                            memberAttachment.setMemberId(member.getId());
                            logger.info("当前member:"+member);
                            memberAttachment.setAddress(attachment.getAddress());
                            logger.info("当前图片的地址:"+attachment.getAddress());
                            memberAttachment.setName(attachment.getName());
                            memberAttachment.setAttachmentId(attachment.getId());
                            memberAttachmentMapper.save(memberAttachment);
                        }
                    }
                });
                member.setName(name);
                member.setCard(card);
                //认证中
                member.setUserType(2);
                //待审核
                member.setStatus(1);
                // 保存身份证信息
                return this.saveMember(member);
            }
            return ResultUtils.returnError("验证姓名身份证失败,请核对信息!");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResultUtils.returnError("姓名身份证验证失败");
        }
    }

    /**
     * 用户个人信息获取【已关注或未关注】
     * @author zhaoshuaiqi
     * @data 2018/1/19
     * @Description:
     * @param member
     * @param memberId
     * @return
     */
    @Override
    public Result memberInfo(Member member, Long memberId) {
        // 判断member是否为空
        if (memberId == null) {
            return ResultUtils.returnError("没有对应的会员信息");
        }
        Map<String, Object> map=Maps.newHashMap();
        map.put("memberId", memberId);
        if(member!=null){
            map.put("login_member_id", member.getId());
        }

        List<Map<String, Object>> memberinfo =messageMapper.getMemberInfo(map);

        for (Map<String, Object> getphone : memberinfo) {
            //phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
            Object phone = getphone.get("phone");
            if(phone!=null){
                phone = ((String) phone).replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
                getphone.put("phone", phone);
            }
        }

        return ResultUtils.returnSuccess("用户个人信息获取OK：", memberinfo);
    }

    /**
     * 保存用户认证信息
     * @author huangxin
     * @data 2018/1/19 17:38
     * @Description: 保存用户认证信息
     * @Version: 3.2.0
     * @param pmember 用户信息
     * @return
     */
    private Result saveMember(Member pmember) {
        try {
            logger.info("pmember:{}",pmember);
            Member member = memberMapper.findOne(pmember.getId());
            member.setUserType(pmember.getUserType());
            //待审核
            member.setStatus(pmember.getStatus());
            member.setName(pmember.getName());
            member.setCard(pmember.getCard());
            logger.info("重新认证的member:"+member);
            memberMapper.updateByPrimaryKeySelective(member);
            logger.info("member的status值:================{}",member.getStatus());
            //判断是否是审核失败修改状态,@wudi，如果是审核失败的修改删除通知的memberMsg,1认证驳回  2内容驳回
            memberMsgMapper.deleteMemberMsgByMId(member.getId(),1);
            return ResultUtils.returnSuccess("提交成功,资料正在审核中");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResultUtils.returnSuccess("认证失败");
        }
    }
}
