package com.zc.mybatis.dao.doctor;

import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.doctor.Doctor;

import java.util.List;
import java.util.Map;

/**
 * @description 高手信息
 * @author 朱军
 * @date 2018-01-25 17:26
 * @version 1.0.0
 */
@MyBatisRepository
public interface DoctorMapper {

    /**
     * @description ：根据id获取高手信息
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/10 9:28
     * @param doctorId
     * @return
     */
    public Map<String,Object> getDoctorInfo(Long doctorId);

    /**
     * @description ：根据高手ID获取荣誉图片集合
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/9 15:07
     * @param id
     * @return
     */
    List<String> getDoctorAttachment(Long id);

    /**
     * @description ：根据高手id获取咨询列表
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/10 10:36
     * @param doctorId
     * @return
     */
    public List<Doctor> queryConsultationList(Long doctorId);
}
