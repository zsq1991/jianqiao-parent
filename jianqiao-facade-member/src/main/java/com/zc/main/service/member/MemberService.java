package com.zc.main.service.member;

import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;

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

	Result getAuthMember(Member member);
}
