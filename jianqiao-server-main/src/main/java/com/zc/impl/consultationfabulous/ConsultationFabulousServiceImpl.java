/*
package com.zc.impl.consultationfabulous;




import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.consultation.Consultation;
import com.zc.main.service.consultationfabulous.ConsultationFabulousService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly=true)
public class ConsultationFabulousServiceImpl implements ConsultationFabulousService {
	private static Logger logger = LoggerFactory.getLogger(ConsultationFabulousServiceImpl.class);
	
	@Autowired
	private ConsultationfabulousDao consultationfabulousDao;
	@Autowired
	private ConsultationDao consultationDao;
	@Autowired
	private MemberMsgDao memberMsgDao;
	
	@Autowired
	private PowerService powerService;
	
	*/
/**
	 *对4种内容进行点赞
	 *点赞 1   取消赞2
	 *//*

	@Override
	@Transactional
	public Result getConsultationFabulousByIdAndMemberId(Long id, Long memberId, Integer type) {
		

		// TODO Auto-generated method stub
		Power power = new Power();
		
		
		if(StringUtils.isBlank(type+"")){
			return ResultUtils.returnError("当前操作有误!!1");
		}
		
		System.out.println("开始执行!");
		if(type !=1 && type !=2){
			return ResultUtils.returnError("当前操作有误!!2");
		}
		
		Consultation consultation = consultationDao.getRowLock(id);
		if(null == consultation){
			return ResultUtils.returnError("当前咨询不存在!");
		}
		ConsultationFabulous consultationFabulous = consultationfabulousDao.getConsultationFabulousByConsultationIdAndMemberId(id, memberId);
		Member member = new Member();
		member.setId(memberId);
		if(null == consultationFabulous){
			if(type ==2){
				return ResultUtils.returnError("操作失败，未有点赞记录");
			}
			ConsultationFabulous consultationFabulous1 = new ConsultationFabulous();
			consultationFabulous1.setCreatedTime(new Date());
			consultationFabulous1.setMember(member);
			consultationFabulous1.setConsultation(consultation);
			consultationFabulous1.setType(type);
			ConsultationFabulous save = consultationfabulousDao.save(consultationFabulous1);
			System.out.println(save);
		}else{
			
			Integer type2 = consultationFabulous.getType();
			if(type2 == type){
				return ResultUtils.returnError("当前操作有误!");
			}
			consultationFabulous.setType(type);
			consultationfabulousDao.save(consultationFabulous);
			
		}
		Long fabulousnum = consultation.getFabulousNum()==null ? 0L : consultation.getFabulousNum();
		//Long powerNum = consultation.getPower()==null ? 0L : consultation.getPower();
		
	//@wudi===================================点赞时维护MemberMsg表或者取消点赞删除MemberMsg表==================================================	
		if(type==1){
			MemberMsg msg = new MemberMsg();
			msg.setMember(consultation.getMember());
			msg.setType(5);
			msg.setContentType(consultation.getType());
			msg.setConsultation(consultation);
			msg.setMemberBase(member);//点赞者
			msg.setCreatedTime(new Date());
			
			memberMsgDao.save(msg);
			
			consultation.setFabulousNum(fabulousnum+1);

			*/
/**
			 * 因为需求原因,将三期需求的权重值增加注释掉
			 * @wjt
			 *//*

			//点赞一次权重值加5
			//consultation.setPower(powerNum + 5);
			
			//权重值维护详情列表
			*/
/*power.setConsultation(consultation);
			power.setMember(member);
			power.setCreatedTime(new Date());
			power.setStatus(0);
			power.setType(1);
			powerService.saveAndModify(power);*//*

			
		}else{
			//取消点赞时删除memberMsg
			Integer types=5;
			logger.info("取消点赞获取的参数："+memberId+"===="+types+"====="+id+"+++++++");
			memberMsgDao.deleteMemberMsgByConsulatationId(memberId, types, id);
			
			consultation.setFabulousNum(fabulousnum-1 <0 ? 0 : fabulousnum-1);
			*/
/**
			 * 因为需求原因,将三期需求的权重值增加注释掉
			 * @wjt
			 *//*

		*/
/*	//取消点赞权重值减5
			consultation.setPower(powerNum - 5 < 0 ? 0 : powerNum - 5);*//*

			//权重值维护详情列表
		*/
/*	power.setConsultation(consultation);
			power.setMember(member);
			power.setCreatedTime(new Date());
			power.setStatus(1);
			power.setType(1);
			powerService.saveAndModify(power);*//*

		}
		consultationDao.save(consultation);
		
		
		
		return ResultUtils.returnSuccess(type==1 ? "点赞成功" : "取消点赞");
		
	}
	@Override
	public Result getConsultationType(Long id, Long memberid) {
		// TODO Auto-generated method stub
		Integer type =null;
		ConsultationFabulous consultationFabulous = consultationfabulousDao.getConsultationFabulousByConsultationIdAndMemberId(id,memberid);
		if(null == consultationFabulous){
			type =1;
		}else{
			if(consultationFabulous.getType() ==1){
				type =2;
			}else if(consultationFabulous.getType() ==2){
				type = 1;
			}
			
		}
		return ResultUtils.returnSuccess(type+"");
	}

}
*/
