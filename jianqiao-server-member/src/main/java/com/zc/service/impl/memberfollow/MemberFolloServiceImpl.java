package com.zc.service.impl.memberfollow;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberfollow.MemberFollow;
import com.zc.main.service.member.MemberService;
import com.zc.main.service.memberfollow.MemberFollowService;
import com.zc.mybatis.dao.MemberFollowMapper;
import com.zc.service.impl.member.MemberServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * @author : wangxueyang[wxueyanghj@163.com]
 * @version ： 1.0.0
 * @package : com.zc.service.impl.memberfollow
 * @progect : jianqiao-parent
 * @Description : 用户相关信息  粉丝 关注
 * @Creation Date ：2018年01月16日18:11
 */
@Component
@Service(version = "1.0.0", interfaceClass = MemberFollowService.class)
public class MemberFolloServiceImpl implements MemberFollowService {

    private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberFollowMapper memberFollowMapper;

    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private MemberService memberService;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result focusMember(Long fId, Long mId) {
        logger.info("获取关注用户传入参数");
        Result result = new Result();
        if(mId==null && fId==null){
            return ResultUtils.returnError("请输入关注着的id");
        }else if(Objects.equals(fId, mId)){
            return ResultUtils.returnError("不能关注自己");
        }
        //检索是否存在用户身份
        Member fMember = memberService.checkMemberById(fId);
        if (fMember == null){
            logger.info("被关注者不存在!");
            return ResultUtils.returnError("您关注的用户不存在!");
        }
        Member member = memberService.checkMemberById(mId);
        if (member == null){
            logger.info("关注者不存在!");
            return ResultUtils.returnError("当前登录身份异常!");
        }
        MemberFollow memberFollowByData = memberFollowMapper.getMemberFollowByData(fId, mId);
        if(memberFollowByData != null){
            //关注或者取消关注,第一个参数是MemberFollow的id，fId时被关注者的id，mId是关注者的id
            try{
                MemberFollow memberFollow = memberFollowMapper.getMemberFollowById(memberFollowByData.getId());
                if (memberFollow != null){
                    Integer number = memberFollow.getIsDelete() == null?0:memberFollow.getIsDelete();
                    if(number==0){//修改为取消关注
                        //粉丝数量，被关注者
                        Long follwNum = fMember.getFollowingNum()==null?0:fMember.getFollowingNum();
                        fMember.setFollowingNum(follwNum-1);
                        fMember.setUpdateTime(new Date());
                        if (memberFollowMapper.updateNumById(fMember)>0){
                            logger.info("更新粉丝数成功！");
                        }
                        //注者数量。关注者
                        Long focusNum = member.getFocusNum()==null?0:member.getFocusNum();
                        member.setFocusNum(focusNum+1);
                        member.setUpdateTime(new Date());
                        if (memberFollowMapper.updateNumById(member)>0){
                            logger.info("更新关注数成功！");
                        }

                        memberFollow.setUpdateTime(new Date());
                        memberFollow.setIsDelete(1);//0或者null为关注，1是取消关注
                        if (memberFollowMapper.updateMemberFollowById(memberFollow)>0){
                            logger.info("更新关注记录成功！");
                        }
                        result.setCode(1);
                        result.setContent(0);//取消成功
                        result.setMsg("取消关注");
                    }else{//修改为关注

                        //粉丝数量，被关注者
                        fMember.setFollowingNum(fMember.getFollowingNum()+1);
                        fMember.setUpdateTime(new Date());

                        if (memberFollowMapper.updateNumById(fMember)>0){
                            logger.info("更新粉丝数成功！");
                        }
                        //减少关注者数量。关注者
                        Long focusNum = member.getFocusNum()==null?0:member.getFocusNum();
                        member.setFocusNum(focusNum+1);
                        member.setUpdateTime(new Date());
                        if (memberFollowMapper.updateNumById(member)>0){
                            logger.info("更新关注数成功！");
                        }

                        memberFollow.setUpdateTime(new Date());
                        memberFollow.setIsDelete(0);//0或者null为关注，1是取消关注
                        if (memberFollowMapper.updateMemberFollowById(memberFollow)>0){
                            logger.info("更新关注记录成功！");
                        }
                        MemberFollow dataById2 = memberFollowMapper.getDataById(mId, fId);
                        if ( dataById2 !=null) {
                            result.setContent(2);
                        }else{
                            result.setContent(1);
                        }
                        result.setCode(1);//0或者null为取消关注，1是关注
                        result.setMsg("关注成功");
                    }
                }
                return result;
            }catch (Exception e){
                logger.info("取消关注异常:"+e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
                return ResultUtils.returnError("取消关注用户失败");
            }
        }else{
            // 关注用户量
            if( fId ==null || mId == null){
                return ResultUtils.returnError("参数不能为空");
            }
            try {
                //粉丝数量，被关注者
                Long follwNum  = fMember.getFollowingNum()==null?0:fMember.getFollowingNum();
                fMember.setFollowingNum(follwNum+1);
                fMember.setUpdateTime(new Date());

                if (memberFollowMapper.updateNumById(fMember)>0){
                    logger.info("更新粉丝数成功！");
                }
                //注者数量。关注者
                Long focusNum = member.getFocusNum()==null?0:member.getFocusNum();
                member.setFocusNum(focusNum+1);
                member.setUpdateTime(new Date());
                if (memberFollowMapper.updateNumById(member)>0){
                    logger.info("更新关注数成功！");
                }
                MemberFollow follow = new MemberFollow();
                follow.setCreatedTime(new Date());
                follow.setMemberId(mId);//被关注者
                follow.setMemberFollowingId(fId);//关注者
                follow.setIsDelete(0);//0为关注1为取消关注
                follow.setUpdateTime(new Date());
                //检索是否已存在关注信息  如果没有直接添加  如果存在则更新 判断三个状态，此接口为0添加关注,1已关注,2相互关注
                MemberFollow dataById2 = memberFollowMapper.getDataById(mId, fId);
                if ( dataById2 !=null) {
                    logger.info("已存在关注记录，开始更新数据");
                    if (memberFollowMapper.updateMemberFollowById(follow)>0){
                        logger.info("更新关注记录成功！");
                    }
                    result.setContent(2);
                }else{
                    logger.info("不存在关注记录，开始添加数据");
                    if (memberFollowMapper.insertMemberFollow(follow)>0){
                        logger.info("添加关注记录成功!");
                    }
                    result.setContent(1);
                    result.setMsg("关注成功");
                }
            } catch (Exception e) {
                logger.info("关注异常："+e.getMessage());
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
                return ResultUtils.returnError("关注用户失败");
            }
            logger.info("关注成功");
            return result;
        }
    }

    @Override
    public Result getFansList(Long mId, int page, int size) {
        logger.info("获取粉丝列表传入参数==》mid:" + mId + " page:" + page + " size:" + size);
        Result result = new Result();
        try {
            if (mId == null) {
                return ResultUtils.returnError("请传入用户id");
            }
            List<Map> mIdList = memberFollowMapper.getMIdList(mId, page, size);
            if (mIdList.size() <= 0) {
                return ResultUtils.returnError("还没有粉丝");
            }
            List list = new ArrayList();
            for (int i = 0; mIdList.size() > i; i++) {
                String id = mIdList.get(i).get("member_following_id").toString();//关注者
                long longValue = Long.valueOf(id).longValue();
                //longValue被关注者id，mId是关注者id
                MemberFollow focusById = memberFollowMapper.getFocusById(longValue, mId);//第一个参数是被关注，第二个参数关注者
                Map fanList = memberFollowMapper.getFanList(longValue);
                Map title = memberFollowMapper.getConsulatationTitleByMId(longValue);
                String titles = title == null ? "" : (String) title.get("title");
                fanList.put("title", titles);
                if (focusById == null) {
                    fanList.put("focus", "加关注");
                    fanList.put("focusType", 1);
                } else {
                    fanList.put("focus", "互相关注");
                    fanList.put("focusType", 2);
                }
                list.add(fanList);
            }
            result.setContent(list);
            logger.info("获取粉丝列表成功");
        } catch (Exception e) {
            logger.info("获取粉丝列表异常:" + e.getMessage());
            e.printStackTrace();
            return ResultUtils.returnError("获取粉丝列表异常");
        }
        return result;
    }

    @Override
    public Result getFocusList(Long mId, int page, int size) {
        logger.info("获取会员关注列表传入参数 --> mId:" + mId + " page: " + page + " size: " + size);
        Result result = new Result();
        try {
            if (mId == null) {
                return ResultUtils.returnError("请传入用户id");
            }
            List<Map> mIdList = memberFollowMapper.getFocusMIdList(mId, page, size);
            if (mIdList.size() <= 0) {
                return ResultUtils.returnError("还没有关注者");
            }
            List list = new ArrayList();
            for (int i = 0; mIdList.size() > i; i++) {
                String id = mIdList.get(i).get("member_id").toString();//被关注者id
                long longValue = Long.valueOf(id).longValue();
                //longValue是关注者id，被关注id是mId
                MemberFollow focusById = memberFollowMapper.getFocusById(mId, longValue);//第一个参数是被关注，第二个参数关注者
                Map fanList = memberFollowMapper.getFanList(longValue);//通过Id获取昵称和头像地址
                Map title = memberFollowMapper.getConsulatationTitleByMId(longValue);
                String titles = title == null ? "" : (String) title.get("title");
                fanList.put("title", titles);
                if (focusById == null) {
                    fanList.put("focus", "已关注");
                    fanList.put("focusType", 1);
                } else {
                    fanList.put("focus", "互相关注");
                    fanList.put("focusType", 2);
                }
                list.add(fanList);
            }
            result.setContent(list);
            logger.info("查询会员关注列表成功！");
        } catch (Exception e) {
            logger.info("查询会员关注列表异常" + e.getMessage());
            e.printStackTrace();
            return ResultUtils.returnError("获取会员关注列表异常!");
        }
        return result;
    }
}
