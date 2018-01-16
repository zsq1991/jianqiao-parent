package com.zc.service.impl.member;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.zc.main.entity.member.Member;
import com.zc.main.service.member.MemberService;
import com.zc.mybatis.dao.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@Service(version = "1.0.0")
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member getMerberById(Long memberId) {
        logger.info("----------查询用户开始----------");
        Member m = new Member();
        m.setId(memberId);
        Member member = memberMapper.findTById(m);
        logger.info("----------查询用户结束----------");
        return member;
    }

    @Override
    public Member getMemberByPhoneAndUuid(Map<String, Object> params) {
        logger.info("============根据ID和UUID查询用户开始,params={}", JSON.toJSONString(params));
        Member member = memberMapper.getMemberByPhoneAndUuid(params);
        logger.info("============根据ID和UUID查询用户结束,member={}", JSON.toJSONString(member));
        return member;
    }
}