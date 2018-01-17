package com.zc.service.impl.securitycode;

import com.alibaba.dubbo.config.annotation.Service;
import com.common.util.securitycode.TencentSms;
import com.mysql.jdbc.StringUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.service.securitycode.SecurityCodeService;
import com.zc.mybatis.dao.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description : 验证码实现
 * @Created by : tenghui
 * @Creation Date ：2018年01月16日11:29
 */
@Component
@Transactional
@Service(version = "1.0.0",interfaceClass=SecurityCodeService.class)
public class SecurityCodeServiceImpl implements SecurityCodeService {

    private static Logger logger = LoggerFactory.getLogger(SecurityCodeServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;


    @Override
    public Result sendCodeRegisterByPhone(String phone, String codeType) {
        logger.info("============通过图形验证码,进入发送短信方法===============");
        Result result = new Result();
        logger.info("=======检验手机号是否合法=========");
        String reg = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(phone);
        if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
            return ResultUtils.returnError("手机号不合法");
        }
        if (!m.matches()) {
            return ResultUtils.returnError("手机号不合法");
        }
        logger.info("=======检查手机号是否已经存在=========");
        Member memberByPhone = memberMapper.getMemberByPhone(phone);
        if(memberByPhone != null){
            return ResultUtils.returnError("该手机号已注册，请直接登录",-621);
        }
        result = this.sendMessageCode(phone, codeType);
        return result;
    }

    @Override
    public Result sendMessageCode(String phone, String codeType) {
        TencentSms sms = new TencentSms();
        Result sendMsg = sms.sendMsg(phone, codeType);
        return sendMsg;
    }

    @Override
    public Result checkMessageCode(String phone, String code, String codeType) {
        TencentSms sms = new TencentSms();
        Result sendMsg = sms.checkMsg(phone, code, codeType);
        return sendMsg;
    }
}
