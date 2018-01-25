package com.zc.main.service.consultationinfo;

import com.zc.common.core.result.Result;

/**
 * @package : com.zc.main.service.consultationinfo
 * @progect : jianqiao-parent
 * @Description :
 * @author  by :ZhaoJunBiao
 * @Creation Date ：2018年01月17日16:59
 */
public interface ConsultationInfoService {

    Result getConsultationDetail(String id, String uuid, String phone, Integer page, Integer size);

    Result getTopAndTopAfterCommentByTopIdList(String id, String uuid, String phone, Integer page, Integer size);
}
