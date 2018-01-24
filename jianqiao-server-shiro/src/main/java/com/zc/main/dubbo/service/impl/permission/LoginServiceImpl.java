package com.zc.main.dubbo.service.impl.permission;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.zc.common.core.encrypt.Md5;
import com.zc.common.core.shiro.PermissionEnum;
import com.zc.common.core.shiro.RespCode;
import com.zc.common.core.shiro.Result;
import com.zc.common.core.shiro.ResultUtil;
import com.zc.main.dto.UserDTO;
import com.zc.main.dubbo.service.permission.ILoginService;
import com.zc.main.entity.permission.User;
import com.zc.main.vo.SessionUserVO;
import com.zc.main.vo.UserVO;
import com.zc.mybatis.dao.UserMapper;
import com.zc.mybatis.dao.UserRoleMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @描述：登录实现层
 */
@Component
@Service(version="1.0.0",interfaceClass=ILoginService.class)
public class LoginServiceImpl implements ILoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    /**
     * 登录校验
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        logger.info("登录参数入参，user={}", JSON.toJSONString(user));
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setTelphone(user.getTelphone());
            UserVO dbUser = userMapper.getUserByCondition(userDTO);
            if (dbUser == null || !user.getPassword().equals(dbUser.getPassword())) {
                //手机号或者密码有错误
                return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
            }
            if (dbUser.getIsable() == 2) {
                return ResultUtil.getResult(PermissionEnum.USER_ISABLED);
            }

            //更新登录日期
            User loginUser = new User();
            loginUser.setId(dbUser.getId());
            loginUser.setLoginTime(new Date());
            //userMapper.updateByPrimaryKeySelective(loginUser);

            //存用户Session
            SessionUserVO sessionUserVo = new SessionUserVO();
            sessionUserVo.setId(dbUser.getId());
            //判断是否是代理商，商户，或者供应商登录
            if (dbUser.getAgentName() != null) {
                sessionUserVo.setUserName(dbUser.getAgentName());
            } else if (dbUser.getMerchantName() != null) {
                sessionUserVo.setUserName(dbUser.getMerchantName());
            } else if (dbUser.getSupplierName() != null) {
                sessionUserVo.setUserName(dbUser.getSupplierName());
            } else {
                sessionUserVo.setUserName(dbUser.getUserName());
            }
            sessionUserVo.setTelphone(dbUser.getTelphone());
            sessionUserVo.setSessionRoleVo(userRoleMapper.selectRoleVOByUserId(dbUser.getId()));
            return ResultUtil.getResult(RespCode.Code.SUCCESS, sessionUserVo);
        } catch (Exception ex) {
            logger.error("登录异常,ex={}", ex);
        }
        return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Result findPass(UserDTO userDTO) {
        logger.info("忘记密码参数入参，userDTO={}", JSON.toJSONString(userDTO));
        try {
            User dbUser = userMapper.findUserByPhone(userDTO.getTelphone());
            if (dbUser == null) {
                //手机号不存在
                return ResultUtil.getResult(PermissionEnum.NO_TELPHONE_ERROR);
            } else {

                if (dbUser.getIsable() == 2) {
                    //账户已被冻结
                    return ResultUtil.getResult(PermissionEnum.USER_ISABLED);
                }

                User user = new User();
                user.setId(dbUser.getId());
                user.setTelphone(userDTO.getTelphone());
                user.setPassword(Md5.getMD5Str(userDTO.getPassword()));
                user.setUpdateTime(new Date());
                //userMapper.updateByPrimaryKeySelective(user);
                return ResultUtil.getResult(RespCode.Code.SUCCESS);
            }
        } catch (Exception ex) {
            logger.error("找回密码异常,ex={}", ex);
           // new PermissionBizException(PermissionEnum.FIND_PASSWORD_ERROR);

        }
        return ResultUtil.getResult(PermissionEnum.Code.INTERNAL_SERVER_ERROR);
    }
}
