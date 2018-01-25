package com.zc.main.service.membermsg;

import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.membermsg.MemberMsg;

/**
 * @package : com.zc.main.service.membermsg
 * @progect : jianqiao-parent
 * @Description :
 * @author  by : asus 王鑫涛
 * @Creation Date ：2018年01月18日10:40
 */
public interface MemberMsgService{
	/**
	 * @description 接口说明 添加用户消息
	 * @author 王鑫涛
	 * @date 17:01 2018/1/18
	 * @version 版本号
	 * @param memberMsg  系统消息
	 * @return
	 */
	int insert(MemberMsg memberMsg);

	/**
	 * * @author:  wangxueyang[wxueyanghj@163.com]
	 * @create:  2018/1/19 11:27
	 * @desc: 保存信息
	 * @version 1.0.0
	 * @param memberMsg
	 * @return
	 */
	int save(MemberMsg memberMsg);

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
	/**
	 *
	 * @description 接口说明 阅读通知信息
	 * @author 王鑫涛
	 * @date 17:25 2018/1/19
	 * @version 版本号
	 * @param members 用户
	 * @param msgId 只有当type为1时，msgId时必须填写，MemberMsg的id
	 * @param type 1审核通知，2赞通知，3评论通知
	 * @return
	 */
	public Result getReadInformList(Member members,Long msgId,Integer type);
	/**
	 *
	 * @description 接口说明 审核通知列表
	 * @author 王鑫涛
	 * @date 17:25 2018/1/19
	 * @version 版本号
	 * @param member 用户
	 * @param page 当前页
	 * @param rows 每页显示的数量
	 * @param type 1审核通知，2赞通知，3评论通知
	 * @return
	 */
	public Result getInformList(Member member,Integer page,Integer rows,Integer type);
}
