package com.zc.service.impl.membersearchconsultation;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.membersearchconsultation.MemberSearchConsultation;
import com.zc.main.service.member.MemberService;
import com.zc.main.service.membersearchconsultation.MembersearchconsultationService;
import com.zc.mybatis.dao.MemberSearchConsultationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version ： 1.0.0
 * @package : com.zc.service.impl.membersearchconsultation
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : wangxueyang[wxueyanghj@163.com]
 * @Creation Date ：2018年01月17日17:09
 */
@Component
@Service(version = "1.0.0", interfaceClass = MembersearchconsultationService.class)
@Transactional(readOnly = true)
public class MembersearchconsultationServiceImpl implements MembersearchconsultationService {

    private Logger logger = LoggerFactory.getLogger(MembersearchconsultationServiceImpl.class);

    @Autowired
    private MemberSearchConsultationMapper memberSearchConsultationMapper;

    @DubboConsumer(version = "1.0.0", timeout = 30000)
    private MemberService memberService;

    @Override
    public List<Map<String, Object>> findSearchKeywordByMember(Map<String, Object> map) {
        return null;
    }

    @Override
    public Integer getSearchConsultationByInfo(HashMap<String, Object> map) {
        return memberSearchConsultationMapper.getSearchConsultationByInfo(map);
    }

    @Override
    public Result saveMemberSearchConsultation(Long id, String info) {
        logger.info("保存用户检索关键词出传入参数==》 id："+ id +" info:"+ info);
        Result result = new Result();
        Member member = memberService.getMerberById(id);//查询用户
        if (null == member) {
            return ResultUtils.returnError("用户不存在");
        }
        MemberSearchConsultation memberSearchConsultation = new MemberSearchConsultation();
        memberSearchConsultation.setKeyword(info);
        memberSearchConsultation.setMemberId(id);
        try {
            memberSearchConsultationMapper.insertSearchConsultation(memberSearchConsultation);
            result.setCode(1);
            result.setMsg("保存历史搜索关键词成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            return ResultUtils.returnError("保存历史搜索关键词失败");
        }
        logger.info("保存用户检索关键词成功!");
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteKeys(Member member) {
        try {
            memberSearchConsultationMapper.deleteAll(member.getId());
            return ResultUtils.returnSuccess("删除成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(),e);
            return ResultUtils.returnError("删除失败");
        }
    }
}
