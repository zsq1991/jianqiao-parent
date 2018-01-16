package com.zc.mybatis.dao;

import com.zc.main.entity.consultationfabulous.ConsultationFabulous;
import org.springframework.data.repository.query.Param;
import tk.mybatis.mapper.common.BaseMapper;

public interface ConsultationfabulousMapper extends BaseMapper<ConsultationFabulous> {
	
	
	 ConsultationFabulous getConsultationFabulousByIdAndMemberId(@Param("id") Long id,@Param("memberId") Long memberId);

	/**
	 * @description: 根据咨询id和会员id查询点赞
	 * @author:  ZhaoJunBiao
	 * @date:  2018/1/16 13:47
	 * @version: 1.0.0
	 * @param id
	 * @param memberId
	 * @return
	 */
	ConsultationFabulous getConsultationFabulousByConsultationIdAndMemberId(@Param("id") Long id,@Param("memberId") Long memberId);
	
}
