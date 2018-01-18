package com.zc.service.impl.membermessageService;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.memberhelp.MemberHelp;
import com.zc.main.service.membermessage.MemberMessageService;
import com.zc.mybatis.dao.MemberMsgMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.message.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @package : com.zc.service.impl.membermessageService
 * @progect : jianqiao-parent
 * @Description :
 * @Created by :ZhaoJunBiao
 * @Creation Date ：2018年01月17日17:58
 */
@Service(version = "1.0.0")
@Component
@Transactional(readOnly = true)
public class MemberMessageServiceImpl implements MemberMessageService{

    @Autowired
    private MemberMsgMapper memberMessageMapper;

    @Override
    public Result saveMemberHelp(Member member, MemberHelp memberhelp, String caseId, String imgId) {
        Result result = new Result();
        if(StringUtils.isNotBlank(caseId)){
            // 查看caseId图片
            String[] split = caseId.split(",");
            for (int i=0;split.length>i;i++) {
                Attachment attachment = memberMessageMapper.getAttamentById(split[i]);
                if(null==attachment){
                    return ResultUtils.returnError("没有此病历图片！");
                }
            }
        }
        if(StringUtils.isNotBlank(imgId)){
            // 查看imgId图片
            String[] split2 = imgId.split(",");
            for (int i=0;split2.length>i;i++) {
                Attachment attachment2 = memberMessageMapper.getAttamentById(split2[i]);
                if(null==attachment2){
                    return ResultUtils.returnError("没有此影像图片！");
                }
            }
        }

        return result;
    }
}
