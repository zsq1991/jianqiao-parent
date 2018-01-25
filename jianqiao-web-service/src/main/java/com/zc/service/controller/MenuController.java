package com.zc.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.shiro.Result;
import com.zc.main.dubbo.service.permission.IMenuService;
import com.zc.main.dubbo.service.permission.IUserService;

/**
 * @description 菜单
 * @author system
 * @date 2018-01-25 17:38
 * @version 1.0.0
 */
@RequestMapping("permission")
@Controller
public class MenuController {
	
	@DubboConsumer(version="1.0.0",timeout=50000)
	private IMenuService menuService;
	
	@DubboConsumer(version="1.0.0",timeout=50000)
	private IUserService userService;
	
	
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public String toListRolePage() {
        return "permission/user";
    }
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public Object getMenuByUserId(@RequestParam(value="userId",required=true)Long userId){
        Result result = menuService.getMenuTreeByUserId(userId);
        return result;
	}

	
	/**
	 * @描述：获取用户拥有的角色列表
	 */
	@RequestMapping(value = "/getUserRole", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserRole(Long userId) {
		return userService.getUserRoleByUserId(userId);
	}
}
