package com.zc.mybatis.dao;

import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.dto.UserDTO;
import com.zc.main.entity.permission.User;
import com.zc.main.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @项目：phshopping-service-permission
 * @描述： 用户mapper
 * @作者： Mr.Shu
 * @创建时间：2017-5-17
 * @Copyright @2017 by Mr.Shu
 */
@MyBatisRepository
public interface UserMapper{

    /**
     * @methodname findUserByPhone 的描述：通过用户手机号查询用户登录信息
     * @param telphone
     * @return com.ph.shopping.facade.permission.vo.UserVO
     * @author Mr.Shu
     * @create 2017/5/17
     */
    User findUserByPhone(@Param("telphone") String telphone);

    
    /**
     * @methodname getUserByPage 的描述：分页获取所有用户
     * @param userDto
     * @return java.util.List<com.ph.shopping.facade.permission.vo.UserVO>
     * @author 郑朋
     * @create 2017/4/24
     */
    List<UserVO> getUserByPage(UserDTO userDto);

    /**
     * 根据条件查询用户
     * @param userDto
     * @return 用户对象
     * @author Mr.Shu
     * @createTime 2017年05月09日
     */
    UserVO getUserByCondition(UserDTO userDto);


	int insert(User user);


	void updateByPrimaryKey(User user);


	User selectByPrimaryKey(@Param("id")Long id);
}
