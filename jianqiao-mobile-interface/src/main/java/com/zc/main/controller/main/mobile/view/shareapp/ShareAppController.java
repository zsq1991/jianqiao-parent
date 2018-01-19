package com.zc.main.controller.main.mobile.view.shareapp;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Reference;
import com.zc.main.service.helpdetails.HelpDetailsService;
import com.zc.main.service.shareapp.ShareAppService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.main.controller.main.mobile.view.share
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月16日14:52
 */
@Controller
@RequestMapping("mobile/view/app")
public class ShareAppController {
    private static Logger logger = LoggerFactory.getLogger(ShareAppController.class);

    private static String url = "http://yst-images.img-cn-hangzhou.aliyuncs.com/";

    @DubboConsumer(version = "1.0.0",timeout = 30000,check = false)
    private ShareAppService shareAppService;
    @DubboConsumer(version = "1.0.0",timeout = 30000,check = false)
    private HelpDetailsService helpDetailsService;

    /**
     * @description: web分享页面
     * @author:  ZhaoJunBiao
     * @date:  2018/1/19 13:49
     * @version: 1.0.0
     * @param model
     * @param response
     * @param request
     * @param id  资讯id
     * @param type  来源标识
     * @return
     */
    @RequestMapping(value = "share", method = RequestMethod.GET)
    public String gotoApp(Model model,
                          HttpServletResponse response, HttpServletRequest request,
                          @RequestParam(value = "id") String id,
                          @RequestParam(value = "type") Integer type) {
            logger.info("web分享页面执行，方法入参{" + "咨询id" + id + "来源标识" + type+"}");
        Map<String, Object> consultationnow = shareAppService.getConsultationnow(id);
        if (null == consultationnow) {
            return "view/error";
        }
        if (null == type) {
            return "view/error";
        }
        if (type != 1 && type != 2 && type != 3 && type != 4) {
            return "view/error";
        }
        try {
            if (type == 1) {
                Long consuid = (Long) consultationnow.get("consulatation_id");
                //根据id查询当前的资讯
                Map<String, Object> consultation = shareAppService.getConsultation(consuid);
                logger.info("consultation:" + consultation);
                //得到资讯下面的第一条内容
                Map<String, Object> consultationTop = shareAppService.getConsultationTop(id);
                //得到当前资讯下的详情
                List<Map<String, Object>> consultationList = shareAppService.getConsultationList(consuid + "");
                long object2 = (long) consultationTop.get("id");
                if (StringUtils.isNotBlank(object2 + "")) {
                    //得到访谈详情内容包括图片
                    List<Map<String, Object>> ftDetailList = shareAppService.getFTDetailList(object2 + "");
                    for (Map<String, Object> map : ftDetailList) {
                        Object object = map.get("address");
                        if (null != object) {
                            boolean contains = (object.toString()).contains("http");
                            if (!contains) {
                                String address = url + object.toString();
                                map.put("address", address);
                            }
                        }
                    }
                    model.addAttribute("ftDetailList", ftDetailList);
                }
                //得到当前资讯下的评论
                List<Map<String, Object>> consultationDetail = shareAppService.getConsultationDetail(object2 + "");
                String commentid = null;
                List<Map<String, Object>> mapppp = new ArrayList<Map<String, Object>>();
                //Map<String, Object> replyDetail =new HashMap<String,Object>();
                List list = new ArrayList<>();
                for (Map<String, Object> map : consultationDetail) {
                    Object object = map.get("commentid");
                    if (null != object) {

                        commentid = object.toString();
                    } else {
                        commentid = "";
                    }
                    //得到当前评论下的回复，取前两条
                    mapppp = shareAppService.getreplyDetail(commentid);
                    //consultationDetail.add(mapppp);
                    map.put("replyDetail", mapppp);
                }
                Map<String, Object> ftplNum = shareAppService.getFTPLNum(commentid + "");
                model.addAttribute("ftplNum", ftplNum);
                model.addAttribute("consultation", consultation);
                model.addAttribute("consultationTop", consultationTop);
                model.addAttribute("consultationList", consultationList);
                model.addAttribute("consultationDetail", consultationDetail);

                return "view/jqapp_fangtan";
            }

            if (type == 2) {
                Long consuid = (Long) consultationnow.get("consulatation_id");
                Map<String, Object> consultation = shareAppService.getConsultation(consuid);
                Map<String, Object> consultationTop = shareAppService.getConsultationTop(id);
                long object2 = (long) consultationTop.get("id");
                if (StringUtils.isNotBlank(object2 + "")) {
                    List<Map<String, Object>> ftDetailList = shareAppService.getFTDetailList(object2 + "");
                    for (Map<String, Object> map : ftDetailList) {
                        Object object = map.get("address");
                        if (null != object) {
                            boolean contains = (object.toString()).contains("http");
                            if (!contains) {
                                String address = url + object.toString();
                                map.put("address", address);
                            }
                        }
                    }
                    model.addAttribute("ftDetailList", ftDetailList);
                }
                List<Map<String, Object>> consultationList = shareAppService.getConsultationList(consuid + "");
                List<Map<String, Object>> consultationDetail = shareAppService.getConsultationDetail(object2 + "");
                String commentid = null;
                List<Map<String, Object>> mapppp = new ArrayList<Map<String, Object>>();
                //Map<String, Object> replyDetail =new HashMap<String,Object>();
                List list = new ArrayList<>();
                for (Map<String, Object> map : consultationDetail) {
                    Object object = map.get("commentid");
                    if (null != object) {

                        commentid = object.toString();
                    } else {
                        commentid = "";
                    }

                    mapppp = shareAppService.getreplyDetail(commentid);
                    //consultationDetail.add(mapppp);
                    map.put("replyDetail", mapppp);
                }
                Map<String, Object> ftplNum = shareAppService.getFTPLNum(consuid + "");
                model.addAttribute("ftplNum", ftplNum);
                model.addAttribute("consultation", consultation);
                model.addAttribute("consultationTop", consultationTop);
                model.addAttribute("consultationList", consultationList);
                model.addAttribute("consultationDetail", consultationDetail);

                return "view/jqapp_koushu";
            }

            if (type == 3) {

                Map<String, Object> helpAuthor = shareAppService.getHelpAuthor(id);
                List<Map<String, Object>> imageList = shareAppService.getImageList(id);
                for (Map<String, Object> map : imageList) {
                    Object object = map.get("address");
                    if (null != object) {
                        boolean contains = (object.toString()).contains("http");
                        if (!contains) {
                            String address = url + object.toString();
                            map.put("address", address);
                        }
                    }

                }
                List<Map<String, Object>> helpAuthorIdList = shareAppService.getHelpAuthorIdList(id);
                Map<String, Object> authoruserList = new HashMap<>();
                List<Map<String, Object>> authorList2 = new ArrayList<>();
                List<Map<String, Object>> user = new ArrayList<>();
                List<List<Map<String, Object>>> deatail = new ArrayList<>();
                for (Map<String, Object> consid : helpAuthorIdList) {
                    Object object = consid.get("id");
                    String bid = object.toString();
                    //获取用户信息
                    authoruserList = shareAppService.getAuthorUserList(bid);
                    user.add(authoruserList);
                    //循环用户信息获取对应的评论信息
                    authorList2 = shareAppService.getAuthorList(bid);
                    deatail.add(authorList2);
                }
                model.addAttribute("helpAuthor", helpAuthor);
                model.addAttribute("imageList", imageList);
                model.addAttribute("consultationList", deatail);
                model.addAttribute("authoruserList", user);
                System.out.println(deatail);
                return "view/jqapp_qiuzhu";
            }
            if (type == 4) {//分享详情基本信息
                Integer page = 1;
                Integer rows = 5;
                Map<String, Object> ftplNum = shareAppService.getFTPLNum(id);
                List<Map<String, Object>> helpdetails = helpDetailsService.gethelpdetails(Long.valueOf(id), page, rows);
                //3张图片的获取
                List<Map<String, Object>> imgList = helpDetailsService.getimgList(Long.valueOf(id));
                for (Map<String, Object> map : imgList) {
                    Object object = map.get("address");
                    if (null != object) {
                        boolean contains = (object.toString()).contains("http");
                        if (!contains) {
                            String address = url + object.toString();
                            map.put("address", address);
                        }
                    }
                }
                //用户评论（100条）以下的接口使用private ShareAppService shareAppService;
                /*Map<String, Object> consultationTop = shareAppService.getConsultationTop(id);*/
                //判断得到咨询下面第一条内容||资讯下的详情||评论内容是否为空
                List<Map<String, Object>> consultationDetail = null;
                List<Map<String, Object>> consultationList = null;
                if (helpdetails != null || consultationList != null || consultationDetail != null) {
                    Long object2 = null;
                    for (Map<String, Object> map : helpdetails) {
                        object2 = (Long) map.get("id");
                    }
                    if (StringUtils.isNotBlank(object2 + "")) {
                        List<Map<String, Object>> ftDetailList = shareAppService.getFTDetailList(object2 + "");
                        model.addAttribute("ftDetailList", ftDetailList);
                    }
                    consultationList = shareAppService.getConsultationList(id);
                    consultationDetail = shareAppService.getConsultationDetail(object2 + "");
                    String commentid = null;
                    List<Map<String, Object>> mapppp = new ArrayList<Map<String, Object>>();
                    //Map<String, Object> replyDetail =new HashMap<String,Object>();
                    List list = new ArrayList<>();
                    for (Map<String, Object> map : consultationDetail) {
                        Object object = map.get("commentid");
                        if (null != object) {

                            commentid = object.toString();
                        } else {
                            commentid = "";
                        }

                        mapppp = shareAppService.getreplyDetail(commentid);
                        //consultationDetail.add(mapppp);
                        map.put("replyDetail", mapppp);
                    }
                }
                model.addAttribute("ftplNum", ftplNum);
                model.addAttribute("helpdetails", helpdetails);
                model.addAttribute("imgList", imgList);
                //===========================================
                model.addAttribute("consultationDetail", consultationDetail);
                model.addAttribute("consultationList", consultationList);
                return "view/jqapp_fenxiang";

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "view/error";
        }
        return "";
    }
}
