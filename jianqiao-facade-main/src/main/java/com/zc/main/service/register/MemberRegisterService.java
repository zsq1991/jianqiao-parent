package com.zc.main.service.register;

import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description : 用户注册实现接口
 * @Created by : tenghui
 * @Creation Date ：2018年01月17日13:44
 */
public interface MemberRegisterService {

    /**
     * @description ：用户注册
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/17 13:49
     * @version : 1.0.0
     */
    public Result memberRegisterByPhone(Map<String,Object> params);

    /**
     * @description ：保存用户
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/17 15:46
     * @version : 1.0.0
     */
    public Result saveMemberRegister(Member member);

    /**
     * @description ：修改密码 业务逻辑
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/18 15:53
     * @version : 1.0.0
     */
    public Result updatePasswordByPhone(Map<String,Object> params);

}
