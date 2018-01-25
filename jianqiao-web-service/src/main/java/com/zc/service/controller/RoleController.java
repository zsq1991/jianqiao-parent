package com.zc.service.controller;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.google.common.collect.Maps;
import com.zc.common.core.shiro.Result;
import com.zc.common.core.shiro.RoleEnum;
import com.zc.common.core.utils.CommonConstants;
import com.zc.common.core.utils.page.PageBean;
import com.zc.main.dto.GetMenuTreeDTO;
import com.zc.main.dto.RoleDTO;
import com.zc.main.dto.RoleMenuDTO;
import com.zc.main.dto.UserDTO;
import com.zc.main.dubbo.service.permission.ILoginService;
import com.zc.main.dubbo.service.permission.IRoleService;
import com.zc.main.dubbo.service.permission.IUserService;
import com.zc.main.entity.permission.Role;
import com.zc.main.entity.permission.User;
import com.zc.main.vo.MenuTreeVO;
import com.zc.main.vo.SessionUserVO;
import com.zc.main.vo.UserVO;
import com.zc.service.confige.MyShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("permission/role")
public class RoleController {
	
	
	@DubboConsumer(version="1.0.0")
	private IRoleService roleService;
	
	@DubboConsumer(version="1.0.0")
	private IUserService userService;
	
	@DubboConsumer(version="1.0.0")
	private ILoginService loginService;
	
	@Autowired
    MyShiroRealm myShiroRealm;
	
	@RequestMapping(value="/listPage",method=RequestMethod.GET)
    public String toListRolePage() {
        return "permission/role";
    }
	
	/**
     * @描述：跳转权限分配页面
     *
     * @param: roleId
     * @param: model
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 16:58
     */
	@RequestMapping(value="/permissionPage",method=RequestMethod.GET)
    public String toPermissionPage(Long roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "permission/role/roleMenu";
    }
	
	   //=================分配权限页面菜单树的获取与修改=========/

    /**
     * @描述：根据角色id查询分配权限页面的菜单树
     *
     * @param: roleId
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:00
     */
    @RequestMapping(value = "/getRoleMenu", method = RequestMethod.POST)
    @ResponseBody
    public Object getRoleMenuByRoleId(Long roleId) {
        Result result = roleService.getRoleMenuAndMenuBtnByRoleId(roleId);
        return result;
    }
	
	

	/**
     * @描述：修改分配权限页面角色菜单树
     *
     * @param: getMenuTreeDTO
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:02
     */
    @RequestMapping(value="/updateRoleMenu",method=RequestMethod.POST)
	@ResponseBody
    public Result updateRoleMenu(@RequestBody GetMenuTreeDTO getMenuTreeDTO) {

        //获取前端传来的json数据
        List<MenuTreeVO> menuTreeVOS = getMenuTreeDTO.getMenuTreeVOS();
        Long roleId = getMenuTreeDTO.getRoleId();

        //组装数据
        List<Long> listMenu = new ArrayList<>();
        Map<Long, List<Long>> menuBtns = Maps.newHashMap();
        if (menuTreeVOS != null && menuTreeVOS.size() != 0) {
            for (MenuTreeVO mtv : menuTreeVOS) {
                Long menuId = mtv.getId();
                Long btnId = mtv.getBtnId();
                Long pId = mtv.getpId();
                if (menuId != null && !"".equals(menuId)) {
                    listMenu.add(menuId);
                } else if (btnId != null && !"".equals(btnId)) {

                    if (menuBtns.containsKey(pId)) {
                        List<Long> listBtn = menuBtns.get(pId);
                        listBtn.add(btnId);
                    } else {
                        List<Long> listBtn = new ArrayList<>();
                        listBtn.add(btnId);
                        menuBtns.put(pId, listBtn);
                    }
                }
            }
        }

        //获取Session
        SessionUserVO sessionUserVO = getLoginUser();

        //修改角色菜单
        RoleMenuDTO roleMenuDto = new RoleMenuDTO();
        roleMenuDto.setRoleId(roleId);
        roleMenuDto.setMenuIds(listMenu);
        roleMenuDto.setCreaterId(sessionUserVO.getId());

        //记录日志
       Result result =  roleService.updateRoleMenuAndMenuBtn(roleMenuDto, menuBtns);
           
        return result;
    }


    //===========================================角色的增删改查，禁用与启用================================================/

    //==============禁用与启用===========/

   

    //==============增删改查===========/

    /**
     * @描述 分页获取角色列表
     * @param: page
     * @param: role
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:03
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@ModelAttribute PageBean page, @ModelAttribute RoleDTO role) {
        return roleService.getRoleListByPage(page, role);
    }

    /**
     * @描述：获取所有角色(管理员code)
     * @param: null
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/18 9:29
     */
    @RequestMapping(value = "/list/all", method = RequestMethod.GET)
    @ResponseBody
    public Object listAll() {
        //查询管理员类型的roleCode
        return roleService.getRoleList();
    }

    /**
     * @描述：新增角色
     * @param: role
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:05
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addRole(@ModelAttribute Role role) {
    	
    	 SessionUserVO sessionUserVO = getLoginUser();
         role.setCreaterId(sessionUserVO.getId());
         role.setUpdaterId(sessionUserVO.getId());
         role.setRoleCode(Byte.valueOf(RoleEnum.ADMIN.getCode() + ""));
        //记录日志
        Result result = roleService.addRole(role);
        return result;
    }

    /**
     * @描述：修改角色
     * @param: role
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:06
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateRole(@ModelAttribute Role role) {
        //记录日志
        Result result  = roleService.updateRole(role);
        return result;
    }

    /**
     * @描述：删除角色
     * @param: role
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:06
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteRole(@ModelAttribute Role role) {
        //记录日志
        Result result = roleService.deleteRole(role);
        return result;
    }


//======================================================私有逻辑处理======================================================/

    /**
     * @描述：通过SessionManage获取在线用户列表修改角色后提出拥有该角色的用户
     * @param: roleId
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/16 17:05
     */
    /*private void forceLogoutUsersByRoleId(Long roleId) {
        //处理session
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表

        List<UserVO> userVoList = roleService.getUsersByRoleId(roleId);
        for (UserVO u : userVoList) {
            for (Session session : sessions) {
                //清除该用户以前登录时保存的session
                if (u.getTelphone().equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
                    sessionManager.getSessionDAO().delete(session);
                }
            }
        }

    }*/

    
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
    
	

}
