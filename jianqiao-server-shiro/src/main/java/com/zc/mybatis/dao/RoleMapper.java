package com.zc.mybatis.dao;

import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.dto.RoleDTO;
import com.zc.main.entity.permission.Role;
import com.zc.main.vo.RoleVO;
import com.zc.main.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @描述： 角色mapper
 * @author system
 */
@MyBatisRepository
public interface RoleMapper{

	/**
     * @methodname getRoleListByUserId 的描述：根据用户id获取角色列表
     */
    List<RoleVO> getRoleListByUserId(@Param("userId") Long userId);

	/**
	 * @methodname getRoleByPage 的描述：分页查询角色信息
	 */
    List<RoleVO> getRoleByPage(RoleDTO roleDTO);

	/**
	 * @methodname getAllRole 的描述：查询所有的角色
	 */
    List<RoleVO> getAllRole();

	/**
	 * @methodname selectRoleBySelective 的描述：通过条件查询角色
	 */
	List<Role> selectRoleBySelective(Role role);

    /**
     * @param roleId
     * @return List<UserVO>
     * @methodname getUserListByRoleId 的描述：通过角色Id查询用户列表
     */
    List<UserVO> getUserListByRoleId(@Param("roleId") Long roleId);
    
    int insert(Role role);

	void updateByPrimaryKeySelective(Role role);

	void delete(Role role);
    
}
