package com.zc.service.impl.consultationcommentfabulous;


import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.service.comment.ConsultationCommentService;
import com.zc.main.service.consultationcommentfabulous.ConsultationCommentFabulousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(version = "1.0.0", interfaceClass = ConsultationCommentFabulousService.class)
public class ConsultationCommentFabulousServiceImpl implements ConsultationCommentFabulousService {

    @DubboConsumer(version = "1.0.0",timeout = 30000,check = false)
    private ConsultationCommentService consultationCommentService;

   /* @Autowired
    private RpcConsultationCommentFabulousService rpcConsultationCommentFabulousService;*/

    @Override
    public Result saveConsultationCommentFabulous(Long commentid, Long memberid, Integer type) {
        // TODO Auto-generated method stub
        if (commentid == null || memberid == null || type == null) {
            return ResultUtils.returnError("参数错误,commentid不能为空");
        }
        if (type != 1 && type != 2) {
            return ResultUtils.returnError("点赞参数非法值错误");
        }
        if (consultationCommentService.findHasConsultationCommentById(commentid) == 0) {
            return ResultUtils.returnError("该咨询评论不存在或已删除，请刷新界面重试");
        }
        //return rpcConsultationCommentFabulousService.saveConsultationCommentFabulous(commentid, memberid, type);
        return  null;
    }

}
