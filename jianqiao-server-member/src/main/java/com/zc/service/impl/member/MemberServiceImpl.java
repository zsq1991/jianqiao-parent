package com.zc.service.impl.member;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
import com.zc.main.service.member.MemberService;
import com.zc.mybatis.dao.MemberAttachmentMapper;
import com.zc.mybatis.dao.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Service(version = "1.0.0",interfaceClass =MemberService.class )
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberAttachmentMapper memberAttachmentMapper;
    /**
     * @description 接口说明 修改用户信息
     * @author 王鑫涛
     * @date 17:14 2018/1/17
     * @version 版本号
     * @param member 用户
     * @return
     */
    @Override
    public int updateById(Member member) {
        int i = memberMapper.updateByPrimaryKeySelective(member);
        return i;
    }
    /**
     * @description 接口说明 根据id获取用户信息
     * @author 王鑫涛
     * @date 17:08 2018/1/17
     * @version 版本号
     * @param id 用户id
     * @return
     */
    @Override
    public Member findOne(Long id) {
        Member one = memberMapper.findOne(id);
        return one;
    }

    @Override
    public Member getMerberById(Long memberId) {
        logger.info("----------查询用户开始----------");
        Member member = memberMapper.selectByPrimaryKey(memberId);
        logger.info("----------查询用户结束----------");
        return member;
    }

    @Override
    public Result getAuthMember(Member member) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("uuid",member.getUuid());
        map.put("phone",member.getPhone());
        Map<String,Object> result = memberMapper.getMemberByIdAndUuid(map);
        if (!Objects.isNull(result)){
            List<Map<String,Object>> resultAttachment = memberAttachmentMapper.getMemberAttachment(member.getId());
            result.put("pics",resultAttachment);
            result.put("member",member);
        }
        return ResultUtils.returnSuccess("成功",result);
    }


    /**
     * @description ：检测是否是高级用户
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/17 13:46
     * @param memberId
     * @return
     */
    @Override
    public Member checkMemberById(Long memberId) {

        return memberMapper.checkMemberById(memberId);
    }

    @Override
    @Transactional
    public Member getMemberByPhoneAndUuid(Map<String, Object> params) {
        logger.info("============根据手机号和UUID查询用户开始,params={}", JSON.toJSONString(params));
        Member member = memberMapper.getMemberByPhoneAndUuid(params);
        logger.info("============根据手机号和UUID查询用户结束,member={}", JSON.toJSONString(member));
        return member;
    }
    @Override
    public Member getMemberByIdAndUuid(Map<String, Object> params) {
        Member member = new Member();
        Map<String,Object> map = memberMapper.getMemberByIdAndUuid(params);
        if (!Objects.isNull(map)){
            Object id = map.get("id");
            if (Objects.isNull(id)){
                return member;
            }
            member.setId(Long.valueOf(String.valueOf(id)));
            Object nickname = map.get("nickname");
            if (!Objects.isNull(nickname)){
                member.setNickname(String.valueOf(nickname));
            }
            Object name = map.get("name");
            if (!Objects.isNull(name)){
                member.setName(String.valueOf(name));
            }
            Object userType = map.get("user_type");
            if (!Objects.isNull(userType)){
                member.setUserType(Integer.valueOf(String.valueOf(userType)));
            }
            Object phone = map.get("phone");
            if (!Objects.isNull(phone)){
                member.setPhone(String.valueOf(phone));
            }

            Object card = map.get("card");
            if (!Objects.isNull(card)){
                member.setCard(String.valueOf(card));
            }

            Object status = map.get("status");
            if (!Objects.isNull(status)){
                member.setStatus(Integer.valueOf(status.toString()));
            }
        }
        return member;
    }
}
