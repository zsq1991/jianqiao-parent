package com.zc.main.controller.main.mobile.view.consultationcomment;


import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.main.service.consultationInfo.ConsultationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.zc.common.core.result.Result;
import javax.annotation.Resource;

/**
 * @description:
 * @author:  ZhaoJunBiao
 * @date:  2018/1/17 16:55
 * @version: 1.0.0
 */
@Controller
@RequestMapping("mobile/view/consultationcomment")
public class ConsultationCommentViewController {
    private static Logger log = LoggerFactory.getLogger(ConsultationCommentViewController.class);

    @DubboConsumer(version = "1.0.0",timeout = 30000)
    private ConsultationInfoService consultationInfoService;

    /**
     * 首页获取咨询评论列表
     *@description:
     * @author:  ZhaoJunBiao
     * @date:  2018/1/17 17:26
     * @version: 1.0.0
     * @param id    咨询ID
     * @param uuid  用户uuid
     * @param phone 用户phone
     * @param page  当前页
     * @param size  每页记录数
     * @return
     */
    @RequestMapping(value = "getConsultationDetail", method = RequestMethod.POST)
    public Result getConsultationDetailInfo(@RequestParam("id") String id,
                                            @RequestParam(value = "uuid", required = false) String uuid,
                                            @RequestParam(value = "phone", required = false) String phone,
                                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return consultationInfoService.getConsultationDetail(id, uuid, phone, page, size);
    }

    /**
     * @description: 点击顶级评论的评论详情页面（回复列表）
     * @author:  ZhaoJunBiao
     * @date:  2018/1/17 17:21
     * @version: 1.0.0
     * @param id 顶级的评论的id
     * @param uuid 唯一标识
     * @param phone 登陆者的手机号
     * @param page  当前页
     * @param size  当前页的数据
     * @return
     */
    @RequestMapping(value = "getCommentDetailList", method = RequestMethod.POST)
    public Result getTopAndTopAfterCommentList(@RequestParam("id") String id,
                                               @RequestParam(value = "uuid", required = false) String uuid,
                                               @RequestParam(value = "phone", required = false) String phone,
                                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                               @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return consultationInfoService.getTopAndTopAfterCommentByTopIdList(id, uuid, phone, page, size);
    }
}
