package com.zc.main.service.membermsg;

import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;

/**
 * @package : com.zc.main.service.membermsg
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : asus 王鑫涛
 * @Creation Date ：2018年01月18日10:40
 */
public interface MemberMsgService{
	/**
	 *
	 * @description 接口说明 读取通知信息的接口
	 * @author 王鑫涛
	 * @date 10:46 2018/1/18
	 * @version 版本号
	 * @param members 用户
	 * @return
	 */
	public Result getMemberMsgReadInform(Member members);
}
