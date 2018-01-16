package com.zc.impl.consultationattachment;


import com.alibaba.dubbo.config.annotation.Service;
import com.zc.main.service.consultationattachment.ConsultationAttachmentService;
import com.zc.mybatis.dao.ConsultationAttachmentMapper;
import com.zc.mybatis.dao.ConsultationMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author dinglanlan
 * @version 咨询图片
 * @ClassName ConsultationAttachmentServiceImpl
 * Date:     2017年5月10日  17:45:41 <br/>
 * @see
 */

@Component
@Service(version = "1.0.0")
@Transactional(readOnly = true)
public class ConsultationAttachmentServiceImpl implements ConsultationAttachmentService {

    private static Logger log = LoggerFactory.getLogger(ConsultationAttachmentServiceImpl.class);

    @Autowired
    private ConsultationAttachmentMapper consultationAttachmentDao;

    @Override
    public List<Map<String, Object>> findConsultationAttachmentByConsultationId(Long id) {

        return consultationAttachmentDao.getConsultationAttachmentCoverAddressByConsultationId(id);
    }


    @Override
    public Map<String, Object> findDetailContentByConsultationId(Long id) {

        return consultationAttachmentDao.findDetailContentByConsultationId(id);
    }

    @Override
    public String findConsultationAttachmentVideoAddressByConsultationId(Long id) {
        // TODO Auto-generated method stub
        return consultationAttachmentDao.findConsultationAttachmentVideoAddressByConsultationId(id);
    }

}
