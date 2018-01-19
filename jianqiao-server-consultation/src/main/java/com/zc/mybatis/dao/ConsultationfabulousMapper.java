package com.zc.mybatis.dao;

import com.common.util.mybatis.BasicMapper;
import com.zc.main.entity.consultationfabulous.ConsultationFabulous;
import org.apache.ibatis.annotations.Param;

public interface ConsultationfabulousMapper extends BasicMapper<ConsultationFabulous> {
	
	
	 ConsultationFabulous getConsultationFabulousByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

	/**
	 * @description: 根据咨询id和会员id查询点赞
	 * @author:  ZhaoJunBiao
	 * @date:  2018/1/16 13:47
	 * @version: 1.0.0
	 * @param id
	 * @param memberId
	 * @return
	 */
	ConsultationFabulous getConsultationFabulousByConsultationIdAndMemberId(@Param("id")Long id, @Param("memberId")Long memberId);
	
}
