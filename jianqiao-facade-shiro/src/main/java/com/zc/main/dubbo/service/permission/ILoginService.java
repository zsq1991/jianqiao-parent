package com.zc.main.dubbo.service.permission;

import com.zc.common.core.shiro.Result;
import com.zc.main.dto.UserDTO;
import com.zc.main.entity.permission.User;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述：登录校验
 *
 * @author ： Mr.Shu
 *
 * @创建时间：2017-03-15
 *
 * @Copyright @2017 by Mr.Shu
 */
public interface ILoginService {
    /**
     * @描述：登录校验
     *
     * @param: user
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:52
     */
    Result login(User user);

    /**
     * @描述：忘记密码
     * @param: user
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:52
     */
    Result findPass(UserDTO userDTO);
}
