package com.zc.main.dubbo.service.impl.permission;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.shiro.PermissionEnum;
import com.zc.common.core.shiro.Result;
import com.zc.common.core.shiro.ResultUtil;
import com.zc.main.dubbo.service.permission.IMenuService;
import com.zc.main.vo.MenuVO;
import com.zc.main.vo.RoleVO;
import com.zc.mybatis.dao.MenuMapper;
import com.zc.mybatis.dao.RoleMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述：菜单service
 */
@Component
@Service(version="1.0.0",interfaceClass=IMenuService.class,timeout=500000)
public class MenuServiceImpl implements IMenuService {

	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	@Override
    public Result getMenuTreeByUserId(Long userId) {
        try {
            List<RoleVO> list = roleMapper.getRoleListByUserId(userId);
            if (CollectionUtils.isEmpty(list)) {
				return ResultUtil.getResult(PermissionEnum.NO_PERMISSION);
			}
			List<Long> role = new ArrayList<>();
            for (RoleVO r : list) {
                role.add(r.getId());
			}
            List<MenuVO> menuVos = menuMapper.getMenuByRoleIds(role);
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS,getMenuTree(menuVos));
		} catch (Exception ex) {
			logger.error("通过用户id查询菜单异常，ex={}",ex);
		}
		return ResultUtil.getResult(PermissionEnum.Code.INTERNAL_SERVER_ERROR);
	}

    @Override
	public Result getMenuList() {
		return ResultUtil.getResult(PermissionEnum.Code.SUCCESS, menuMapper.getAllMenu());
	}

    @Override
    public List<MenuVO> getMenuByRoleIds(List<Long> roleIds) {
        return menuMapper.getMenuByRoleIds(roleIds);
    }

//===============================================私有通用方法================================================/

    /**
	 * 获取菜单树
	 * @param menuVos
	 * @return
	 */
    public static List<MenuVO> getMenuTree(List<MenuVO> menuVos) {
        List<MenuVO> treeList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(menuVos)) {
            for (MenuVO vo : menuVos) {
                if (null != vo.getParentId() && Long.valueOf(0).equals(vo.getParentId())) {
					vo = getChildMenu(vo,menuVos);
					treeList.add(vo);
				}
			}
		}
		return treeList;
	}

	private static MenuVO getChildMenu(MenuVO parent, List<MenuVO> menuVos) {
		List<MenuVO> child = null;
		for (MenuVO vo : menuVos) {
			if (parent.getId().equals(vo.getParentId())) {
				vo = getChildMenu(vo, menuVos);
				if (child == null) {
					child = new ArrayList<>();
				}
				child.add(vo);
			}
		}
		parent.setChild(child);
		return parent;
	}


}
