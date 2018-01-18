package com.zc.service.impl.register;

import com.alibaba.dubbo.config.annotation.Service;
import com.mysql.jdbc.StringUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.common.core.utils.MD5Util;
import com.zc.common.core.utils.UniqueUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.service.register.MemberRegisterService;
import com.zc.main.service.securitycode.SecurityCodeService;
import com.zc.mybatis.dao.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description : 用户注册实现类
 * @Created by : tenghui
 * @Creation Date ：2018年01月17日13:45
 */
@Component
@Transactional(readOnly = true)
@Service(version = "1.0.0",interfaceClass=MemberRegisterService.class)
public class MemberRegisterServiceImpl implements MemberRegisterService {

    private static Logger logger = LoggerFactory.getLogger(MemberRegisterServiceImpl.class);

    @Autowired
    private SecurityCodeService securityCodeService;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    @Transactional
    public Result memberRegisterByPhone(Map<String,Object> params) {
        logger.info("=========进入用户注册方法==========");
        Result result = new Result();
        logger.info("=========开始获取参数并校验格式是否正确==========");
        // 判断信息是否符合要求
        String phone = params.get("phone").toString();
        // 获取密码
        String password = params.get("password").toString();
        // 用户输入的验证码
        String image = params.get("imageCode").toString();
        // 获取验证密码
        String twicePassword = params.get("twicePassword").toString();

        String ip = params.get("ip").toString();

        String agent = params.get("agent").toString();

        String codeType="JQ2017613";
        // 判断手机是否符合判断格式,输入密码的规范
        Pattern pPhone = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
        Pattern pPasswordNum = Pattern.compile("^[A-Za-z0-9]{6,12}$");
        Matcher m = pPasswordNum.matcher(phone);
        if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
            logger.info("=========参数异常,注册方法结束==========");
            return ResultUtils.returnError("手机号不合法");
        }  else if (StringUtils.isEmptyOrWhitespaceOnly(image)) {
            logger.info("=========参数异常,注册方法结束==========");
            return ResultUtils.returnError("验证码不能为空");
        } else if (!m.matches()) {
            logger.info("=========参数异常,注册方法结束==========");
            return ResultUtils.returnError("手机号不合法");
        }else if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
            logger.info("=========参数异常,注册方法结束==========");
            return ResultUtils.returnError("密码格式有误");
        }else if (6 > password.length() || password.length() > 12) {
            logger.info("=========参数异常,注册方法结束==========");
            return ResultUtils.returnError("密码格式有误");
        } else if (!pPasswordNum.matcher(password).matches()) {
            logger.info("=========参数异常,注册方法结束==========");
            return ResultUtils.returnError("密码格式有误");
        } else if (!password.equals(twicePassword)) {
            logger.info("=========参数异常,注册方法结束==========");
            return ResultUtils.returnError("两次密码设置不一致");
        }

        // 验证码的结果，checkMsg.getCode()为1是验证成功
        logger.info("=========开始验证验证码是否正确==========");
//        Result checkMsg = securityCodeService.checkMessageCode(phone, image, codeType);
//        if (!(checkMsg.getCode() == 1)) {
//            logger.info("=========验证码未通过,注册方法结束==========");
//            return ResultUtils.returnError(checkMsg.getMsg());
//        }
        logger.info("=========查询用户是否已经注册==========");
        Member list = memberMapper.getMemberByPhone(phone);
        if (list != null) {
            return ResultUtils.returnError("该手机号已注册，请直接登录");
        }
        logger.info("=========系统获取相关参数,准备加入注册信息==========");

        // 获取uuid
        String uuid = UniqueUtils.getUUID();
        uuid = uuid.replace("-", "");
        // 密码：md5(md5(密码)+uuid)
        String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + uuid, "utf-8");

        String userbrowser = "未知";
        if (null != agent) {
            if (agent.contains("Chrome")) {
                userbrowser = "Chrome";
            } else if (agent.contains("Firefox")) {
                userbrowser = "Firefox";
            } else if (agent.contains("Safari")) {
                userbrowser = "Safari";
            } else if (agent.contains("Trident")) {
                userbrowser = "Trident";
            } else if (agent.contains("IE")) {
                userbrowser = "IE";
            } else if (agent.contains("360")) {
                userbrowser = "360";
            }
        }
        logger.info("=========创建实体,开始注册==========");
        Member member = new Member();
        String substring = phone.substring(0,3);
        String substring2 = phone.substring(7,11);
        String nicknamess=substring+"****"+substring2;
        member.setUuid(uuid);
        member.setPhone(phone);
        member.setPassword(passwordMD5);
        member.setCreatedTime(new Date());
        member.setUpdateTime(new Date());
        member.setCreatedIp(ip);
        member.setUserType(0);
        member.setStatus(0);
        member.setNickname(nicknamess);

        Result saveMemberRegister = this.saveMemberRegister(member);
        if (saveMemberRegister.getCode() == 1) {
            Member findMemberByPhone = memberMapper.getMemberByPhone(phone);
            Map map = new HashMap();
            map.put("id", findMemberByPhone.getId());
            map.put("nickname", findMemberByPhone.getNickname());
            map.put("uuid", findMemberByPhone.getUuid());
            map.put("phone", findMemberByPhone.getPhone());
            Integer type = findMemberByPhone.getUserType() == null ? 0 : findMemberByPhone.getUserType().intValue();
            map.put("type", type);// 0表示，0普通 1认证后用户可以发布访谈 口述
            result.setCode(1);
            result.setContent(map);
            result.setMsg("成功");
        } else {
            return ResultUtils.returnError("注册失败");
        }
        return result;

    }

    @Override
    @Transactional
    public Result saveMemberRegister(Member member) {
        Result result = new Result();
        try {
            int insert = memberMapper.insert(member);
            if ( insert > 0 ) {
                result.setCode(1);
                result.setMsg("保存成功");
//                result.setContent(saveAndModify);
            }
        } catch (Exception e) {
            // TODO: handle exception
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            return ResultUtils.returnError("注册用户失败");
        }
        return result;
    }


}
