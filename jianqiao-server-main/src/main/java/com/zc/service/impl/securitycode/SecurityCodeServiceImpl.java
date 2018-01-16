package com.zc.service.impl.securitycode;

import com.alibaba.dubbo.config.annotation.Service;
import com.mysql.jdbc.StringUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.service.securitycode.SecurityCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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


    @Override
    public Result sendCodeRegisterByPhone(String phone, String codeType) {
        Result result = null;
        // 判断手机号
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
        Matcher m = p.matcher(phone);

        if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
            return ResultUtils.returnError("手机号不合法");
        }
        if (!m.matches()) {
            return ResultUtils.returnError("手机号不合法");
        }
//        // 判断数据库中是否存在
//        Member memberByPhone = sendMsgDao.getMemberByPhone(phone);
//        if(memberByPhone !=null){
//            return ResultUtils.returnError("该手机号已注册，请直接登录",-621);
//        }
//        result = rpcSendMsgService.sendMessageCode(phone, codeType);

        return result;
    }
}
