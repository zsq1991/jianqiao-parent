package com.zc.main.controller.main.mobile.view.doctor;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.controller.main.mobile.view.specialist.SpecialistController;
import com.zc.main.service.doctor.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @package : com.alqsoft.controller.mobile.after.doctor
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : 朱军
 * @Creation Date ：2018年01月10日9:49
 */
@Controller
@RequestMapping("mobile/view/doctor")
public class DoctorController {

    private static Logger logger = LoggerFactory.getLogger(DoctorController.class);
    @DubboConsumer(version="1.0.0")
    private DoctorService doctorServer;

    /**
     * @description ：获取高手详情
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/10 9:18
     * @param doctorId
     * @return
     */
    @RequestMapping(value = "getDoctorDetail",method = RequestMethod.POST)
    @ResponseBody
    public Result getDoctorDetail(@RequestParam(value = "doctorId" ,required = false)Long doctorId){
        logger.info("===========开始调用【获取高手详情】接口===========");
        try {
            logger.info("===========调用【获取高手详情】接口成功===========");
            return doctorServer.getDoctorDetail(doctorId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("===========调用【获取高手详情】接口异常===========");
            return ResultUtils.returnError("接口异常");
        }
    }
}
