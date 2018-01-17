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
@Service(version = "1.0.0")
@Transactional(readOnly = true)
@Component
public class ConsultationInfoServiceImpl implements ConsultationInfoService {


    private static Logger log = LoggerFactory.getLogger(ConsultationInfoServiceImpl.class);
    
    @DubboConsumer(version = "1.0.0",timeout = 30000)
    private MemberService memberService;
    @Autowired
    private ConsultationMapper consultationMapper;
    @Autowired
    private ConsultationCommentMapper consultationCommentMapper;

    @Override
    public Result getConsultationDetail(String id, String uuid, String phone, Integer page, Integer size) {
        String loginuser="0";
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
                loginuser="1";
            }
            //通过资讯id查询资讯数据
            Map<String, Object> consultation = consultationMapper.getConsultationById(id);
            if (Objects.isNull(consultation)) {
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

                if(!Objects.isNull(e.get("created_time")) && e.get("created_time").toString().contains(dateString)){
                    e.put("created_time", (e.get("created_time").toString().substring(11,16)));
                }else  {
                    if (!Objects.isNull(e.get("created_time"))) {
                        e.put("created_time", (e.get("created_time").toString().substring(0,11)));
                    }

                }

                if (!Objects.isNull(e.get("id"))){
                    //子类评论展示2条
                    //获取前2条的数据
                    List arr = new ArrayList();
                    //顶级品论的id
                    List<Map> commentSonIdByPid = consultationCommentMapper.getCommentSonIdByPid((Long)e.get("id"));
                    if(commentSonIdByPid!=null &&commentSonIdByPid.size()>0 ){

                        for(int i=0;commentSonIdByPid.size()>i;i++){
                            Map<String, Object> sonCommentList = consultationCommentMapper.getSonCommentList((Long)commentSonIdByPid.get(i).get("id"));
                            if(!Objects.isNull(sonCommentList) && !Objects.isNull(sonCommentList.get("created_time")) && sonCommentList.get("created_time").toString().contains(dateString)){
                                sonCommentList.put("created_time", (sonCommentList.get("created_time").toString().substring(11,16)));
                            }else  {
                                if (!Objects.isNull(sonCommentList) && !Objects.isNull(sonCommentList.get("created_time"))) {
                                    sonCommentList.put("created_time", (sonCommentList.get("created_time").toString().substring(0,11)));
                                }
                            }
                            arr.add(sonCommentList);
                        }
                    }
                    log.info("son comment list :{}",arr);
                    e.put("firstComment", arr);

                }
            });
            result.put("commentList", commentList);
            result.put("loginuser", loginuser);
            return ResultUtils.returnSuccess("成功", result);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return ResultUtils.returnError("接口调用失败");
        }
    }
}
