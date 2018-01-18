package com.zc.service.impl.consultationfabulous;


import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.consultation.Consultation;
import com.zc.main.entity.consultationfabulous.ConsultationFabulous;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.membermsg.MemberMsg;
import com.zc.main.service.consultationfabulous.ConsultationFabulousService;
import com.zc.mybatis.dao.ConsultationMapper;
import com.zc.mybatis.dao.ConsultationfabulousMapper;
import com.zc.mybatis.dao.MemberMsgMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Service(version = "1.0.0",interfaceClass = ConsultationFabulousService.class)
@Transactional(readOnly = true)
public class ConsultationFabulousServiceImpl implements ConsultationFabulousService {
    private static Logger logger = LoggerFactory.getLogger(ConsultationFabulousServiceImpl.class);

    @Autowired
    private ConsultationfabulousMapper consultationfabulousMapper;
    @Autowired
    private ConsultationMapper consultationMapper;
    @Autowired
    private MemberMsgMapper memberMsgMapper;

    //@Autowired
    //private PowerService powerService;

    /**
     * @param id       咨询id
     * @param memberId 用户id
     * @param type     点赞 1   取消赞2
     * @return
     * @description:对4种内容进行点赞
     * @author: ZhaoJunBiao
     * @date: 2018/1/16 13:51
     * @version: 1.0.0
     */
    @Override
    @Transactional
    public Result getConsultationFabulousByIdAndMemberId(Long id, Long memberId, Integer type) {
        logger.info("咨询内容点赞取消赞开始执行，入参参数{}" + "咨询内容id:" + id + "点赞1取消2:" + type + "用户id:" + memberId);
        //Power power = new Power();
        Consultation consultationDTO = new Consultation();

        if (StringUtils.isBlank(type + "")) {
            return ResultUtils.returnError("参数异常，当前操作有误!!");
        }

        if (type != 1 && type != 2) {
            return ResultUtils.returnError("参数异常，当前操作有误!!");
        }
        consultationDTO.setId(id);
        //获取咨询详细数据
        Consultation consultation = consultationMapper.findTById(consultationDTO);
        if (null == consultation) {
            return ResultUtils.returnError("当前咨询不存在!");
        }
        //获取咨询内容的点赞状态
        ConsultationFabulous consultationFabulous = consultationfabulousMapper.getConsultationFabulousByConsultationIdAndMemberId(id, memberId);
        //添加点赞记录
        Member member = new Member();
        if (null == consultationFabulous) {
            if (type == 2) {
                return ResultUtils.returnError("操作失败，未有点赞记录");
            }
            ConsultationFabulous consultationFabulous1 = new ConsultationFabulous();
            consultationFabulous1.setCreatedTime(new Date());
            consultationFabulous1.setMemberId(memberId);
            consultationFabulous1.setConsultationId(consultation.getId());
            consultationFabulous1.setType(type);
            consultationfabulousMapper.insert(consultationFabulous1);
        } else {
            Integer type2 = consultationFabulous.getType();
            if (type2.equals(type)) {
                return ResultUtils.returnError("当前操作有误!");
            }
            consultationFabulous.setType(type);
            consultationfabulousMapper.insert(consultationFabulous);
        }
        Long fabulousnum = consultation.getFabulousNum() == null ? 0L : consultation.getFabulousNum();
        //点赞时维护MemberMsg表或者取消点赞删除MemberMsg表
        if (type == 1) {
            MemberMsg msg = new MemberMsg();
            msg.setMemberId(consultation.getMemberId());
            msg.setType(5);
            msg.setContentType(consultation.getType());
            msg.setConsultationId(consultation.getId());
            msg.setMemberBaseId(member.getId());//点赞者
            msg.setCreatedTime(new Date());
            memberMsgMapper.insert(msg);
            consultation.setFabulousNum(fabulousnum + 1);

            /**
             * 因为需求原因,将三期需求的权重值增加注释掉
             * @wjt
             */
            //点赞一次权重值加5
            //consultation.setPower(powerNum + 5);
            //权重值维护详情列表
            /*power.setConsultation(consultation);
			power.setMember(member);
			power.setCreatedTime(new Date());
			power.setStatus(0);
			power.setType(1);
			powerService.saveAndModify(power);*/

        } else {
            //取消点赞时删除memberMsg
            Integer types = 5;
            logger.info("取消点赞获取的参数：" + memberId + "====" + types + "=====" + id + "+++++++");
            memberMsgMapper.deleteMemberMsgByConsulatationId(memberId, types, id);

            consultation.setFabulousNum(fabulousnum - 1 < 0 ? 0 : fabulousnum - 1);
            /**
             * 因为需求原因,将三期需求的权重值增加注释掉
             * @wjt
             */
		/*	//取消点赞权重值减5
			consultation.setPower(powerNum - 5 < 0 ? 0 : powerNum - 5);*/
            //权重值维护详情列表
		/*	power.setConsultation(consultation);
			power.setMember(member);
			power.setCreatedTime(new Date());
			power.setStatus(1);
			power.setType(1);
			powerService.saveAndModify(power);*/
        }
        consultationMapper.insert(consultation);
        return ResultUtils.returnSuccess(type == 1 ? "点赞成功" : "取消点赞");

    }

    @Override
    public Result getConsultationType(Long id, Long memberid) {
        Integer type = null;
        ConsultationFabulous consultationFabulous = consultationfabulousMapper.getConsultationFabulousByConsultationIdAndMemberId(id, memberid);
        if (null == consultationFabulous) {
            type = 1;
        } else {
            if (consultationFabulous.getType() == 1) {
                type = 2;
            } else if (consultationFabulous.getType() == 2) {
                type = 1;
            }

        }
        return ResultUtils.returnSuccess(type + "");
    }

}
