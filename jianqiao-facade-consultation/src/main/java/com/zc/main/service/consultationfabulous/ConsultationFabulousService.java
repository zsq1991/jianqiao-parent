package com.zc.main.service.consultationfabulous;


import com.zc.common.core.result.Result;

/**
 * @description 资讯点赞
 * @author ZhaoJunBiao
 * @date 2018-01-25 17:19
 * @version 1.0.0
 */
public interface ConsultationFabulousService {
	
	public Result getConsultationFabulousByIdAndMemberId(Long id, Long memberId, Integer type);
	
	public Result getConsultationType(Long id, Long memberId);
	
}
