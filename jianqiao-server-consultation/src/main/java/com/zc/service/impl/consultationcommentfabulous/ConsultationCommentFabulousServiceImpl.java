package com.zc.service.impl.consultationcommentfabulous;


import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.consultationcomment.ConsultationComment;
import com.zc.main.entity.consultationcommentfabulous.ConsultationCommentFabulous;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.membermsg.MemberMsg;
import com.zc.main.service.comment.ConsultationCommentService;
import com.zc.main.service.consultationcommentfabulous.ConsultationCommentFabulousService;
import com.zc.mybatis.dao.ConsultationCommentFabulousMapper;
import com.zc.mybatis.dao.ConsultationCommentMapper;
import com.zc.mybatis.dao.MemberMapper;
import com.zc.mybatis.dao.MemberMsgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

@Component
@Service(version = "1.0.0", interfaceClass = ConsultationCommentFabulousService.class)
@Transactional(rollbackFor = Exception.class)
public class ConsultationCommentFabulousServiceImpl implements ConsultationCommentFabulousService {

    private static Logger logger = LoggerFactory.getLogger(ConsultationCommentFabulousServiceImpl.class);

    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private ConsultationCommentService consultationCommentService;

    @Autowired
    private ConsultationCommentMapper consultationCommentMapper;

    @Autowired
    private ConsultationCommentFabulousMapper consultationCommentFabulousMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberMsgMapper memberMsgMapper;

    @Override
    public Result saveConsultationCommentFabulous(Long commentid, Long memberid, Integer type) {
        // TODO Auto-generated method stub
        if (commentid == null || memberid == null || type == null) {
            return ResultUtils.returnError("参数错误,commentid不能为空");
        }
        if (type != 1 && type != 2) {
            return ResultUtils.returnError("点赞参数非法值错误");
        }
        if (consultationCommentService.findHasConsultationCommentById(commentid) == 0) {
            return ResultUtils.returnError("该咨询评论不存在或已删除，请刷新界面重试");
        }
        try {
            ConsultationComment consultationComment = new ConsultationComment();
            consultationComment.setId(commentid);
            ConsultationComment consultationCommentdb = consultationCommentMapper.findTById(consultationComment);
            if (consultationCommentdb == null) {
                return ResultUtils.returnError("该咨询评论记录不存在");
            }
            ConsultationCommentFabulous consultationCommentFabulousdb = consultationCommentFabulousMapper.getConsultationCommentFabulousByCommentIdAndMemberId(commentid, memberid);
            Member member = new Member();
            member.setId(memberid);
            if (consultationCommentFabulousdb == null) {//评论点赞
                if (type == 2) {
                    return ResultUtils.returnError("操作失败，未有点赞记录");
                }
                ConsultationCommentFabulous consultationCommentFabulous = new ConsultationCommentFabulous();
                consultationCommentFabulous.setMemberId(memberid);
                consultationCommentFabulous.setConsultationCommentId(commentid);
                consultationCommentFabulous.setType(type);
                consultationCommentFabulousMapper.insert(consultationCommentFabulous);
                if (consultationCommentdb.getMemberId().longValue() != memberid) {
                    //memberConsultationMsService.addMemberConsultationMsg(consultationCommentdb.getMember(), consultationCommentdb.getConsultation(), consultationCommentdb, member, 3);//添加到资讯msg表中
                }

            } else {//取消点赞
                consultationCommentFabulousdb.setType(type);
                consultationCommentFabulousMapper.updateById(consultationCommentFabulousdb);
            }
            Long fabulousnum = consultationCommentdb.getFabulousNum() == null ? 0L : consultationCommentdb.getFabulousNum();
            if (type == 1) {
                consultationCommentdb.setFabulousNum(fabulousnum + 1);
            } else {
                consultationCommentdb.setFabulousNum(fabulousnum - 1 < 0 ? 0 : fabulousnum - 1);
            }

            int saveAndModify = consultationCommentMapper.updateById(consultationCommentdb);
//@wudi=================================维护MemberMsg表进行点赞===========================================
            if(saveAndModify<=0){
                return ResultUtils.returnError("操作失败");
            }
            logger.info("点赞类型为type:" + type);
            if (type == 1) {
                //查询咨询的评论
                ConsultationComment consultationComment2 = new ConsultationComment();
                consultationComment2.setId(commentid);
                ConsultationComment findOne = consultationCommentMapper.findTById(consultationComment2);
                //查询评论的用户
                Member findOne2 = memberMapper.selectByPrimaryKey(memberid);
                //保存系统消息
                MemberMsg memberMsg = new MemberMsg();
                memberMsg.setConsultationCommentId(commentid);
                memberMsg.setReadType(0);
                memberMsg.setType(5);
                if (findOne != null) {
                    memberMsg.setMemberId(findOne.getMemberId());
                }
                memberMsg.setMemberBaseId(findOne2.getId());
                memberMsg.setCreatedTime(new Date());
                memberMsg.setUpdateTime(new Date());
                memberMsgMapper.insert(memberMsg);

            } else if (type == 2) {//取消点赞的时删除对应的memberMsg表格
                Integer types = 5;
                memberMsgMapper.deleteMemberMsgBycontentId(memberid, types, commentid);
            }
            return ResultUtils.returnSuccess(type == 1 ? "点赞成功" : "取消点赞");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.error("资讯评论回复点赞异常：commentid:" + commentid + "=memberid:" + memberid + "=" + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            return ResultUtils.returnError("失败");
        }
    }


}
