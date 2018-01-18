package com.zc.main.service.membersearchconsultation;

import com.zc.common.core.result.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : wangxueyang[wxueyanghj@163.com]
 * @version ： 1.0.0
 * @package : com.zc.main.service.membersearchconsultation
 * @progect : jianqiao-parent
 * @Description :
 * @Creation Date ：2018年01月17日17:08
 */
public interface MembersearchconsultationService {
    List<Map<String,Object>> findSearchKeywordByMember(Map<String, Object> map);

    Integer getSearchConsultationByInfo(HashMap<String, Object> map);
    Result saveMemberSearchConsultation(Long id, String info);
}
