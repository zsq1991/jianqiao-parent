package com.zc.mybatis.dao;

import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.member.Member;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Description :
 * @Created by : tenghui
 * @Creation Date ：2018年01月15日17:25
 */
@MyBatisRepository
public interface MemberMapper extends BasicMapper<Member> {
	/**
	 * @description 接口说明 获得用户信息通过id和uuid
	 * @author 王鑫涛
	 * @date 14:21 2018/1/16
	 * @version 版本号
	 * @param params
	 * @return
	 */
	Map<String,Object> getMemberByIdAndUuid(Map<String, Object> params);
    /**
     * 根据手机号和UUID查询用户
     * @description 根据手机号和UUID查询用户
     * @author whl
     * @date 2018-01-16 12:39
     * @version 1.0.0
     * @param params 包含id和uuid的Map
     * @return
     */
    Member getMemberByPhoneAndUuid(Map<String, Object> params);

	/**
	 * @description ：
	 * @Created by  : 朱军
	 * @version
	 * @Creation Date ： 2018/1/17 13:47
	 * @param memberId
	 * @return
	 */
	Member checkMemberById(Long memberId);

	/**
	 * @description ：根据手机号判断账户是否存在
	 * @Created by  : tenghui
	 * @Creation Date ： 2018/1/17 9:26
	 * @version : 1.0.0
	 */
	public Member getMemberByPhone(@Param("phone")String phone);
}
