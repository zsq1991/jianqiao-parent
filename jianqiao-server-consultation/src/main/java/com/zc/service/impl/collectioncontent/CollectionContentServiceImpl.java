package com.zc.service.impl.collectioncontent;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.date.DateUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.service.collectioncontent.CollectionContentService;
import com.zc.main.service.consultationattachment.ConsultationAttachmentService;
import com.zc.mybatis.dao.ConsultationMapper;
import com.zc.mybatis.dao.CollectionContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : wangxueyang[wxueyanghj@163.com]
 * @version ： 1.0.0
 * @package : com.zc.service.impl.collectioncontent
 * @progect : jianqiao-parent
 * @Description : 收藏  业务实现
 * @Creation Date ：2018年01月17日11:46
 */
@Component
@Service(version = "1.0.0", interfaceClass = CollectionContentService.class)
@Transactional(readOnly = true)
public class CollectionContentServiceImpl implements CollectionContentService {

    private static Logger logger = LoggerFactory.getLogger(CollectionContentServiceImpl.class);

    @Autowired
    private CollectionContentMapper collectionContentMapper;

    @Autowired
    private ConsultationMapper consultationMapper;

    @Autowired
    private ConsultationAttachmentService consultationAttachmentService;

    @Override
    public Result mycollection(Member member, Integer page, Integer rows) {
        logger.info("获取收藏列表传入参数==》 member：" + member.toString() + " page：" + page + "rows:" + rows);
        List<Map<String, Object>> consultationInfoList = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", member.getId());
            param.put("startIndex", page);
            param.put("endIndex", rows);
            // 得到当前用户的收藏信息
            List<Map<String, Object>> myCollentent = collectionContentMapper.getMyCollentent(param);
            if (myCollentent.size() <= 0) {
                return ResultUtils.returnError("没有收藏信息");
            }
            // 获取当前所有子类的父id
            Set<String> set = new HashSet<String>();
            for (Map<String, Object> consultationId : myCollentent) {
                Map<String, Object> consulationDetail = consultationMapper.getConsultationById(consultationId.get("consultation_id").toString());
                if (null == consulationDetail) {
                    continue;
                }
                String type = (String) consulationDetail.get("type").toString();
                if (type.equals("1") || type.equals("3")) {
                    String consultation_id = consulationDetail.get("pid").toString();
                    set.add(consultation_id);
                } else if (type.equals("0") || type.equals("2") || type.equals("4") || type.equals("6")) {
                    String consultation_id = consulationDetail.get("id").toString();
                    set.add(consultation_id);
                }
            }


            // 判断当前
            for (String consultation_id : set) {
                // String consultation_id = map.get("consultation_id");  咨询详情
                Map<String, Object> consulationDetail = consultationMapper.getConsultationById(consultation_id);
                if (null == consulationDetail) {
                    continue;
                }
                // 处理时间格式
                String chidAlreadyTime1 = DateUtils.dateFormat((Date) consulationDetail.get("createdTime"),"yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat chiddf1 = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
                String chidNowTime1 = chiddf1.format(new Date());
                String chidTime11 = chidAlreadyTime1.subSequence(0, 10).toString();
                String chidTime22 = chidNowTime1.subSequence(0, 10).toString();
                if (chidTime11.equals(chidTime22)) {// 同一天
                    String chidcreatedTime = chidAlreadyTime1.subSequence(11, 16).toString();// 截取当天
                    // 时，分
                    consulationDetail.put("createdTime", chidcreatedTime);
                } else {// 不同一天
                    String chidcreatedTime = chidAlreadyTime1.subSequence(0, 10).toString();// 截取当天
                    // 年，月，日
                    consulationDetail.put("createdTime", chidcreatedTime);
                }
                // 获取当前咨询的type
                String type = (String) consulationDetail.get("type").toString();
                boolean isMore = false;// 是否是主题或访谈的标识 false 不是 true 是
                List<Map<String, Object>> consultationChidList = new ArrayList<Map<String, Object>>();
                if (type.equals("0") || type.equals("2")) {// 0是访谈主题 1访谈内容 2口述主题
                    // 3口述内容 4求助 5回答 6分享
                    // 判断访谈和口述是否有内容
                    Integer count = consultationMapper.getCountById(Long.valueOf(consulationDetail.get("id").toString()));
                    if (count == 0) {
                        continue;
                    }
                    isMore = true;
                    consultationChidList = consultationMapper.findConsultationChidById(Long.valueOf(consulationDetail.get("id").toString()));// 根据访谈或口述的id查询其子类
                    // 取详情内容
                    Object detailContentChid = "";
                    if (consultationChidList.size() > 0) {
                        for (Map<String, Object> consultationChidInfo : consultationChidList) {
                            // 处理时间格式
                            String chidAlreadyTime = DateUtils.dateFormat((Date) consultationChidInfo.get("createdTime"),"yyyy/MM/dd HH:mm:ss");
                            SimpleDateFormat chiddf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
                            String chidNowTime = chiddf.format(new Date());
                            String chidTime1 = chidAlreadyTime.subSequence(0, 10).toString();
                            String chidTime2 = chidNowTime.subSequence(0, 10).toString();
                            if (chidTime1.equals(chidTime2)) {
                                // 同一天
                                String chidcreatedTime = chidAlreadyTime.subSequence(11, 16).toString();// 截取当天
                                // 时，分
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            } else {
                                // 不同一天
                                String chidcreatedTime = chidAlreadyTime.subSequence(0, 10).toString();// 截取当天
                                // 年，月，日
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            }

                            // 图片地址
                            String addressChid = "";
                            // 处理咨询图片
                            List<Map<String, Object>> consultationChidAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationChidInfo.get("id").toString()));
                            consultationChidInfo.put("address", consultationChidAttachmentList);

                            // 处理访谈和口述专题的详情内容
                            Map<String, Object> map1 = consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consulationDetail.get("id").toString()));
                            if (null == map1 || map1.size() == 0) {
                                detailContentChid = "";
                            } else {
                                detailContentChid = map1.get("detailContent");
                            }
                            consultationChidInfo.put("detailContentChid", detailContentChid);
                        }
                    }

                    consulationDetail.put("isMore", isMore);
                    consulationDetail.put("consultationChidList", consultationChidList);

                } else {

                    consulationDetail.put("isMore", isMore);
                    consulationDetail.put("consultationChidList", consultationChidList);
                }

                // 处理咨询图片
                List<Map<String, Object>> consultationAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consulationDetail.get("id").toString()));
                consulationDetail.put("address", consultationAttachmentList);

                // 取详情内容
                Object detailContent = "";
                if (type.equals("4") || type.equals("6")) {
                    Map<String, Object> map2 = consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consulationDetail.get("id").toString()));
                    if (null == map2 || map2.size() == 0) {
                        detailContent = "";
                    } else {
                        detailContent = map2.get("detailContent");
                    }
                }
                consulationDetail.put("detailContent", detailContent);
                consultationInfoList.add(consulationDetail);
            }
        }catch (Exception e){
            logger.info("获取收藏列表异常："+e.getMessage());
            e.printStackTrace();
        }
        logger.info("获取收藏列表成功!");
        return ResultUtils.returnSuccess("请求成功", consultationInfoList);
    }
}
