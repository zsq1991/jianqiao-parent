package com.zc.mybatis.dao;


import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.permission.UserRole;
import com.zc.main.vo.SessionRoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @项目：phshopping-service-permission
 * @描述：
 * @author ： Mr.zheng
 * @创建时间：2017-03-14
 * @Copyright @2017 by Mr.zheng
 */
@MyBatisRepository
public interface UserRoleMapper  {

    /**
     * 通过用户id删除用户角色关系
     * @methodname deleteUserRoleByUserId 的描述：通过用户id删除用户角色关系
     * @param userId
     * @return int
     * @author Mr.Shu
     * @create 2017/5/17
     */
    int deleteUserRoleByUserId(@Param("userId") Long userId);


    /**
     * 通过用户id查询用户对应的角色id
     * @methodname selectUserRoleByUserId 的描述：通过用户id查询用户对应的角色id
     * @param userId
     * @return java.util.List<java.lang.Long>
     * @author Mr.Shu
     * @create 2017/5/17
     */
    List<Long> selectUserRoleByUserId(@Param("userId") Long userId);


    /**
     * 给用户赋予角色
     * @author Mr.Shu
     * @create 2017/5/17
     * @param userId
     * @param roleIds
     * @param createrId
     * @param updaterId
     * @return
     */
    int insertUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds, @Param("createrId") Long createrId, @Param("updaterId") Long updaterId);

    /**
     * 通过用户id查询role
     * @methodname selectRoleVOByUserId 的描述：通过用户id查询role
     * @param userId
     * @return java.util.List<com.ph.shopping.facade.permission.vo.SessionRoleVO>
     * @author Mr.Shu
     * @create 2017/5/17
     */
    List<SessionRoleVO> selectRoleVOByUserId(@Param("userId") Long userId);
    
    /**
     * 新增用户对应的角色
     * @methodname inserUserRole 的描述：新增用户对应的角色
     * @param userRole
     * @return int
     * @author 郑朋
     * @create 2017/4/24
     */
    int inserUserRole(UserRole userRole);


    /**
     * 查询角色用户
     * @methodname selectUserRole 的描述：查询角色用户
     * @param userRole
     * @return java.util.List<com.ph.shopping.facade.permission.entity.UserRole>
     * @author 郑朋
     * @create 2017/4/24
     */
    List<UserRole> selectUserRole(UserRole userRole);
}
