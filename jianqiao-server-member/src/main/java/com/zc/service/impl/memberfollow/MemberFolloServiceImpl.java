package com.zc.service.impl.memberfollow;

import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.memberfollow.MemberFollow;
import com.zc.main.service.memberfollow.MemberFollowService;
import com.zc.mybatis.dao.MemberFollowMapper;
import com.zc.service.impl.member.MemberServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : wangxueyang[wxueyanghj@163.com]
 * @version ： 1.0.0
 * @package : com.zc.service.impl.memberfollow
 * @progect : jianqiao-parent
 * @Description : 用户相关信息  粉丝 关注
 * @Creation Date ：2018年01月16日18:11
 */
@Component
@Service(version = "1.0.0", interfaceClass = MemberFollowService.class)
@Transactional(readOnly = true)
public class MemberFolloServiceImpl implements MemberFollowService {

    private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberFollowMapper memberFollowMapper;

    @Override
    public Result focusMember(Long fId, Long mId) {
        logger.info("获取关注用户传入参数");
        return null;
    }

    @Override
    public Result getFansList(Long mId, int page, int size) {
        logger.info("获取粉丝列表传入参数==》mid:" + mId + " page:" + page + " size:" + size);

        return null;
    }

    @Override
    public Result getFocusList(Long mId, int page, int size) {
        logger.info("获取会员关注列表传入参数 --> mId:" + mId + " page: " + page + " size: " + size);
        Result result = new Result();
        try {
            if (mId == null) {
                return ResultUtils.returnError("请传入用户id");
            }
            List<Map> mIdList = memberFollowMapper.getFocusMIdList(mId, page, size);
            if (mIdList.size() <= 0) {
                return ResultUtils.returnError("还没有关注者");
            }
            List list = new ArrayList();
            for (int i = 0; mIdList.size() > i; i++) {
                String id = mIdList.get(i).get("member_id").toString();//被关注者id
                long longValue = Long.valueOf(id).longValue();
                //longValue是关注者id，被关注id是mId
                MemberFollow focusById = memberFollowMapper.getFocusById(mId, longValue);//第一个参数是被关注，第二个参数关注者
                Map fanList = memberFollowMapper.getFanList(longValue);//通过Id获取昵称和头像地址
                Map title = memberFollowMapper.getConsulatationTitleByMId(longValue);
                String titles = title == null ? "" : (String) title.get("title");
                fanList.put("title", titles);
                if (focusById == null) {
                    fanList.put("focus", "已关注");
                    fanList.put("focusType", 1);
                } else {
                    fanList.put("focus", "互相关注");
                    fanList.put("focusType", 2);
                }
                list.add(fanList);
            }
            result.setContent(list);
            logger.info("查询会员关注列表成功！");
        } catch (Exception e) {
            logger.info("查询会员关注列表异常"+e.getMessage());
            e.printStackTrace();
            return ResultUtils.returnError("获取会员关注列表异常!");
        }
        return result;
    }
}
