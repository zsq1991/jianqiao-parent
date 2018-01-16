package com.zc.impl.share;

import com.zc.common.core.date.DateUtils;
import com.zc.main.service.share.ShareAppService;
import com.zc.mybatis.dao.ShareMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
public class ShareAppServiceImpl implements ShareAppService {

    @Autowired
    private ShareMapper shareMapper;
    @Override
    public Map<String, Object> getConsultationnow(String id) {
        Map<String, Object> consultationnow = shareMapper.getConsultationnow(id);
        return consultationnow;
    }

    @Override
    public Map<String, Object> getConsultation(Long id) {
        Map<String, Object> consultation = shareMapper.getConsultation(id);
        return consultation;
    }

    @Override
    public Map<String, Object> getConsultationTop(String id) {
        Map<String, Object> consultationTop = shareMapper.getConsultationTop(id);
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
    public List<Map<String, Object>> getConsultationList(String s) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getFTDetailList(String s) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getConsultationDetail(String s) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getreplyDetail(String commentid) {
        return null;
    }

    @Override
    public Map<String, Object> getFTPLNum(String s) {
        return null;
    }

    @Override
    public Map<String, Object> getHelpAuthor(String id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getImageList(String id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getHelpAuthorIdList(String id) {
        return null;
    }

    @Override
    public Map<String, Object> getAuthorUserList(String bid) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAuthorList(String bid) {
        return null;
    }
}
