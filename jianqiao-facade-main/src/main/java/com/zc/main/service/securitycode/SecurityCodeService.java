package com.zc.main.service.securitycode;

import com.zc.common.core.result.Result;

/**
 * @Description : 验证码接口
 * @Created by : tenghui
 * @Creation Date ：2018年01月16日11:26
 */
public interface SecurityCodeService {

    /**
     * @description ：发送短信
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/16 17:29
     * @version : 1.0.0
     * @param phone  手机号
     * @param codeType  发送类型
     * @return
     */
    public Result sendCodeRegisterByPhone(String phone, String codeType);

}
