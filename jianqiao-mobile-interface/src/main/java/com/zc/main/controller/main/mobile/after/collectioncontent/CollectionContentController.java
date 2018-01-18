package com.zc.main.controller.main.mobile.after.collectioncontent;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.zc.common.core.annotation.MemberAnno;
import com.zc.main.entity.member.Member;
import com.zc.main.service.collectioncontent.CollectionContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zc.common.core.result.Result;

/**
 * @author : wangxueyang[wxueyanghj@163.com]
 * @version ： 1.0.0
 * @package : com.zc.main.controller.main.mobile.after.collectioncontent
 * @progect : jianqiao-parent
 * @Description :  收藏接口
 * @Creation Date ：2018年01月17日11:34
 */
@Controller
@RequestMapping("mobile/after/collectioncontent")
public class CollectionContentController {
    private static Logger logger = LoggerFactory.getLogger(CollectionContentController.class);

    @DubboConsumer(version = "1.0.0")
    private CollectionContentService collectionContentService;

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     *
     * @param member 当前登录者
     * @param page   页码
     * @param rows   页大小
     * @return
     * @create: 2018/1/17 11:48
     * @desc: 收藏列表
     * @version 1.0.0
     */
    @RequestMapping(value = "mycollection-list", method = RequestMethod.POST)
    @ResponseBody
    public Result mycollection(
            @MemberAnno Member member,
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows) {
        logger.info("查询收藏列表传入参数==》member：" + member.toString() + "page：" + page + "rows" + rows);
        if (page < 1) {
            page = 1;
        }
        if (rows < 1) {
            rows = 10;
        }
        page = (page - 1) * rows;
        Result result = collectionContentService.mycollection(member, page, rows);
        logger.info("查询收藏列表成功!");
        return result;
    }
}
