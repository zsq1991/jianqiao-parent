package com.zc.service.impl.doctor;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.doctor.Doctor;
import com.zc.main.service.doctor.DoctorService;
import com.zc.mybatis.dao.doctor.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @package : com.alqsoft.service.impl.doctor
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : 朱军
 * @Creation Date ：2018年01月10日9:53
 */
@Component
@Service(version = "1.0.0", interfaceClass = DoctorService.class)
@Transactional(readOnly=true)
public class DoctorServiceImpl implements DoctorService{


    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public Result getDoctorDetail(Long doctorId) {
        Map<String,Object> map = Maps.newHashMap();
        //获取高手详情
        Map<String, Object> doctorInfo = doctorMapper.getDoctorInfo(doctorId);
        if(doctorInfo == null){
            return ResultUtils.returnError("高手不存在");
        }else{
            map.put("doctorInfo",doctorInfo);

            //荣誉图片地址集合
            List<String> honorAddressList = doctorMapper.getDoctorAttachment(new Long(doctorInfo.get("id").toString()));
            map.put("doctorInfo",doctorInfo);
            map.put("honorAddress",honorAddressList);
            Map<String,Object> consultatoin = Maps.newHashMap();
            List<Doctor> consultatoinList = doctorMapper.queryConsultationList(new Long(doctorInfo.get("id").toString()));
            //专家相关推荐列表
            map.put("consultatoin",consultatoinList);
        }

        return ResultUtils.returnSuccess("获取成功",map);
    }
}
