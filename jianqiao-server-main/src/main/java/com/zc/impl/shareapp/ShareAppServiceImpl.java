package com.zc.impl.shareapp;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.date.DateUtils;
import com.zc.main.service.shareapp.ShareAppService;
import com.zc.mybatis.dao.ShareAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @package : com.zc.impl.share
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月16日15:01
 */
@Service(version = "1.0.0",interfaceClass = ShareAppService.class)
@Component
@Transactional(readOnly = true)
public class ShareAppServiceImpl implements ShareAppService {

    @Autowired
    private ShareAppMapper shareAppMapper;
    @Override
    public Map<String, Object> getConsultationnow(String id) {
        Map<String, Object> consultationnow = shareAppMapper.getConsultationnow(id);
        return consultationnow;
    }

    @Override
    public Map<String, Object> getConsultation(Long id) {
        Map<String, Object> consultation = shareAppMapper.getConsultation(id);
        return consultation;
    }

    @Override
    public Map<String, Object> getConsultationTop(String id) {
        Map<String, Object> consultationTop = shareAppMapper.getConsultationTop(id);
        if (consultationTop != null) {
            // 处理时间格式
            String chidAlreadyTime = DateUtils.dateFormat((Date) consultationTop.get("createdTime"),
                    "yyyy/MM/dd HH:mm:ss");
            SimpleDateFormat chiddf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
            String chidNowTime = chiddf.format(new Date());
            String chidTime1 = chidAlreadyTime.subSequence(0, 10).toString();
            String chidTime2 = chidNowTime.subSequence(0, 10).toString();
            if (chidTime1.equals(chidTime2)) {// 同一天
                String chidcreatedTime = chidAlreadyTime.subSequence(11, 16).toString();// 截取当天
                // 时，分
                consultationTop.put("createdTime", chidcreatedTime);
            } else {// 不同一天
                String chidcreatedTime = chidAlreadyTime.subSequence(0, 10).toString();// 截取当天
                // 年，月，日
                consultationTop.put("createdTime", chidcreatedTime);
            }
        }
        return consultationTop;
    }

    @Override
    public List<Map<String, Object>> getConsultationList(String consultaionId) {
        List<Map<String, Object>> consultationList = shareAppMapper.getConsultationList(consultaionId);
        for (Map<String, Object> consultationInfo : consultationList) {
            // 处理时间格式
            String alreadyTime = DateUtils.dateFormat((Date) consultationInfo.get("createdTime"),
                    "yyyy/MM/dd HH:mm:ss");
            if (alreadyTime != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
                String nowTime = df.format(new Date());
                String time1 = alreadyTime.subSequence(0, 10).toString();
                String time2 = nowTime.subSequence(0, 10).toString();
                if (time1.equals(time2)) {// 同一天
                    String createdTime = alreadyTime.subSequence(11, 16).toString();// 截取当天
                    // 时，分
                    consultationInfo.put("createdTime", createdTime);
                } else {// 不同一天
                    String createdTime = alreadyTime.subSequence(0, 10).toString();// 截取当天
                    // 年，月，日
                    consultationInfo.put("createdTime", createdTime);
                }
            }
        }
        return consultationList;
    }

    @Override
    public List<Map<String, Object>> getFTDetailList(String id) {
        List<Map<String, Object>> ftDetailList = shareAppMapper.getFTDetailList(id);
        return ftDetailList;
    }

    @Override
    public List<Map<String, Object>> getConsultationDetail(String consultaionId) {
        List<Map<String, Object>> consultationDetail = shareAppMapper.getConsultationDetail(consultaionId);
        for (Map<String, Object> consultationInfo : consultationDetail) {
            // 处理时间格式
            String alreadyTime = DateUtils.dateFormat((Date) consultationInfo.get("createdTime"),
                    "yyyy/MM/dd HH:mm:ss");
            if (alreadyTime != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
                String nowTime = df.format(new Date());
                String time1 = alreadyTime.subSequence(0, 10).toString();
                String time2 = nowTime.subSequence(0, 10).toString();
                if (time1.equals(time2)) {// 同一天
                    String createdTime = alreadyTime.subSequence(11, 16).toString();// 截取当天
                    // 时，分
                    consultationInfo.put("createdTime", createdTime);
                } else {// 不同一天
                    String createdTime = alreadyTime.subSequence(0, 10).toString();// 截取当天
                    // 年，月，日
                    consultationInfo.put("createdTime", createdTime);
                }
            }
        }
        return consultationDetail;
    }

    @Override
    public List<Map<String, Object>> getreplyDetail(String commentId) {
        List<Map<String, Object>> getreplyDetail = shareAppMapper.getreplyDetail(commentId);
        for (Map<String, Object> consultationInfo : getreplyDetail) {
            // 处理时间格式
            String alreadyTime = DateUtils.dateFormat((Date) consultationInfo.get("createdTime"),
                    "yyyy/MM/dd HH:mm:ss");
            if (alreadyTime != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
                String nowTime = df.format(new Date());
                String time1 = alreadyTime.subSequence(0, 10).toString();
                String time2 = nowTime.subSequence(0, 10).toString();
                if (time1.equals(time2)) {// 同一天
                    String createdTime = alreadyTime.subSequence(11, 16).toString();// 截取当天
                    // 时，分
                    consultationInfo.put("createdTime", createdTime);
                } else {// 不同一天
                    String createdTime = alreadyTime.subSequence(0, 10).toString();// 截取当天
                    // 年，月，日
                    consultationInfo.put("createdTime", createdTime);
                }
            }
        }
        return getreplyDetail;
    }

    @Override
    public Map<String, Object> getFTPLNum(String id) {
        Map<String, Object> ftplNum = shareAppMapper.getFTPLNum(id);
        return ftplNum;
    }

    @Override
    public Map<String, Object> getHelpAuthor(String id) {
        Map<String, Object> helpAuthor = shareAppMapper.getHelpAuthor(id);
        String alreadyTime = DateUtils.dateFormat((Date) helpAuthor.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
        if (alreadyTime != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
            String nowTime = df.format(new Date());
            String time1 = alreadyTime.subSequence(0, 10).toString();
            String time2 = nowTime.subSequence(0, 10).toString();
            if (time1.equals(time2)) {// 同一天
                String createdTime = alreadyTime.subSequence(11, 16).toString();// 截取当天
                // 时，分
                helpAuthor.put("createdTime", createdTime);
            } else {// 不同一天
                String createdTime = alreadyTime.subSequence(0, 10).toString();// 截取当天
                // 年，月，日
                helpAuthor.put("createdTime", createdTime);
            }
        }
        return helpAuthor;
    }

    @Override
    public List<Map<String, Object>> getImageList(String id) {
        List<Map<String, Object>> imageList = shareAppMapper.getImageList(id);
        return imageList;
    }

    @Override
    public List<Map<String, Object>> getHelpAuthorIdList(String id) {
        List<Map<String, Object>> helpAuthorIdList = shareAppMapper.getHelpAuthorIdList(id);
        return helpAuthorIdList;
    }

    @Override
    public Map<String, Object> getAuthorUserList(String id) {
        Map<String, Object> authorUserList = shareAppMapper.getAuthorUserList(id);
        String alreadyTime = DateUtils.dateFormat((Date) authorUserList.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
        if (alreadyTime != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
            String nowTime = df.format(new Date());
            String time1 = alreadyTime.subSequence(0, 10).toString();
            String time2 = nowTime.subSequence(0, 10).toString();
            if (time1.equals(time2)) {// 同一天
                String createdTime = alreadyTime.subSequence(11, 16).toString();// 截取当天
                // 时，分
                authorUserList.put("createdTime", createdTime);
            } else {// 不同一天
                String createdTime = alreadyTime.subSequence(0, 10).toString();// 截取当天
                // 年，月，日
                authorUserList.put("createdTime", createdTime);
            }
        }
        return authorUserList;
    }

    @Override
    public List<Map<String, Object>> getAuthorList(String id) {
        List<Map<String, Object>> authorList = shareAppMapper.getAuthorList(id);
        return authorList;
    }
}
