package com.zc.service.impl.comment;


import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.consultation.Consultation;
import com.zc.main.entity.consultationcomment.ConsultationComment;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.membermsg.MemberMsg;
import com.zc.main.service.comment.ConsultationCommentService;
import com.zc.main.service.membermsg.MemberMsgService;
import com.zc.mybatis.dao.ConsultationCommentMapper;
import com.zc.mybatis.dao.ConsultationMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 咨询评论
 *
 * @author Administrator
 */
@Component
@Service(version = "1.0.0", interfaceClass = ConsultationCommentService.class)
@Transactional(readOnly = true,rollbackFor=Exception.class)
public class ConsultationCommentServiceImpl implements ConsultationCommentService {

    private static Logger logger = LoggerFactory.getLogger(ConsultationCommentService.class);
    @Autowired
    private ConsultationCommentMapper consultationCommentMapper;

    @Autowired
    private ConsultationMapper consultationMapper;

    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private MemberMsgService memberMsgService;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result saveDirectConsultationComment(Long memberid, Long consultationid, String content) {
        logger.info("评论咨询传入参数 --> memberid:"+memberid+" consultationid:"+consultationid+" content:"+content);
        if(consultationid==null){
            return ResultUtils.returnError("咨询传入异常");
        }
        if(!StringUtils.isNotBlank(content)){
            return ResultUtils.returnError("请填写评论内容");
        }
        if (content.length()>3000) {
            return ResultUtils.returnError("字数已达上限");
        }
        try {
            Consultation consultation = consultationMapper.getOne(consultationid);
            if (consultation == null || consultation.getIsDelete()==1){
                logger.info("该咨询"+ consultationid +"已删除");
                return ResultUtils.returnError("该咨询已删除，无法评论");
            }
            Integer consultationdbDelete =	consultation.getIsDelete()==null?0:consultation.getIsDelete();
            //开始保存咨询评论
            ConsultationComment consultationComment = new ConsultationComment();
            consultationComment.setConsultationMemberId(consultation.getMemberId());
            consultationComment.setConsultationId(consultationid);
            consultationComment.setMemberId(memberid);
            consultationComment.setContent(content);
            consultationComment.setIsDelete(0);
            consultationComment.setCommentInfoId(null);
            int i = consultationCommentMapper.insert(consultationComment);
            if (i>0){
                logger.info("保存咨询评论成功,开始更新咨询评论数量!");
            }
            //维护咨询评论数量
            consultation.setCommentNum(consultation.getCommentNum()==null?1:consultation.getCommentNum()+1);
//        维护MemberMsg系统通知通知类型  0高级用户审核通过  1认证驳回  2内容驳回 3内容通过4资讯评论通知5评论回复通知6收藏通知
            MemberMsg memberMsg = new MemberMsg();
            memberMsg.setCreatedTime(new Date());
            memberMsg.setUpdateTime(new Date());
            memberMsg.setConsultationId(consultationid);
            memberMsg.setConsultationCommentId(consultationComment.getId());//关联新的评论
            memberMsg.setMemberId(consultation.getMemberId());
            memberMsg.setType(4);
            memberMsg.setReadType(0);
            memberMsg.setMemberBaseId(memberid);
            if (memberMsgService.save(memberMsg)>0){
                logger.info("保存消息成功!");
            }
            logger.info("保存评论成功!");
            return ResultUtils.returnSuccess("评论成功");
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            logger.info("评论异常："+e.getMessage());
            return ResultUtils.returnSuccess("评论异常");
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result saveReplyconsultationCommentService(Long memberId, Integer type, Long parentId, String content) {

        logger.info("==========开始调用评论回复接口============参数( memberId："+memberId+"type:"+type+"parentId:"+parentId+"content:"+content);
        if(type==null){
            return ResultUtils.returnError("参数错误，type类型不能为空");
        }
        if(type!=0&&type!=1){
            return ResultUtils.returnError("参数错误，非法的type值");
        }
        if(!StringUtils.isNotBlank(content)){
            return ResultUtils.returnError("请填写回复内容");
        }
        if (content.length()>3000) {
            return ResultUtils.returnError("字数已达上限");
        }
        try {

            ConsultationComment parentConsultationCommentdb = consultationCommentMapper.getRowLock(parentId);
            if(parentConsultationCommentdb==null){
                return ResultUtils.returnError("该评论信息不存在");
            }
            if(parentConsultationCommentdb.getIsDelete()==1){
                return ResultUtils.returnError("该评论已删除，无法进行回复");
            }
            if(parentConsultationCommentdb.getConsultationId()==null){
                return ResultUtils.returnError("该评论信息异常，未关联资讯信息");
            }
            Consultation consultation = consultationMapper.getOne(parentConsultationCommentdb.getConsultationId());
            if(consultation ==null || consultation.getIsDelete()==1) {
                return ResultUtils.returnError("该资讯已删除，无法进行回复");
            }
            ConsultationComment consultationComment = new ConsultationComment();
            consultationComment.setConsultationId(parentConsultationCommentdb.getConsultationId());
            consultationComment.setConsultationMemberId(parentConsultationCommentdb.getConsultationMemberId());
            //当前登入的用户，回复的用户
            Member member = new Member(); member.setId(memberId);
            consultationComment.setMemberId(member.getId());
            consultationComment.setContent(content);
            consultationComment.setIsDelete(0);
            if(type==0){
                //给顶级资讯评论者进行回复，  如果是给顶级咨询评论者回复是0，给咨询评论者回复的人进行回复是1
                consultationComment.setCommentInfoId(parentConsultationCommentdb.getCommentInfoId());
                consultationComment.setParentId(null);
                parentConsultationCommentdb.setReplyNum(parentConsultationCommentdb.getReplyNum()==null?1:parentConsultationCommentdb.getReplyNum()+1);
                consultationCommentMapper.insert(consultationComment);//保存新增回复评论
                if(parentConsultationCommentdb.getFirstReplyCommentId()==null){//???
                    parentConsultationCommentdb.setFirstReplyCommentId(consultationComment.getId());//维护此咨询最早的回复用于方便查询使用
                }
                //更新回复数量
                consultationCommentMapper.insert(parentConsultationCommentdb);

                //维护MemberMsg系统通知
                MemberMsg memberMsg = new MemberMsg();
                memberMsg.setMemberId(parentConsultationCommentdb.getMemberId());//被评论的资讯
                memberMsg.setCreatedTime(new Date());
                memberMsg.setUpdateTime(new Date());
                memberMsg.setMemberBaseId(member.getId());
                memberMsg.setType(4);
                memberMsg.setReadType(0);
                memberMsgService.insert(memberMsg);

            }else{
                //顶级评论下的相互回复
                if(parentConsultationCommentdb.getCommentInfoId()==null){
                    return ResultUtils.returnError("无法回复，该评论记录异常未关联顶级评论信息");
                }
                ConsultationComment	topConsulattionCommentdb = consultationCommentMapper.getRowLock(parentConsultationCommentdb.getCommentInfoId());
                if(topConsulattionCommentdb==null){
                    return ResultUtils.returnError("资讯评论记录异常，顶级评论记录不存在");
                }
                if(topConsulattionCommentdb.getIsDelete()==1){
                    return ResultUtils.returnError("该资讯顶级评论者已删除评论内容，不能回复，请刷新页面");
                }
                consultationComment.setCommentInfoId(parentConsultationCommentdb.getCommentInfoId());
                consultationComment.setParentId(parentConsultationCommentdb.getParentId());
                topConsulattionCommentdb.setReplyNum(topConsulattionCommentdb.getReplyNum()==null?1:topConsulattionCommentdb.getReplyNum()+1);
                this.consultationCommentMapper.insert(consultationComment);//保存新增回复评论
                this.consultationCommentMapper.insert(topConsulattionCommentdb);//更新回复的数量\

                //维护MemberMsg系统通知
                MemberMsg memberMsg = new MemberMsg();
                memberMsg.setMemberId(topConsulattionCommentdb.getMemberId());//被评论的资讯
                memberMsg.setCreatedTime(new Date());
                memberMsg.setUpdateTime(new Date());
                memberMsg.setMemberBaseId(member.getId());
                memberMsg.setType(4);
                memberMsg.setReadType(0);
                memberMsgService.insert(memberMsg);
            }
            if(!parentConsultationCommentdb.getMemberId().equals(memberId)){
                //添加到推送表中
            }
            logger.info("==========调用评论回复接口结束============参数( memberId："+memberId+"type:"+type+"parentId:"+parentId+"content:"+content);
            return ResultUtils.returnSuccess("回复成功");
        } catch (Exception e) {
            //回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("====回复资讯异常：用户ID："+memberId+"====="+e.getMessage());
            e.printStackTrace();
            return ResultUtils.returnError("回复失败");

        }
    }

    @Override
    public int findHasConsultationCommentById(Long commentid) {
        return consultationCommentMapper.findHasConsultationCommentById(commentid);
    }

    /**
     * 评论列表,getCommentList获取到的时顶级评论，firt_reply_comment是第一评论的id
     * @author huangxin
     * @data 2018/1/18 16:15
     * @Description: 评论列表,getCommentList获取到的时顶级评论，firt_reply_comment是第一评论的id
     * @Version: 3.2.0
     * @param paramMap
     * @return
     */
    @Override
    public List<Map<String, Object>> getCommentList(Map<String, Object> paramMap) {
        return consultationCommentMapper.getCommentList(paramMap);
    }

    /**
     * 通过父类id获取子的2个id
     * @author huangxin
     * @data 2018/1/18 16:16
     * @Description: 通过父类id获取子的2个id
     * @Version: 3.2.0
     * @param id 资讯id
     * @return
     */
    @Override
    public List<Map> getCommentSonIdByPid(Long id) {
        return consultationCommentMapper.getCommentSonIdByPid(id);
    }

    /**
     * 查询评论
     * @author huangxin
     * @data 2018/1/18 16:19
     * @Description: 查询评论
     * @Version: 3.2.0
     * @param id 资讯id
     * @return
     */
    @Override
    public Map<String, Object> getSonCommentList(Long id) {
        return consultationCommentMapper.getSonCommentList(id);
    }
}
