package com.zc.service.impl.comment;


import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.consultationcomment.ConsultationComment;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.membermsg.MemberMsg;
import com.zc.main.service.comment.ConsultationCommentService;
import com.zc.main.vo.consultationcomment.ConsultationCommentDTO;
import com.zc.mybatis.dao.ConsultationCommentMapper;
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
@Transactional(readOnly = true)
public class ConsultationCommentServiceImpl implements ConsultationCommentService {

    private static Logger logger = LoggerFactory.getLogger(ConsultationCommentService.class);
    @Autowired
    private ConsultationCommentMapper consultationCommentMapper;

    @Override
    @Transactional(readOnly = false)
    public Result saveReplyconsultationCommentService(Long memberid, Integer type, Long parentid, String content) {

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

           ConsultationCommentDTO parentConsultationCommentdb = consultationCommentMapper.getRowLock(parentid);
            if(parentConsultationCommentdb==null){
                return ResultUtils.returnError("该评论信息不存在");
            }
            if(parentConsultationCommentdb.getIsDelete()==1){
                return ResultUtils.returnError("该评论已删除，无法进行回复");
            }
            if(parentConsultationCommentdb.getConsultationId()==null){
                return ResultUtils.returnError("该评论信息异常，未关联资讯信息");
            }
           /* if(parentConsultationCommentdb.getConsultation().getIsDelete()==1) {
                return ResultUtils.returnError("该资讯已删除，无法进行回复");
            }*/
            ConsultationComment consultationComment = new ConsultationComment();
            consultationComment.setConsultationId(parentConsultationCommentdb.getConsultationId());
            //consultationComment.setHunter(parentConsultationCommentdb.getHunter());
            //consultationComment.setIndustryAssociation(parentConsultationCommentdb.getIndustryAssociation());
            consultationComment.setConsultationMemberId(parentConsultationCommentdb.getConsultationMemberId());
            Member member = new Member(); member.setId(memberid);//当前登入的用户，回复的用户
            consultationComment.setMemberId(member.getId());
            consultationComment.setContent(content);
            consultationComment.setIsDelete(0);
            if(type==0){
                //给顶级资讯评论者进行回复，  如果是给顶级咨询评论者回复是0，给咨询评论者回复的人进行回复是1
                consultationComment.setCommentInfoId(parentConsultationCommentdb.getCommentInfoId());
                consultationComment.setParentId(null);
                parentConsultationCommentdb.setReplyNum(parentConsultationCommentdb.getReplyNum()==null?1:parentConsultationCommentdb.getReplyNum()+1);
                //ConsultationComment newConsultationCommentdb = consultationCommentMapper.saveConsultationComment(consultationComment);//保存新增回复评论
                if(parentConsultationCommentdb.getFirstReplyCommentId()==null){
                    //parentConsultationCommentdb.setFirstReplyCommentId(newConsultationCommentdb.getFirstReplyCommentId());//维护此咨询最早的回复用于方便查询使用
                }

                //this.saveAndModify(parentConsultationCommentdb);//更新回复数量
//===========================维护MemberMsg系统通知=================================================================
                MemberMsg memberMsg = new MemberMsg();
                //memberMsg.setConsultationCommentId(newConsultationCommentdb.getFirstReplyCommentId());//保存新增的回复数据
                memberMsg.setMemberId(parentConsultationCommentdb.getMemberId());//被评论的资讯
                memberMsg.setCreatedTime(new Date());
                memberMsg.setUpdateTime(new Date());
                memberMsg.setMemberBaseId(member.getId());
                memberMsg.setType(4);
                memberMsg.setReadType(0);
                //memberMsgDao.save(memberMsg);

            }else{
                //顶级评论下的相互回复
                if(parentConsultationCommentdb.getCommentInfoId()==null){
                    return ResultUtils.returnError("无法回复，该评论记录异常未关联顶级评论信息");
                }
                ConsultationCommentDTO	topConsulattionCommentdb = consultationCommentMapper.getRowLock(parentConsultationCommentdb.getCommentInfoId());
                if(topConsulattionCommentdb==null){
                    return ResultUtils.returnError("资讯评论记录异常，顶级评论记录不存在");
                }
                if(topConsulattionCommentdb.getIsDelete()==1){
                    return ResultUtils.returnError("该资讯顶级评论者已删除评论内容，不能回复，请刷新页面");
                }
                consultationComment.setCommentInfoId(parentConsultationCommentdb.getCommentInfoId());
                consultationComment.setParentId(parentConsultationCommentdb.getParentId());
                topConsulattionCommentdb.setReplyNum(topConsulattionCommentdb.getReplyNum()==null?1:topConsulattionCommentdb.getReplyNum()+1);
                //ConsultationComment saveAndModify = this.saveAndModify(consultationComment);//保存新增回复评论
                //this.saveAndModify(topConsulattionCommentdb);//更新回复的数量
//===============================维护MemberMsg系统通知================================================================
                MemberMsg memberMsg = new MemberMsg();
                //memberMsg.setConsultationCommentId(saveAndModify.getId());//保存新增的回复数据
                memberMsg.setMemberId(topConsulattionCommentdb.getMemberId());//被评论的资讯
                memberMsg.setCreatedTime(new Date());
                memberMsg.setUpdateTime(new Date());
                memberMsg.setMemberBaseId(member.getId());
                memberMsg.setType(4);
                memberMsg.setReadType(0);
                //memberMsgDao.save(memberMsg);
            }
            if(!parentConsultationCommentdb.getMemberId().equals(memberid)){
                //添加到推送表中
                //memberConsultationMsgPoolService.addConsultationCommentPush(parentConsultationCommentdb.getMember(), parentConsultationCommentdb.getConsultation(), parentConsultationCommentdb, member, 1);
            }
            return ResultUtils.returnSuccess("回复成功");

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            logger.error("====回复资讯异常：用户ID："+memberid+"====="+e.getMessage());
            e.printStackTrace();
            return ResultUtils.returnError("回复失败");

        }
        //return rpcConsultationCommentService.saveReplyconsultationCommentService(memberid,type,parentid,content);
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
