package com.zc.service.impl.comment;


import com.alibaba.dubbo.config.annotation.Service;
import com.zc.main.service.comment.ConsultationCommentService;
import com.zc.mybatis.dao.ConsultationCommentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 咨询评论
 *
 * @author Administrator
 */
@Component
@Service(version = "1.0.0", interfaceClass = ConsultationCommentService.class)
@Transactional(readOnly = true)
public class ConsultationCommentServiceImpl implements ConsultationCommentService {

    @Autowired
    private ConsultationCommentMapper consultationCommentMapper;

    @Override
    public int findHasConsultationCommentById(Long commentid) {
        return consultationCommentMapper.findHasConsultationCommentById(commentid);
    }
}
