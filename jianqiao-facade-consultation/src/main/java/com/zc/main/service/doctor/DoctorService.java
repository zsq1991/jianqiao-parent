package com.zc.main.service.doctor;

import com.zc.common.core.result.Result;

/**
 * @package : com.alqsoft.service.doctor
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : Administrator
 * @Creation Date ：2018年01月10日9:52
 */
public interface DoctorService {

    /**
     * @description ：获取高手详情
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/10 9:19
     * @param doctorId
     * @return
     */
    Result getDoctorDetail(Long doctorId);
}
