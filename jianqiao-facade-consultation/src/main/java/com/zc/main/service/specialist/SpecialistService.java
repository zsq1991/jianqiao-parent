package com.zc.main.service.specialist;


import com.zc.common.core.result.Result;

import java.util.List;
import java.util.Map;

/**
 * @package : com.alqsoft.service.specialist
 * @progect : jianqiao-parent
 * @Description :
 * @author  by : 朱军
 * @Creation Date ：2018年01月09日9:19
 */
public interface SpecialistService {

    /**
     * @description ：获取app专家详情
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/9 16:49
     * @param specialistId
     * @return
     */
    public Result getSpecialistDetail(Long specialistId);

    /**
     * @description ：获取专家列表
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/9 16:48
     * @param dataType 1 获取前6条 0 获取所有
     * @return
     */
    List<Map<String,Object>> getSpecialistData(String dataType);

    /**
     * 获取高手
     * @param dataType
     * @return
     */
    List<Map<String,Object>> getDoctorData(String dataType);

}
