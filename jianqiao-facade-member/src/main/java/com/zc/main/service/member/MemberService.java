package com.zc.main.service.member;

import com.zc.main.entity.member.Member;

import java.util.Map;

/**
 * @description ： 用户操作
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/16 11:10
 * @version 1.0.0
 */
public interface MemberService {

	/**
	 * @description ：根据id来查询用户
	 * @Created by  : gaoge
	 * @Creation Date ： 2018/1/16 11:10
	 * @version 1.0.0
	 * @param :  memberId 用户id
	 * @return :
	 */
	Member getMerberById(Long memberId);

	/**
	 * 根据手机号和UUID查询用户
	 * @description 根据手机号和UUID查询用户
	 * @author whl
	 * @date 2018-01-16 12:00
	 * @version 1.0.0
	 * @param params 包含phone和uuid的Map
	 * @return
	 */
	Member getMemberByPhoneAndUuid(Map<String, Object> params);
}
