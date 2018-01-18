package com.zc.main.controller.main.mobile.view.consultation;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.service.consultation.ConsultationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gaoge
 * @version APP首页内容展示
 * @ClassName ConsultationInfoViewController
 * Date:     2017年6月13日  10:45:41 <br/>
 * @see
 */
@RequestMapping("mobile/view/consultation")
@Controller
public class ConsultationViewController {

    private static Logger logger = LoggerFactory.getLogger(ConsultationViewController.class);

    @DubboConsumer(version = "1.0.0",timeout = 30000)
    private ConsultationService consultationService;

    /**
     * @param page      页码
     * @param rows      行数
     * @param checktype 类型：1精选  2分享
     * @return
     * @description ：APP首页  内容根据关键词搜索
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:04
     * @version 1.0.0
     */
    @RequestMapping(value = "findconsultationinfohomepage", method = RequestMethod.POST)
    @ResponseBody
    public Result findconsultationinfohomepage(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows,
            @RequestParam(value = "checktype", defaultValue = "1", required = false) String checktype) {//checktype  1精选  2分享

        return consultationService.findconsultationinfo(page, rows, checktype);
    }

    /**
     * @param page 页码
     * @param rows 每页行数
     * @description ：APP求助  内容根据关键词搜索
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:43
     * @version 1.0.0
     */
    @RequestMapping(value = "findconsultationinfohelp", method = RequestMethod.POST)
    @ResponseBody
    public Result findconsultationinfohelp(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows) {

        return consultationService.findConsultationInfoHelp(page, rows);
    }

    /**
     * @param page      页码
     * @param rows      每页行数
     * @param checktype 类型 ： 1访谈  2口述
     * @description ：APP民间高手  内容根据关键词搜索
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:37
     * @version 1.0.0
     */
    @RequestMapping(value = "findconsultationinfopeople", method = RequestMethod.POST)
    @ResponseBody
    public Result findconsultationinfopeople(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows,
            @RequestParam(value = "checktype", defaultValue = "1", required = false) String checktype) {

        logger.info("PP民间高手  内容根据关键词搜索: 咨询类型checktype={}", checktype);
        return consultationService.findConsultationInfoPeople(page, rows, checktype);
    }

    /**
     * @param page     页码
     * @param rows     每页行数
     * @param memberId 用户id
     * @description ：访谈详情页   点用户头像查看访谈
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:36
     * @version 1.0.0
     */
    @RequestMapping(value = "findconsultationbyfangtan", method = RequestMethod.POST)
    @ResponseBody
    public Result findconsultationbyfangtan(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows,
            @RequestParam(value = "memberId") String memberId) {

        String checktype = "1";//checktype  1 访谈  2 口述   3 求助   4 分享
        logger.info("访谈详情页   点用户头像查看访谈: 用户id={},咨询类型checktype={}", memberId, checktype);
        return consultationService.findConsultationAllByTouxiang(page, rows, memberId, checktype);
    }

    /**
     * @param page     页码
     * @param rows     每页行数
     * @param memberId 用户id
     * @description ：访谈详情页   点用户头像查看口述
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:35
     * @version 1.0.0
     */
    @RequestMapping(value = "findconsultationbykoushu", method = RequestMethod.POST)
    @ResponseBody
    public Result findconsultationbykoushu(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows,
            @RequestParam(value = "memberId") String memberId) {

        //checktype  1 访谈  2 口述   3 求助   4 分享
        String checktype = "2";
        logger.info("访谈详情页   点用户头像查看口述: 用户id={},咨询类型checktype={}", memberId, checktype);
        return consultationService.findConsultationAllByTouxiang(page, rows, memberId, checktype);
    }

    /**
     * @param page     页码
     * @param rows     每页行数
     * @param memberId 用户id
     * @description ：访谈详情页   点用户头像查看求助
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:05
     * @version 1.0.0
     */
    @RequestMapping(value = "findconsultationbyqiuzhu", method = RequestMethod.POST)
    @ResponseBody
    public Result findconsultationbyqiuzhu(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows,
            @RequestParam(value = "memberId") String memberId) {

        String checktype = "3";//checktype  1 访谈  2 口述   3 求助   4 分享
        logger.info("访谈详情页   点用户头像查看求助: 用户id={},咨询类型checktype={}", memberId, checktype);
        return consultationService.findConsultationAllByTouxiang(page, rows, memberId, checktype);
    }

    /**
     * @param page     页码
     * @param rows     每页行数
     * @param memberId 用户id
     * @description ：访谈详情页   点用户头像查看分享
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:34
     * @version 1.0.0
     */
    @RequestMapping(value = "findconsultationbyfengxiang", method = RequestMethod.POST)
    @ResponseBody
    public Result findconsultationbyfengxiang(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows,
            @RequestParam(value = "memberId") String memberId) {

        String checktype = "4";//checktype  1 访谈  2 口述   3 求助   4 分享
        logger.info("访谈详情页   点用户头像查看分享: 用户id={},咨询类型checktype={}", memberId, checktype);
        return consultationService.findConsultationAllByTouxiang(page, rows, memberId, checktype);
    }

    /**
     * 检测是否是高级用户
     *
     * @return
     */
    @RequestMapping(value = "checkisusertype", method = RequestMethod.POST)
    @ResponseBody
    public Result checkisusertype(@RequestParam(value = "memberId") String memberId) {

        logger.info("==========开始调用【检测是否是高级用户】接口方法==========");
        try {
            return consultationService.checkIsUserType(memberId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("==========调用【检测是否是高级用户】接口方法异常==========");
            return ResultUtils.returnError("接口异常");
        }
    }

    /**
     * @description ：查看全部  分页加载
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/17 11:49
     * @param page
     * @param rows
     * @param typeId
     * @return
     */
    @RequestMapping(value = "findconsultationallbyfive", method = RequestMethod.POST)
    @ResponseBody
    public Result findconsultationallbyfive(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "5", required = false) Integer rows,
            @RequestParam(value = "typeId") String typeId) {//0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享

        logger.info("==========开始调用【查看全部分页加载】接口方法==========");
        try {
            return consultationService.findConsultationAllByFive(page, rows, typeId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("==========调用【查看全部分页加载】接口方法异常==========");
            return ResultUtils.returnError("接口异常");
        }
    }

    /**
     * 咨询信息详情
     *
     * @param cid 咨询ID
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public
    @ResponseBody
    Result consultationDetail(@RequestParam("id") String cid,
                              @RequestParam(value = "row", defaultValue = "1", required = false) int row,
                              @RequestParam(value = "type", defaultValue = "0", required = false) String type) {

        return consultationService.getConsultationDetail(cid, null, row, type);
    }

    /**
     * APP首页搜索接口
     *
     * @return
     */
    @RequestMapping(value = "searchconsultationinfo", method = RequestMethod.POST)
    @ResponseBody
    public Result searchconsultationinfo(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows,
            @RequestParam(value = "info") String info,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "uuid") String uuid,
            @RequestParam(value = "checktype", defaultValue = "1", required = false) String checktype) {//checktype  1精选(全部) 2口述  3分享   4求助   5访谈

        return consultationService.searchConsultationInfo(page, rows, info, phone, uuid, checktype);
    }

    /**
     * 咨询信息详情 查看全部
     *
     * @return
     */
    @RequestMapping(value = "getSubjectByPage", method = RequestMethod.POST)
    public
    @ResponseBody
    Result consultationTypePage(
            @RequestParam("id") String cid,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int row) {

        return consultationService.getConsultationSubjectByPage(cid, page, row, null);
    }

}
