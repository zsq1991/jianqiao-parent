package com.zc.main.dubbo.service.impl.permission;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zc.common.core.encrypt.Md5;
import com.zc.common.core.shiro.PermissionEnum;
import com.zc.common.core.shiro.RespCode;
import com.zc.common.core.shiro.Result;
import com.zc.common.core.shiro.ResultUtil;
import com.zc.common.core.utils.page.PageBean;
import com.zc.main.dto.UpdatePassDTO;
import com.zc.main.dto.UpdateUserRoleDTO;
import com.zc.main.dto.UserDTO;
import com.zc.main.dto.UserRoleDTO;
import com.zc.main.dubbo.service.permission.IUserService;
import com.zc.main.entity.permission.Role;
import com.zc.main.entity.permission.User;
import com.zc.main.entity.permission.UserRole;
import com.zc.main.exception.PermissionBizException;
import com.zc.main.vo.UserVO;
import com.zc.mybatis.dao.RoleMapper;
import com.zc.mybatis.dao.UserMapper;
import com.zc.mybatis.dao.UserRoleMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @描述：用户实现层
 */
@Component
@Service(version = "1.0.0", interfaceClass = IUserService.class)
public class UserTServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserTServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addUser(UserRoleDTO dto) {
        try {
            logger.info("新增用户入参，dto={}", JSON.toJSONString(dto));
            Result result = ResultUtil.getResult(PermissionEnum.Code.FAIL);
            //if (ParamsCheck.checkAddUser(dto)) {
            //验证手机号是否存在
            if (null != userMapper.findUserByPhone(dto.getTelphone())) {
                return ResultUtil.getResult(PermissionEnum.REGISTERED);
            }
            User user = new User();
            //参数转换
            paramsTranf(user, dto);
            logger.info("新增用户入参，user={}", JSON.toJSONString(user));
            //新增用戶
            userMapper.insert(user);
            Long userId = user.getId();
            //除了管理员类型的账号以外，需要默认分配角色
            if (dto.getRoleCode().intValue() != 1) {
                addUserRole(dto.getRoleCode().byteValue(), userId);
            }
            result = ResultUtil.getResult(PermissionEnum.Code.SUCCESS, userId);
            //}
            logger.info("新增用户返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception ex) {
            logger.error("新增用户异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.ADD_USER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updatePassword(User user) {
        try {
            logger.info("修改密码入参,user={}", JSON.toJSONString(user));
            if (StringUtils.isNotBlank(user.getTelphone())) {
                User findUser = this.userMapper.findUserByPhone(user.getTelphone());
                if (findUser == null) {
                    return new Result(false, "500", "用户不存在");
                }
                user.setId(findUser.getId());
            }
            modifyUser(user);
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
        } catch (Exception ex) {
            logger.error("修改密码异常，ex={}", ex);
            //throw new PermissionBizException(PermissionEnum.UPDATE_PASSWORD_ERROR);
            return null;
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updatePass(UpdatePassDTO updatePassDto) {
        try {
            Result result;
            logger.info("(后台)修改密码入参,updatePassDto={}", JSON.toJSONString(updatePassDto));
            User user = userMapper.selectByPrimaryKey(updatePassDto.getId());
            String oldPass = Md5.getMD5Str(updatePassDto.getPassword());
            String newPass = Md5.getMD5Str(updatePassDto.getNewPassword());
            if (user != null && StringUtils.isNotBlank(user.getPassword())
                    && user.getPassword().equals(oldPass)) {
                User mdyUser = new User();
                mdyUser.setId(updatePassDto.getId());
                mdyUser.setPassword(newPass);
                modifyUser(mdyUser);
                result = ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
            } else {
                result = ResultUtil.getResult(PermissionEnum.PASSWORD_ERROR);
            }
            logger.info("(后台)修改密码返回值,result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception ex) {
            logger.error("(后台)修改密码入参，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.UPDATE_PASSWORD_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateUser(User user) {
        try {
            logger.info("修改用户信息入参,user={}", JSON.toJSONString(user));
            //判断手机号是否重复
            User queryUser = userMapper.findUserByPhone(user.getTelphone());
            if (null != queryUser && !queryUser.getId().equals(user.getId())) {
                return ResultUtil.getResult(PermissionEnum.REGISTERED);
            }
            modifyUser(user);
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
        } catch (Exception ex) {
            logger.error("修改用户信息异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.UPDATE_USER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result frozenOrEnableUser(User user) {
        try {
            logger.info("冻结或者启用用户入参,user={}", JSON.toJSONString(user));
            user.setUpdateTime(new Date());
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
        } catch (Exception e) {
            //throw new PermissionBizException(PermissionEnum.DISABLE_ERROR);
            return null;
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleteUser(User user) {
        try {
            logger.info("删除用户入参,user={}", JSON.toJSONString(user));
            userRoleMapper.deleteUserRoleByUserId(user.getId());
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
        } catch (Exception ex) {
            logger.error("删除用户异常，ex={}", ex);
            //throw new PermissionBizException(PermissionEnum.DELETE_USER_ERROR);
            return null;
        }
    }

    @Override
    public Result getUserById(User user) {
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS, null);
    }

    @Override
    public Result getUserListByPage(PageBean page, UserDTO userDto) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<UserVO> list = userMapper.getUserByPage(userDto);
        PageInfo<UserVO> pageInfo = new PageInfo<>(list);
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Result getUserRoleByUserId(Long userId) {
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS, userRoleMapper.selectUserRoleByUserId(userId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateUserRole(UpdateUserRoleDTO updateUserRoleDto) {
        try {
            logger.info("修改用户对应的角色入参，updateUserRoleDto={}", JSON.toJSONString(updateUserRoleDto));
            userRoleMapper.deleteUserRoleByUserId(updateUserRoleDto.getUserId());
            userRoleMapper.insertUserRole(updateUserRoleDto.getUserId(), updateUserRoleDto.getRoleIds(), updateUserRoleDto.getCreaterId(), updateUserRoleDto.getUpdaterId());
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
        } catch (Exception e) {
            logger.error("修改用户对应的角色异常，ex={}", e);
            throw new PermissionBizException(PermissionEnum.UPDATE_USER_ROLE_ERROR);
        }

    }


    @Override
    public UserVO getUserByCondition(UserDTO userDto) {
        return userMapper.getUserByCondition(userDto);
    }

    @Override
    public Result checkUserByCondition(UserDTO userDto) {

        UserVO userVO = userMapper.getUserByCondition(userDto);
        if (userVO == null) {
            return ResultUtil.getResult(PermissionEnum.NO_TELPHONE_ERROR);
        } else {
            int num=2;
            if (userVO.getIsable() == num) {
                return ResultUtil.getResult(PermissionEnum.LOCKED_ACCOUNT);
            }

            if (userVO.getAgentName() != null) {
                return ResultUtil.getResult(RespCode.Code.SUCCESS);
            } else {
                return ResultUtil.getResult(PermissionEnum.NO_FIND_PASSWORD_ERROR);
            }
        }
    }

//=======================================私有通用方法===============================================/

    /**
     * @描述：修改用户
     * @param: user
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/18 9:47
     */
    private void modifyUser(User user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(user.getPassword());
        }
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * @描述：参数转换
     */
    public static final Byte START = 1;

    private void paramsTranf(User user, UserRoleDTO dto) {
        user.setUpdaterId(dto.getUpdaterId());
        user.setUserName(dto.getUserName());
        user.setTelphone(dto.getTelphone());
        user.setPassword(Md5.getMD5Str(dto.getPassword()));
        user.setIsable(START);
        user.setCreatedTime(new Date());
        user.setUpdateTime(new Date());

    }

    /**
     * @描述：除了管理员类的角色自动分配
     * @param: roleCode
     * @param: userId
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/18 9:49
     */
    private void addUserRole(Byte roleCode, Long userId) {
        //通过roleCode查询角色信息
        Role role = new Role();
        role.setRoleCode(roleCode);
        List<Role> list = roleMapper.selectRoleBySelective(role);
        Long roleId = null;
        if (CollectionUtils.isNotEmpty(list)) {
            roleId = list.get(0).getId();
        }
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        userRoleMapper.inserUserRole(userRole);
    }

    /**
     * @描述：生成随机数字和字母的密码
     * @param: length 密码长度
     * @return: String
     * @作者： Mr.Shu
     * @创建时间：2017/6/5 10:03
     */
    public String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
