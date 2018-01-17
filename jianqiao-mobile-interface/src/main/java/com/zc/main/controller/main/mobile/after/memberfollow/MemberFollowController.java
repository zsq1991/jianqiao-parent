package com.zc.main.controller.main.mobile.after.memberfollow;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.Explosionproof;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
import com.zc.main.service.memberfollow.MemberFollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : wangxueyang[wxueyanghj@163.com]
 * @version ： 1.0.0
 * @package : com.zc.main.controller.main.mobile.after.memberfollow
 * @progect : jianqiao-parent
 * @Description : 会员关注接口
 * @Creation Date ：2018年01月16日14:02
 */
@Controller
@RequestMapping("mobile/after/memberfollow")
public class MemberFollowController {

    private static Logger logger = LoggerFactory.getLogger(MemberFollowController.class);

    @DubboConsumer(version = "1.0.0")
    private MemberFollowService memberFollowService;

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param mId  登陆者的id
     * @param page 页码
     * @param rows 页大小
     * @return
     * @create: 2018/1/16 14:11
     * @desc: 获取关注者的列表
     * @version 1.0.0
     */
    @RequestMapping(value = "focuslist", method = RequestMethod.POST)
    @ResponseBody
    public Result getFocusList(@MemberAnno Member member,
                               @RequestParam(value = "mId") Long mId,
                               @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                               @RequestParam(value = "rows", defaultValue = "10", required = false) int rows

    ) {
        logger.info("获取会员关注列表传入参数 --> mId:" + mId + " page: " + page + " rows: " + rows);
        if (page < 1) {
            page = 1;
        }
        if (rows < 1) {
            rows = 10;
        }
        page = (page - 1) * rows;
        logger.info("获取会员关注列表成功!");
        Result focusList = memberFollowService.getFocusList(mId, page, rows);
        return focusList;
    }

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param member 当前登录者
     * @param fId    被关注用户的id
     * @param mId    关注者的id
     * @return
     * @create: 2018/1/16 17:10
     * @desc: 关注用户接口
     * @version 1.0.0
     */
    @Explosionproof
    @RequestMapping(value = "focus", method = RequestMethod.POST)
    @ResponseBody
    public Result focusMember(@MemberAnno Member member,
                              @RequestParam(value = "fId") Long fId,
                              @RequestParam(value = "mId") Long mId) {
        logger.info("获取关注用户传入参数==>fid:" + fId + " mid:" + mId);
        Result focusMember = memberFollowService.focusMember(fId, mId);
        logger.info("获取关注用户成功!");
        return focusMember;
    }

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param member 当前登录者
     * @param mId    登录着的id
     * @param page   页码
     * @param rows   页大小
     * @return
     * @create: 2018/1/16 17:17
     * @desc: 粉丝列表
     * @version 1.0.0
     */
    @RequestMapping(value = "fanslist", method = RequestMethod.POST)
    @ResponseBody
    public Result getFansList(@MemberAnno Member member,
                              @RequestParam(value = "mId") Long mId,
                              @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                              @RequestParam(value = "rows", defaultValue = "10", required = false) int rows
    ) {
        logger.info("获取粉丝列表传入参数==》mid:" + mId + " page:" + page + " rows:" + rows);
        if (page < 1) {
            page = 1;
        }
        if (rows < 1) {
            rows = 10;
        }
        page = (page - 1) * rows;
        Result fansList = memberFollowService.getFansList(mId, page, rows);
        logger.info("获取粉丝列表成功！");
        return fansList;
    }
}
