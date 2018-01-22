package com.zc.service.impl.consultationInfo;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.service.consultationInfo.ConsultationInfoService;
import com.zc.main.service.member.MemberService;
import com.zc.mybatis.dao.ConsultationCommentMapper;
import com.zc.mybatis.dao.ConsultationMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @package : com.zc.service.impl.consultationInfo
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月17日16:59
 */
@Service(version = "1.0.0",interfaceClass =ConsultationInfoService.class )
@Transactional(readOnly = true,rollbackFor=Exception.class)
@Component
public class ConsultationInfoServiceImpl implements ConsultationInfoService {


    private static Logger log = LoggerFactory.getLogger(ConsultationInfoServiceImpl.class);

    @DubboConsumer(version = "1.0.0", timeout = 30000)
    private MemberService memberService;
    @Autowired
    private ConsultationMapper consultationMapper;
    @Autowired
    private ConsultationCommentMapper consultationCommentMapper;

    /**
     * @description: 首页获取咨询评论列表
     * @author:  ZhaoJunBiao
     * @date:  2018/1/19 15:11
     * @version: 1.0.0
     * @param id    咨询ID
     * @param uuid  用户uuid
     * @param phone 用户phone
     * @param page  当前页
     * @param size  每页记录数
     * @return
     */
    @Override
    public Result getConsultationDetail(String id, String uuid, String phone, Integer page, Integer size) {
        log.info("首页获取咨询评论列表接口调用，方法入参{"+ "咨询id:"+id+",用户phone："+phone+"}");
        String loginuser = "0";
        if (StringUtils.isBlank(id)) {
            return ResultUtils.returnError("参数异常");
        }
        try {
            Map<String, Object> result = new HashMap<>();
            Long memberId = 0L;
            if (StringUtils.isNotBlank(uuid) && StringUtils.isNotBlank(phone)) {
                Map<String, Object> paramMap = Maps.newHashMap();
                paramMap.put("uuid", uuid);
                paramMap.put("phone", phone);
                Member member = memberService.getMemberByIdAndUuid(paramMap);
                if (Objects.isNull(member)) {
                    return ResultUtils.returnError("用户信息不存在");
                }
                memberId = member.getId();
                loginuser = "1";
            }
            //通过资讯id查询资讯数据
            Map<String, Object> consultation = consultationMapper.getConsultationById(id);
            if (consultation.size()==0) {
                return ResultUtils.returnError("咨询不存在");
            }
            Long mid = (Long) consultation.get("mid");
            if (Objects.isNull(mid)) {
                return ResultUtils.returnError("用户不存在");
            }
            //获取当前时间yyyy-MM--dd
            Date date = new Date();
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String dateString = formatter.format(currentTime);

            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("cid", id);
            paramMap.put("mid", memberId);
            paramMap.put("page", (page - 1) * size);
            paramMap.put("size", size);
            //评论列表,getCommentList获取到的时顶级评论，firt_reply_comment是第一评论的id
            List<Map<String, Object>> commentList = consultationCommentMapper.getCommentList(paramMap);

            commentList.forEach(e -> {
                //获取时间,当天时间保存是时间，不是当天保存的时时间

                if (!Objects.isNull(e.get("created_time")) && e.get("created_time").toString().contains(dateString)) {
                    e.put("created_time", (e.get("created_time").toString().substring(11, 16)));
                } else {
                    if (!Objects.isNull(e.get("created_time"))) {
                        e.put("created_time", (e.get("created_time").toString().substring(0, 11)));
                    }

                }

                if (!Objects.isNull(e.get("id"))) {
                    //子类评论展示2条
                    //获取前2条的数据
                    List arr = new ArrayList();
                    //顶级品论的id
                    List<Map> commentSonIdByPid = consultationCommentMapper.getCommentSonIdByPid((Long) e.get("id"));
                    if (commentSonIdByPid != null && commentSonIdByPid.size() > 0) {

                        for (int i = 0; commentSonIdByPid.size() > i; i++) {
                            Map<String, Object> sonCommentList = consultationCommentMapper.getSonCommentList((Long) commentSonIdByPid.get(i).get("id"));
                            if (!Objects.isNull(sonCommentList) && !Objects.isNull(sonCommentList.get("created_time")) && sonCommentList.get("created_time").toString().contains(dateString)) {
                                sonCommentList.put("created_time", (sonCommentList.get("created_time").toString().substring(11, 16)));
                            } else {
                                if (!Objects.isNull(sonCommentList) && !Objects.isNull(sonCommentList.get("created_time"))) {
                                    sonCommentList.put("created_time", (sonCommentList.get("created_time").toString().substring(0, 11)));
                                }
                            }
                            arr.add(sonCommentList);
                        }
                    }
                    log.info("son comment list :{}", arr);
                    e.put("firstComment", arr);

                }
            });
            result.put("commentList", commentList);
            result.put("loginuser", loginuser);
            log.info("首页获取咨询评论列表接口调用成功");
            return ResultUtils.returnSuccess("成功", result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info("首页获取咨询评论列表接口调用失败，方法入参{"+ "咨询id:"+id+",用户phone："+phone+"}");
            return ResultUtils.returnError("接口调用失败");
        }
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
    @Override
    public Result getTopAndTopAfterCommentByTopIdList(String id, String uuid, String phone, Integer page, Integer size) {
        log.info("回复列表方法执行，入参参数{"+"顶级的评论id："+ id+"当前用户的手机号："+phone+"}");
        String loginuser = "0";
        //获取当前时间yyyy-MM--dd
        Date date = new Date();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = formatter.format(currentTime);

        if (id==null) {
            return ResultUtils.returnError("参数错误，id不能为空");
        }
        try {
            Long memberId = 0L;
            if (StringUtils.isNotBlank(uuid) && StringUtils.isNotBlank(phone)) {
                Map<String, Object> paramMap = Maps.newHashMap();
                paramMap.put("uuid", uuid);
                paramMap.put("phone", phone);
                Member member = memberService.getMemberByIdAndUuid(paramMap);
                if (Objects.isNull(member)) {
                    return ResultUtils.returnError("用户信息不存在");
                }
                memberId = member.getId();
                loginuser = "1";
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("topcommentid", id);
            params.put("memberid", memberId);
            //顶级评论详情内容
            Map<String, Object> topcommentdetail = consultationCommentMapper.getTopConsultationCommentDetailById(params);
            if (topcommentdetail == null) {
                return ResultUtils.returnError("该评论不存在或已删除");
            }
            if (topcommentdetail.get("member_id") == null) {
                return ResultUtils.returnError("数据异常,评论信息未关联用户信息");
            }
            //截取时间
            if (!Objects.isNull(topcommentdetail.get("created_time")) && topcommentdetail.get("created_time").toString().contains(dateString)) {
                topcommentdetail.put("created_time", (topcommentdetail.get("created_time").toString().substring(11, 16)));
            } else {
                if (!Objects.isNull(topcommentdetail.get("created_time"))) {
                    topcommentdetail.put("created_time", (topcommentdetail.get("created_time").toString().substring(0, 11)));
                }

            }
            //标示符，评论内容是否为当前用户 0不是，1是
            topcommentdetail.put("self", 0);
            if (Long.valueOf(topcommentdetail.get("member_id").toString()) == memberId.longValue()) {
                topcommentdetail.put("self", 1);
            }
            params.put("startIndex", (page - 1) * size);
            params.put("endIndex", size);

            //顶级评论下的评论列表
            List<Map<String, Object>> afterlist = consultationCommentMapper.findTopAfterCommentListByTopId(params);

            for (Map<String, Object> after : afterlist) {
                //获取时间,当天时间保存是时间，不是当天保存的是时间
                if (!Objects.isNull(after.get("created_time")) && after.get("created_time").toString().contains(dateString)) {
                    after.put("created_time", (after.get("created_time").toString().substring(11, 16)));
                } else {
                    if (!Objects.isNull(after.get("created_time"))) {
                        after.put("created_time", (after.get("created_time").toString().substring(0, 11)));
                    }

                }
                after.get("created_time").toString();
                String aftercontent = after.get("content").toString();
                if (after.get("member_id") == null) {
                    return ResultUtils.returnError("数据异常,评论信息未关联用户信息");
                }
                if (Long.valueOf(after.get("member_id").toString()) == memberId.longValue()) {
                    after.put("self", 1);
                } else {
                    after.put("self", 0);
                }
            }
            topcommentdetail.put("replylist", afterlist);
            topcommentdetail.put("loginuser", loginuser);
            return ResultUtils.returnSuccess("查询成功", topcommentdetail);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info("回复列表接口调用异常");
            return ResultUtils.returnError("查询失败");
        }

    }
}
