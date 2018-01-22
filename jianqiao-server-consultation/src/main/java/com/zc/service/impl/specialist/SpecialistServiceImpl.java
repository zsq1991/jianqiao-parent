package com.zc.service.impl.specialist;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.specialists.Specialist;
import com.zc.main.service.specialist.SpecialistService;
import com.zc.mybatis.dao.specialist.SpecialistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @package : com.alqsoft.service.impl.specialist
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : 朱军
 * @Creation Date ：2018年01月09日9:19
 */
@Component
@Service(version = "1.0.0", interfaceClass = SpecialistService.class)
@Transactional(readOnly=true,rollbackFor = Exception.class)
public class SpecialistServiceImpl implements SpecialistService {

    @Autowired
    private SpecialistMapper specialistMapper;

    @Override
    public Result getSpecialistDetail(Long specialistId) {

        Map<String,Object> map = Maps.newHashMap();
        //专家信息
        Map<String, Object> specialistDetail = specialistMapper.getSpecialistDetail(specialistId);
        if(specialistDetail == null){
            return ResultUtils.returnError("专家不存在");
        }else{
            //荣誉图片地址集合
            List<String> honorAddressList = specialistMapper.getDoctorAttachment(new Long(specialistDetail.get("id").toString()));
            map.put("specialistInfo",specialistDetail);
            map.put("honorAddress",honorAddressList);
            Map<String,Object> consultatoin = Maps.newHashMap();
            if(specialistDetail.get("office") != null && specialistDetail.get("office") != ""){
                List<Specialist> consultatoinList = specialistMapper.queryConsultationList(specialistDetail.get("office").toString());
                //专家相关推荐列表
                map.put("consultatoin",consultatoinList);
            }
        }

        return ResultUtils.returnSuccess("获取成功",map);
    }

    @Override
    public List<Map<String, Object>> getSpecialistData(String dataType) {
        Map<String,Object> param = Maps.newHashMap();
        if("0".equals(dataType)){
            Integer specialistCount = specialistMapper.getSpecialistCount();
            param.put("specialistCount",specialistCount);
        }
        param.put("dataType",dataType);
        List<Map<String, Object>> maplist =specialistMapper.getSpecialistData(param);
        return maplist;
    }

    @Override
    public List<Map<String, Object>> getDoctorData(String dataType) {
        Map<String,Object> param = Maps.newHashMap();
        if("0".equals(dataType)){
            Integer doctorCount = specialistMapper.getDoctorCount();
            param.put("doctorCount",doctorCount);
        }
        param.put("dataType",dataType);
        List<Map<String, Object>> maplist =specialistMapper.getDoctorData(param);
        return maplist;
    }

}
