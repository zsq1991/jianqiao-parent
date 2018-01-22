package com.zc.service.impl.membermsg;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.consultationattachment.ConsultationAttachment;
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

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @package : com.zc.main.service.membermsg
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : asus 王鑫涛
 * @Creation Date ：2018年01月18日10:40
 */
@Component
@Service(version = "1.0.0", interfaceClass = MemberMsgService.class)
@Transactional(readOnly = true)
public class MemberMsgServiceImpl implements MemberMsgService{

	private static Logger logger = LoggerFactory.getLogger(MemberMsgServiceImpl.class);

	@Autowired
	private MemberMsgMapper memberMsgMapper;


	@Override
	public int insert(MemberMsg memberMsg) {
		return memberMsgMapper.insert(memberMsg);
	}

	@Override
	public int save(MemberMsg memberMsg) {
		return memberMsgMapper.save(memberMsg);
	}
	/**
	 *
	 * @description 接口说明 读取通知信息的接口
	 * @author 王鑫涛
	 * @date 10:46 2018/1/18
	 * @version 版本号
	 * @param members 用户
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public Result getMemberMsgReadInform(Member members) {
		logger.info("==========================进入读取通知信息的接口======================");
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
				Long memberId=id;
				logger.info("====================获取总的数据======================");
				List<MemberMsg> rowLock = memberMsgMapper.getRowLockList(memberId);
				for (int i = 0; i < rowLock.size(); i++) {
					logger.info("=====================根据id获取系统消息=================");
					MemberMsg findOne = memberMsgMapper.findOne(rowLock.get(i).getId());
					findOne.setCountType(1);
					logger.info("=====================修改系统消息类型=================");
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
			logger.info("=====================进入读取通知信息的接口结束=================");
			return result;
		}

		//=========================================修改接口=============================================
		try {
			// 1审核通知，2赞通知，3评论通知
			if (type == 3 && id != null) {
				logger.info("===================================进入通知评论============================");
				Integer types = 4;
				Integer readType = 1;
				logger.info("=======================通过数据修改系统通知状态======================");
				int updateMemberMsgReadType = memberMsgMapper.updateMemberMsgReadType(types, readType, id);
				if (updateMemberMsgReadType>0) {
					result.setCode(1);
					result.setMsg("成功");
				}
			} else if (type == 2 && id != null) {
				logger.info("===================================进入2赞通知============================");
				Integer types = 5;
				Integer readType = 1;
				logger.info("=======================通过数据修改系统通知状态======================");
				int updateMemberMsgReadType = memberMsgMapper.updateMemberMsgReadType(types, readType, id);
				if (updateMemberMsgReadType >0) {
					result.setCode(1);
					result.setMsg("成功");
				}
			} else if (type == 1 && id != null && msgId != null && msgId != 0) {
				logger.info("===================================进入审核通知============================");
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
		logger.info("=====================进入读取通知信息的接口结束=================");
		return result;
	}

	/**
	 * @description 接口说明 阅读通知信息
	 * @author 王鑫涛
	 * @date 14:29 2018/1/18
	 * @version 版本号
	 * @param members
	 * @param msgId 只有当type为1时，msgId时必须填写，MemberMsg的id
	 * @param type 1审核通知，2赞通知，3评论通知
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public Result getReadInformList(Member members, Long msgId, Integer type) {
		logger.info("====================进入阅读通知信息方法=====================================");
		// TODO Auto-generated method stub
		Result result = new Result();
		if (members == null) {
			return ResultUtils.returnError("请先登录");
		} else if (type == null) {
			return ResultUtils.returnError("请输入选中的那个通知");
		}
		// 1审核通知，2赞通知，3评论通知
		Long mId = members.getId();
		//==========================================符合此条件的时候，读取阅读通知===============================
		if (mId != null && msgId==0 && type ==0) {
			try {
				Long memberId=mId;
				logger.info("--------------------------获取总的数据-------------------");
				List<MemberMsg> rowLock = memberMsgMapper.getRowLockList(memberId);
				for (int i = 0; i < rowLock.size(); i++) {
					logger.info("---------------------根据id获取系统消息==================");
					MemberMsg findOne = memberMsgMapper.findOne(rowLock.get(i).getId());
					findOne.setCountType(1);
					logger.info("===============修改通知类型=============");
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
			logger.info("====================阅读通知信息方法结束=====================================");
			return result;
		}

		//=========================================修改接口=============================================
		try {

			if (type == 3 && mId != null) {
				logger.info("=============进入评论通知====================");
				Integer types = 4;
				Integer readType = 1;
				logger.info("========================通过数据修改系统通知状态=======================");
				int updateMemberMsgReadType = memberMsgMapper.updateMemberMsgReadType(types, readType, mId);
				if (updateMemberMsgReadType>0) {
					result.setCode(1);
					result.setMsg("成功");
				}
			} else if (type == 2 && mId != null) {
				logger.info("=====================进入赞通知============================");
				Integer types = 5;
				Integer readType = 1;
				int updateMemberMsgReadType = memberMsgMapper.updateMemberMsgReadType(types, readType, mId);
				if (updateMemberMsgReadType >0) {
					result.setCode(1);
					result.setMsg("成功");
				}
			} else if (type == 1 && mId != null && msgId != null && msgId != 0) {
				logger.info("======================进入审核通知======================");
				MemberMsg findOne = memberMsgMapper.findOne(msgId);
				if (findOne == null) {
					return ResultUtils.returnError("没有此参数");
				}
				findOne.setReadType(1);
				int save = memberMsgMapper.updateById(findOne);
				if (save>0) {
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
		logger.info("====================阅读通知信息方法结束=====================================");
		return result;
	}
	/**
	 * 审核通知列表  系统消息==通知
	 * @author 王鑫涛
	 * @date 17:25 2018/1/19
	 * @version 版本号
	 * @param member
	 * @param page 当前页
	 * @param rows	每页显示的数量
	 * @param type 1审核通知，2赞通知，3评论通知
	 * @return
	 */
	@Override
	public Result getInformList(Member member, Integer page, Integer rows, Integer type) {
		logger.info("========进入系统消息======通知方法===================");
		Result result = new Result();
		// TODO Auto-generated method stub

		if (member == null) {
			return ResultUtils.returnError("请先登录");
		} else if (type == null) {
			return ResultUtils.returnError("请输入数据");
		}
		Long mId = member.getId();

		List list = new ArrayList();
		SimpleDateFormat chiddf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
		String chidNowTime = chiddf.format(new Date());
		// type 1审核通知，2赞通知，3评论通知
		int type1 = 1;
		int type2 = 2;
		int type3 = 3;
		try {
			if (type == type1) {
				logger.info("===========进入审核通知==============");
				List<Map> checkInformList = memberMsgMapper.getCheckInformList(mId, (page - 1) * rows, rows, type);
				// =============================================修改时间=========================================================
				for (Map map : checkInformList) {
					String date = (String) map.get("date");
					String chidTime1 = date.subSequence(0, 10).toString();
					String chidTime2 = chidNowTime.subSequence(0, 10).toString();
					if (chidTime1.equals(chidTime2)) {// 同一天
						String chidcreatedTime = date.subSequence(11, 16).toString();// 截取当天
						// 时，分
						map.put("date", chidcreatedTime);
					} else {// 不同一天
						String chidcreatedTime = date.subSequence(0, 10).toString();// 截取当天
						// 年，月，日
						map.put("date", chidcreatedTime);
					}
					list.add(map);
				}
				result.setCode(1);
				result.setContent(list);
				result.setMsg("成功");
			} else if (type == type2) {
				logger.info("---------------------进入赞通知-------------------------");
				List<Map> supportInformList = memberMsgMapper.getSupportInformList(mId, (page - 1) * rows, rows);
				if (supportInformList == null) {
					result.setCode(1);
					result.setMsg("没有人点赞");
				} else {
					for (Map maps : supportInformList) {
						Long conId = (Long) maps.get("conId");
						Long comId = (Long) maps.get("comId");
						Long memId = (Long) maps.get("memId");
						Long mLId = (Long) maps.get("mLId");
						Map map = new HashMap();
						if ((conId == null || conId == 0) && comId != null && comId != 0) {// 评论点赞
							logger.info("==================获取评论资讯赞====================");
							List<Map> mapsss = memberMsgMapper.getCommentSupportInformList(comId, memId, mLId);
							if (mapsss.size() == 0) {
								return ResultUtils.returnError("获取的数据有脏数据");
							}

							map = mapsss.get(0);
							map.put("result", 0);
							map.put("type", "赞了这条评论");
							map.put("content", "你的评论：" + map.get("content"));
							// =============================================修改时间=========================================================
							String date = (String) map.get("date");
							String chidTime1 = date.subSequence(0, 10).toString();
							String chidTime2 = chidNowTime.subSequence(0, 10).toString();
							if (chidTime1.equals(chidTime2)) {// 同一天
								String chidcreatedTime = date.subSequence(11, 16).toString();// 截取当天
								// 时，分
								map.put("date", chidcreatedTime);
							} else {// 不同一天
								String chidcreatedTime = date.subSequence(0, 10).toString();// 截取当天
								// 年，月，日
								map.put("date", chidcreatedTime);
							}
						} else if ((comId == null || comId == 0) && conId != null && conId != 0) {// 资讯点赞
							// ===============================================//0是访谈主题
							// 1访谈内容 2口述主题 3口述内容 4求助 5回答 6分享==========
							logger.info("-------------获取资讯赞-----------------");
							List<Map> mapss = memberMsgMapper.getConsulatationSupportInformList(conId, memId, mLId);
							if (mapss.size() == 0) {
								return ResultUtils.returnError("获取的数据有脏数据");
							}
							map = mapss.get(0);
							map.put("result", 1);
							String info = (Long) map.get("info") + "";// 获取类型
							String types = null;
							switch (info) {
								case "0":
									types = "赞了 你的访谈";
									break;
								case "1":
									types = "赞了你的文章";
									break;
								case "2":
									types = "赞了你的口述";
									break;
								case "3":
									types = "赞了你的文章";
									break;
								case "4":
									types = "赞了你的求助";
									break;
								case "5":
									types = "赞了你的回答";
									break;
								case "6":
									types = "赞了你的分享";
									break;
								default:
									break;
							}
							map.put("type", types);
							map.put("content", types.substring(2, types.length()) + ":" + map.get("content"));
							// =============================================修改时间=========================================================
							String date = (String) map.get("date");
							String chidTime1 = date.subSequence(0, 10).toString();
							String chidTime2 = chidNowTime.subSequence(0, 10).toString();
							if (chidTime1.equals(chidTime2)) {// 同一天
								String chidcreatedTime = date.subSequence(11, 16).toString();// 截取当天
								// 时，分
								map.put("date", chidcreatedTime);
							} else {// 不同一天
								String chidcreatedTime = date.subSequence(0, 10).toString();// 截取当天
								// 年，月，日
								map.put("date", chidcreatedTime);
							}
						}
						// =================================================添加到数组中==========================================================
						list.add(map);
					}
				}
				result.setCode(1);
				result.setContent(list);
				result.setMsg("成功");
			} else if (type == type3) {
				logger.info("==========================进入评论通知========================");
				logger.info("=====================获取人评论者列表的数据==================");
				List<Map> commentInform = memberMsgMapper.getCommentInformList(mId, (page - 1) * rows, rows);
				if (commentInform == null) {
					result.setCode(1);
					result.setMsg("没有人评论");
				} else {
					for (Map map2 : commentInform) {
						Long conId = (Long) map2.get("conId");
						Long comId = (Long) map2.get("comId");
						Long memId = (Long) map2.get("memId");
						Long mLId = (Long) map2.get("mLId");
						Map map = new HashMap();
						String message = (String) map2.get("message");
						if ((conId == null || conId == 0) && comId != null && comId != 0) {// 评论的回复
							logger.info("---------------获取评论回复--------------");
							List<Map> maps = memberMsgMapper.getCommentDiscussInformList(comId, memId, mLId);
							if (maps.size() == 0) {
								return ResultUtils.returnError("获取的数据有脏数据");
							}
							map = maps.get(0);
							map.put("result", 0);
							map.put("content", "回复了你：" + map.get("content"));// 新评论的内容
							if (map.get("message") == null) {
								// 通过资讯id获取关联的附件。conId
								map.get("conId");

							} else {
								map.put("message", "你的评论:" + map.get("message"));
							}

							// =============================================修改时间=========================================================
							String date = (String) map.get("date");
							String chidTime1 = date.subSequence(0, 10).toString();
							String chidTime2 = chidNowTime.subSequence(0, 10).toString();
							if (chidTime1.equals(chidTime2)) {// 同一天
								String chidcreatedTime = date.subSequence(11, 16).toString();// 截取当天
								// 时，分
								map.put("date", chidcreatedTime);
							} else {// 不同一天
								String chidcreatedTime = date.subSequence(0, 10).toString();// 截取当天
								// 年，月，日
								map.put("date", chidcreatedTime);
							}
						} else if (comId != null && comId != 0 && conId != null && conId != 0) {// 资讯的评论
							// 查询资讯id，通过资讯id判断是回答还是consulation_id,回答是没有title
							// ===============================================//0是访谈主题
							// 1访谈内容 2口述主题 3口述内容 4求助 5回答 6分享==========
							logger.info("------------------------获取资讯评论------------------------");
							List<Map> maps = memberMsgMapper.getConsulatationDiscussInformList(comId, conId, memId, mLId);
							if (maps.size() == 0) {
								return ResultUtils.returnError("获取的数据有脏数据");
							}
							map = maps.get(0);
							map.put("result", 1);
							String type22 = (Long) map.get("type") + "";// 获取类型
							String types = null;
							switch (type22) {
								case "0":
									types = " 你的访谈";
									break;
								case "1":
									types = "你的文章";
									break;
								case "2":
									types = "你的口述";
									break;
								case "3":
									types = "你的文章";
									break;
								case "4":
									types = "你的求助";
									break;
								case "5":
									types = "你的回答";
									break;
								case "6":
									types = "你的分享";
									break;
								default:
									break;
							}
							// =================================当message为空时是对回答的评论===================================================================================================//
							if (map.get("message") == null) {
								Long id = (Long) map.get("conId");
								logger.info("-------------------------通过资讯的id获取Attachment---------------------------");
								ConsultationAttachment attachmentByconId = memberMsgMapper.getAttachmentByconId(id);
								map.put("message", types + ":" + attachmentByconId.getDetailContent());
								map.put("conId", map.get("messageId"));
							} else {
								map.put("message", types + ":" + map.get("message"));
							}
							map.put("content", "评论了你：" + map.get("content"));

							// =============================================修改时间==================================================================================================//
							String date = (String) map.get("date");
							String chidTime1 = date.subSequence(0, 10).toString();
							String chidTime2 = chidNowTime.subSequence(0, 10).toString();
							if (chidTime1.equals(chidTime2)) {// 同一天
								String chidcreatedTime = date.subSequence(11, 16).toString();// 截取当天
								// 时，分
								map.put("date", chidcreatedTime);
							} else {// 不同一天
								String chidcreatedTime = date.subSequence(0, 10).toString();// 截取当天
								// 年，月，日
								map.put("date", chidcreatedTime);
							}

						} else if ((comId == null || comId == 0) && conId != null && conId != 0) {// 对求助的评论就是回答
							logger.info("-----------------------获取回答者的数据-------------------");
							List<Map> maps = memberMsgMapper.getReplyDiscussInformList(conId, memId, mLId);
							if (maps.size() == 0) {
								return ResultUtils.returnError("获取的数据有脏数据");
							}
							map = maps.get(0);
							map.put("result", 1);
							String type22 = (Long) map.get("type") + "";// 获取类型
							String types = null;
							switch (type22) {
								case "0":
									types = " 你的访谈";
									break;
								case "1":
									types = "你的文章";
									break;
								case "2":
									types = "你的口述";
									break;
								case "3":
									types = "你的文章";
									break;
								case "4":
									types = "你的求助";
									break;
								case "5":
									types = "你的回答";
									break;
								case "6":
									types = "你的分享";
									break;
								default:
									break;
							}
							// =================================当message为空时是对回答的评论，===================================================================================================//
							if (map.get("message") == null) {
								Long id = (Long) map.get("conId");
								logger.info("-------------------------通过资讯的id获取Attachment-----------------------");
								ConsultationAttachment attachmentByconId = memberMsgMapper.getAttachmentByconId(id);
								map.put("message", types + ":" + attachmentByconId.getDetailContent());
								map.put("result", 0);// result为0时是评论的回复，资讯的评论
								map.put("conId", map.get("messageId"));// 回答的的资讯id
								map.put("conType", 5);
								map.put("info", 0);// info为0时就是评论资讯
								map.put("content", "回答了你：" + map.get("content"));
							} else {
								map.put("message", types + ":" + map.get("message"));
								map.put("content", "评论了你：" + map.get("content"));
								map.put("info", 0);// info为0时就是评论资讯，1时是评论的回复

							}

							// =============================================修改时间==================================================================================================//
							String date = (String) map.get("date");
							String chidTime1 = date.subSequence(0, 10).toString();
							String chidTime2 = chidNowTime.subSequence(0, 10).toString();
							if (chidTime1.equals(chidTime2)) {// 同一天
								String chidcreatedTime = date.subSequence(11, 16).toString();// 截取当天
								// 时，分
								map.put("date", chidcreatedTime);
							} else {// 不同一天
								String chidcreatedTime = date.subSequence(0, 10).toString();// 截取当天
								// 年，月，日
								map.put("date", chidcreatedTime);
							}
						}
						// =================================================添加到数组中==========================================================
						list.add(map);
					}

				}
				result.setCode(1);
				result.setContent(list);
				result.setMsg("成功");
			} else {
				return ResultUtils.returnError("请输入type数据类型");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("审核通知列表：" + e);
			result.setCode(0);
			result.setMsg("获取审核列表数据异常");
		}
		logger.info("------------------------进入系统消息======通知方法==============================");
		return result;
	}
}
