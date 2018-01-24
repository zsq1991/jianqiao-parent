package com.zc.service.confige;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zc.main.entity.permission.Btn;
import com.zc.main.entity.permission.Menu;
import com.zc.main.entity.permission.Role;
import com.zc.main.vo.BtnVO;
import com.zc.main.vo.MenuVO;
import com.zc.main.vo.RoleVO;

public class ConvertRoleListUtil {

    /**
     * 将角色List转换成Set<String>
     *
     * @param roles
     * @return
     */
    public static Set<String> convertRoleListToSet(List<RoleVO> roles) {
        Set<String> set = new HashSet<String>();
        for (RoleVO role : roles) {
            set.add(role.getRoleCode().toString());
        }
        return set;
    }

    /**
     * 将角色权限List转换成List<String>
     *
     * @param permissions
     */
    public static List<String> convertPermissionListToStringList(List<MenuVO> permissions) {
        List<String> list = new ArrayList<String>();
        //---这里用的菜单表，后面要改成菜单权限表和按钮权限表
        for (MenuVO permission : permissions) {

            String permissionName = permission.getMenuUrl();

            if (permissionName != null && !"".equals(permissionName.trim())) {
                list.add(permissionName);
            }
        }
        return list;
    }


    /**
     * 将按钮权限List转换成List<String>
     *
     * @param permissions
     *
     * @return
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/15 13:54
     */
    public static List<String> convertBtnPermissionListToStringList(List<BtnVO> permissions) {
        List<String> list = new ArrayList<String>();
        //---这里用的按钮表
        for (BtnVO permission : permissions) {

            String permissionName = permission.getBtnCode();

            if (permissionName != null && !"".equals(permissionName.trim())) {
                list.add(permissionName);
            }
        }
        return list;
    }
}
