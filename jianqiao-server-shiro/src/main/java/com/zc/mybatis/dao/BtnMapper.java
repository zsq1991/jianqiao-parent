package com.zc.mybatis.dao;

import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.vo.BtnVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
@MyBatisRepository
public interface BtnMapper  {

    /**
     * @param menuIds
     * @param roleId
     * @return java.util.List<com.ph.shopping.facade.permission.vo.MenuVO>
     * @methodname getBtnListByMenuId 的描述：根据角色Id和菜单id获取已分配（已选择）按钮
     * @author Mr.Shu
     * @create 2017/5/13
     */
    List<BtnVO> getBtnListByMenuIdsAndRoleId(@Param("menuIds") List<Long> menuIds, @Param("roleId") Long roleId);

    /**
     * @param menuId
     * @return java.util.List<com.ph.shopping.facade.permission.vo.BtnVO>
     * @methodname getAllBaseBtnByMenuId 的描述：通过菜单id查询该菜单的基础按钮
     * @author Mr.Shu
     * @create 2017/5/12
     */
    List<BtnVO> getAllBaseBtnByMenuId(@Param("menuId") Long menuId);

    /**
     * @param
     * @return java.util.List<com.ph.shopping.facade.permission.vo.BtnVO>
     * @methodname getAllBtn 的描述：查询所有按钮
     * @author Mr.Shu
     * @create 2017/5/13
     */
    List<BtnVO> getAllBtn();

}
