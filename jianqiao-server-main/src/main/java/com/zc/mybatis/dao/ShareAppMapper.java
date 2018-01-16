package com.zc.mybatis.dao;

import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.mybatis.dao
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月16日15:12
 */
public interface ShareAppMapper {
    Map<String,Object> getConsultationnow(String id);

    Map<String,Object> getConsultation(Long id);

    Map<String,Object> getConsultationTop(String id);

    List<Map<String,Object>> getConsultationList(String consultaionId);
}
