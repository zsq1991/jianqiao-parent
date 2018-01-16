package com.zc.main.controller.main.mobile.after.memberfollow;

import com.zc.common.core.annotation.MemberAnno;
import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;
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
        return null;
    }
}
