package com.zc.main.service.member;

import com.zc.common.core.result.Result;
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
	 * @description 接口说明 修改收藏内容数量
	 * @author 王鑫涛
	 * @date 8:28 2018/1/18
	 * @version 版本号
	 * @param member 用户
	 * @return
	 */
	int updateByConNum(Long id,Long num);
	/**
	 * @description 接口说明 修改用户信息
	 * @author 王鑫涛
	 * @date 17:14 2018/1/17
	 * @version 版本号
	 * @param member 用户
	 * @return
	 */
	int updateById(Member member);
	/**
	 * @description 接口说明 根据id获取用户信息
	 * @author 王鑫涛
	 * @date 17:08 2018/1/17
	 * @version 版本号
	 * @param id 用户id
	 * @return
	 */
	Member findOne(Long id);
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

	/**
	 * @description 接口说明 获取用户认证
	 * @author 王鑫涛
	 * @date 12:04 2018/1/19
	 * @version 版本号
	 * @param member 用户
	 * @return
	 */
	Result getAuthMember(Member member);

	/**
	 * @description ：检测是否是高级用户
	 * @Created by  : 朱军
	 * @version
	 * @Creation Date ： 2018/1/17 13:45
	 * @param memberId
	 * @return
	 */
	Member checkMemberById(Long memberId);

    Member getMemberByIdAndUuid(Map<String, Object> params);

	/**
	 * 用户认证
	 * @author huangxin
	 * @data 2018/1/19 16:35
	 * @Description: 用户认证
	 * @Version: 3.2.0
	 * @param name 姓名
	 * @param card 身份证号
	 * @param phone 手机号
	 * @param member 用户信息
	 * @param code 验证码
	 * @param pics 附件ID ,多个ID用,拼接
	 * @param type 修改认证信息时传入此参数
	 * @return
	 */
    Result authMember(String name, String card, String phone, String code, String pics, Member member, String type);
}
