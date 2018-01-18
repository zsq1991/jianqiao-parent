package com.zc.service.impl.membermsg;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.membermsg.MemberMsg;
import com.zc.main.service.membermsg.MemberMsgService;
import com.zc.mybatis.dao.MemberMsgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;


/**
 * @package : com.zc.main.service.membermsg
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : asus 王鑫涛
 * @Creation Date ：2018年01月18日10:40
 */
@Component
@Service(version = "1.0.0", interfaceClass = MemberMsgService.class)
@Transactional
public class MemberMsgServiceImpl implements MemberMsgService{

	private static Logger logger = LoggerFactory.getLogger(MemberMsgServiceImpl.class);

	@Autowired
	private MemberMsgMapper memberMsgMapper;


	@Override
	public Result getMemberMsgReadInform(Member members) {
		Result result = new Result();
		if (members == null) {
			return ResultUtils.returnError("请先登录");
		}
		Long id = members.getId();
		int type = 0;
		Long msgId = 0L;
		// 1审核通知，2赞通知，3评论通知
		//==========================================符合此条件的时候，读取阅读通知===============================
		if (id != null && msgId==0 && type ==0) {
			try {
				Long member_id=id;
				List<MemberMsg> rowLock = memberMsgMapper.getRowLockList(member_id);
				for (int i = 0; i < rowLock.size(); i++) {

					MemberMsg findOne = memberMsgMapper.findOne(rowLock.get(i).getId());
					findOne.setCountType(1);
					memberMsgMapper.updateById(findOne);
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.info("数据异常："+e);
				TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
				return ResultUtils.returnError("修改数据异常");
			}
			result.setCode(1);
			result.setMsg("修改成功");
			return result;
		}

		//=========================================修改接口=============================================
		try {

			if (type == 3 && id != null) {
				Integer types = 4;
				Integer readType = 1;
				int updateMemberMsgReadType = memberMsgMapper.updateMemberMsgReadType(types, readType, id);
				if (updateMemberMsgReadType>0) {
					result.setCode(1);
					result.setMsg("成功");
				}
			} else if (type == 2 && id != null) {
				Integer types = 5;
				Integer readType = 1;
				int updateMemberMsgReadType = memberMsgMapper.updateMemberMsgReadType(types, readType, id);
				if (updateMemberMsgReadType >0) {
					result.setCode(1);
					result.setMsg("成功");
				}
			} else if (type == 1 && id != null && msgId != null && msgId != 0) {
				MemberMsg findOne = memberMsgMapper.findOne(msgId);
				if (findOne == null) {
					return ResultUtils.returnError("没有此参数");
				}
				findOne.setReadType(1);
				int i = memberMsgMapper.updateById(findOne);
				if (i>0) {
					result.setCode(1);
					result.setMsg("成功");
				}

			} else {
				result.setCode(0);
				result.setMsg("参数数据有误");

			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("修改memberMsg系统通知状态：" + e);
			result.setCode(0);
			result.setMsg("修改数据异常");
		}
		return result;
	}
}
