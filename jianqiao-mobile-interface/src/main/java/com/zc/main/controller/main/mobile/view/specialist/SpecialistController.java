package com.zc.main.controller.main.mobile.view.specialist;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.service.specialist.SpecialistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.main.controller.main.specialist
 * @className : SpecialistController
 * @description ：专家控制层
 * @Created by  : 朱军
 * @Creation Date ： 2018/1/16 13:56
 * @version
 */
@Controller
@RequestMapping("mobile/view/specialist")
public class SpecialistController {


    private static Logger logger = LoggerFactory.getLogger(SpecialistController.class);

    @DubboConsumer(version="1.0.0")
    private SpecialistService specialistService;


    /**
     * @description ：app权威高手、专家团队页面
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/10 10:37
     * @param dataType
     * @param model
     * @return
     */
    @RequestMapping(value = "toSpecialistPage",method = RequestMethod.GET)
    public String specialistPage(@RequestParam(value="dataType",defaultValue = 1+"")String dataType, Model model){
        List<Map<String,Object>> specialistItem = specialistService.getSpecialistData(dataType);
        List<Map<String,Object>> doctorItem = specialistService.getDoctorData(dataType);
        model.addAttribute("specialistItem",specialistItem);
        model.addAttribute("doctorItem",doctorItem);
        return "view/jianqiaoteam";
    }

    /**
     * @description ：列表获取更多数据
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/10 18:12
     * @param utype
     * @return
     */
    @RequestMapping(value = "moreDoctorSpecialist",method = RequestMethod.POST)
    @ResponseBody
    public Result moreDoctorSpecialist(@RequestParam("utype")Integer utype){
        if(utype.intValue() == 1){
            List<Map<String,Object>> specialistItem = specialistService.getSpecialistData("0");
            return ResultUtils.returnSuccess("成功",specialistItem);
        }else{

            List<Map<String,Object>> doctorItem = specialistService.getDoctorData("0");
            return ResultUtils.returnSuccess("成功",doctorItem);
        }
    }

    /**
     * @description ：获取专家详情
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/8 15:09
     * @param specialistId
     * @return
     */
    @RequestMapping(value = "getSpecialistDetail",method = RequestMethod.POST)
    @ResponseBody
    public Result getSpecialistDetail(@RequestParam("specialistId")Long specialistId){

        logger.info("===========开始调用【获取专家详情】接口===========");
        try {
            return specialistService.getSpecialistDetail(specialistId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("===========调用【获取专家详情】接口异常===========");
            return ResultUtils.returnError("接口异常");
        }
    }

}
