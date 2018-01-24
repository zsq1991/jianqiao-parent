package com.zc.service.controller;

import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.shiro.PermissionEnum;
import com.zc.common.core.shiro.Result;
import com.zc.common.core.shiro.ResultUtil;
import com.zc.common.core.shiro.RoleEnum;
import com.zc.common.core.utils.CommonConstants;
import com.zc.common.core.utils.page.PageBean;
import com.zc.main.dto.UpdatePassDTO;
import com.zc.main.dto.UpdateUserRoleDTO;
import com.zc.main.dto.UserDTO;
import com.zc.main.dto.UserRoleDTO;
import com.zc.main.dubbo.service.permission.ILoginService;
import com.zc.main.dubbo.service.permission.IUserService;
import com.zc.main.entity.permission.User;
import com.zc.main.vo.SessionUserVO;
import com.zc.main.vo.UserVO;
import com.zc.service.confige.MyShiroRealm;


@Controller
@RequestMapping("permission/user")
public class UserController {
	
	
	@DubboConsumer(version="1.0.0")
	private IUserService userService;
	
	
	@DubboConsumer(version="1.0.0")
	private ILoginService loginService;
	
	@Autowired
	MyShiroRealm myShiroRealm;
	
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public String toListRolePage() {
        return "permission/user";
    }
	
	/**
	 * @描述：分页获取列表
	 * @param: page
	 * @param: userDto
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserListByPage(@ModelAttribute PageBean page, @ModelAttribute UserDTO userDto) {
		return userService.getUserListByPage(page, userDto);
	}
	
	
	/**
	 * @描述：新增用户
	 */
	@RequestMapping(value="/add",method= RequestMethod.POST)
	@ResponseBody
	public Object addUser(@ModelAttribute UserRoleDTO userDto) {
		SessionUserVO sessionUserVO = getLoginUser();
		userDto.setCreaterId(sessionUserVO.getId());
		userDto.setUpdaterId(sessionUserVO.getId());
		userDto.setRoleCode(RoleEnum.ADMIN.getCode());
		//后台用户添加，默认密码为123456
		userDto.setPassword("123456");
		//记录日志
		Result result  = userService.addUser(userDto);
		return result;
	}

	/**
	 * @描述：修改用户
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@ModelAttribute User user) {
		//记录日志
		Result result = userService.updateUser(user);
		return result;
	}

	/**
	 * @描述：删除用户
	 *
	 * @param: null
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/17 17:37
	 */
	@RequestMapping(value="/delete",method= RequestMethod.POST)
	@ResponseBody
	public Object deleteUser(@ModelAttribute User user) {
		return userService.deleteUser(user);
	}

	
	 
    public SessionUserVO getLoginUser() {

		//获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		SessionUserVO info = (SessionUserVO) currentUser.getSession().getAttribute(
				CommonConstants.LOGIN_BACK_USER_SESSION);

		//如果是记住我，处理Session失效
		if (info == null) {
            Object telPhone = currentUser.getPrincipal();
            if (telPhone != null) {
                boolean isRemembered = currentUser.isRemembered();
                if (isRemembered) {
                    //清除权限缓存
                    myShiroRealm.getAuthorizationCache().remove(currentUser.getPrincipals());
                    UserDTO userDTO = new UserDTO();
                    userDTO.setTelphone(telPhone.toString());
					UserVO userVO = userService.getUserByCondition(userDTO);
					//对密码进行加密后验证
					UsernamePasswordToken token = new UsernamePasswordToken(userVO.getTelphone(), userVO.getPassword(), currentUser.isRemembered());
					//把当前用户放入session
                    currentUser.login(token);
                    Session session = currentUser.getSession();
					User user = new User();
					user.setTelphone(userVO.getTelphone());
					user.setPassword(userVO.getPassword());
					Result result = loginService.login(user);
                    session.setAttribute(CommonConstants.LOGIN_BACK_USER_SESSION, result.getData());
                    info = (SessionUserVO) result.getData();
                }
            }

        }
		return info;

	}
    
    
    @RequestMapping(value = "/getUserRole", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserRole(Long userId) {
		return userService.getUserRoleByUserId(userId);
	}

    
    @RequestMapping(value = "/updateUserPermission", method = RequestMethod.POST)
	@ResponseBody
	public Object updateUserRole(@ModelAttribute UpdateUserRoleDTO ud) {
		//判断角色是否选择
		if (ud.getRoleIds() == null || ud.getRoleIds().size() == 0) {
			return new Result(false, PermissionEnum.ROLE_NOT_NULL_ERROR.getCode(),
					PermissionEnum.ROLE_NOT_NULL_ERROR.getMsg(), "[]", 0);
		}
		SessionUserVO sessionUserVO = getLoginUser();
		ud.setCreaterId(sessionUserVO.getId());
		ud.setUpdaterId(sessionUserVO.getId());

		//记录日志
		Result result =  userService.updateUserRole(ud);
			//踢出该用户
			if (result.isSuccess()) {
				forceLogoutUsersByUserId(ud.getTelPhone());
			}
		return result;
	}
    
    public void forceLogoutUsersByUserId(String telPhone) {
		//处理session
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
		//获取当前已登录的用户session列表
		Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
		if (sessions != null && sessions.size() != 0) {
			for (Session session : sessions) {
				//清除该用户以前登录时保存的session
				if (telPhone.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
					sessionManager.getSessionDAO().delete(session);
				}
			}
		}
	}
    
    
    /**
	 * @描述：后台用户修改密码
	 *
	 * @param: updatePassDto
	 */
	@RequestMapping(value="/updatePsw",method= RequestMethod.POST)
	@ResponseBody
	public Object updatePsw(UpdatePassDTO updatePassDto) {
		Result result = ResultUtil.getResult(PermissionEnum.Code.FAIL);
			if (null != updatePassDto) {
				SessionUserVO sessionUserVO = getLoginUser();
				updatePassDto.setId(sessionUserVO.getId());
				//记录日志
				result   = userService.updatePass(updatePassDto);
			}
		return result;
	}
}
