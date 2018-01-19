package com.zc.main.service.consultationfabulous;


import com.zc.common.core.result.Result;

public interface ConsultationFabulousService {
	
	public Result getConsultationFabulousByIdAndMemberId(Long id, Long memberId, Integer type);
	
	public Result getConsultationType(Long id, Long memberId);
	
}
