package com.zc.service.impl.consultationattachment;


import com.alibaba.dubbo.config.annotation.Service;
import com.common.util.statuscodeenums.StatusCodeEnums;
import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.member.Member;
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
@Service(version = "1.0.0",interfaceClass =ConsultationAttachmentService.class )
@Transactional(readOnly = true,rollbackFor=Exception.class)
public class ConsultationAttachmentServiceImpl implements ConsultationAttachmentService {

    private static Logger logger = LoggerFactory.getLogger(ConsultationAttachmentServiceImpl.class);

    @Autowired
    private ConsultationAttachmentMapper consultationAttachmentDao;

    @Autowired
    private ConsultationMapper consultationMapper;

    /**
     * @description方法说明 父级咨询信息
     * @author 王鑫涛
     * @date  15:57  2018/1/19
     * @version 版本号
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> getConsultationAttachmentByConsultationType(Map<String, Object> map) {
        return consultationAttachmentDao.getConsultationAttachmentByConsultationType(map);
    }

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

    /**
     * 个人中心 <我的发布>
     * @author huangxin
     * @data 2018/1/17 11:30
     * @Description: 个人中心 <我的发布>
     * @Version: 1.0.0
     * @param type 类型
     * @param member 用户
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result getConsultationAttachmentByConsultation(String type, Member member, Integer page, Integer size) {
        logger.info("===============进入个人中心我的发布列方法===============");
        if (StringUtils.isBlank(type) || Objects.isNull(member)){
            logger.info("===============用户信息异常===============");
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        }
        if (!"1,2,3,4".contains(type)){
            logger.info("===============type值类型异常===============");
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        }
        Integer curPage = (page-1)*size;
        logger.info("===============针对type值进行操作===============");
        switch (type){
            //访谈
            case "1":
                logger.info("===============进入访谈列表查询===============");
                Map<String,Object> map = Maps.newHashMap();
                map.put("page",curPage);
                map.put("size",size);
                map.put("mid",member.getId());
                map.put("type","0");
                //已发布
                List<Map<String,Object>> pconsultations= consultationAttachmentDao.getConsultationAttachmentByConsultationType(map);
                pconsultations.stream().forEach(e->{
                    Long id = Long.parseLong(e.get("id").toString());
                    Map<String,Object> mapParams = Maps.newHashMap();
                    mapParams.put("pid",id);
                    mapParams.put("page",0);
                    mapParams.put("size",3);
                    List<Map<String,Object>> sconsultations= consultationMapper.getConsultationByMap(mapParams);
                    sconsultations.forEach(ee->{
                        Long sid = Long.parseLong(ee.get("id").toString());
                        //封面图
                        ee.put("covers",consultationAttachmentDao.getConsultationAttachmentCoverAddressByConsultationId(sid));
                    });
                    e.put("rows",sconsultations);
                });
                logger.info("===============访谈列表查询结束===============");
                return ResultUtils.returnSuccess(StatusCodeEnums.SUCCESS.getMsg(),pconsultations);
            //口述
            case "2":
                logger.info("===============进入口述列表查询===============");
                Map<String,Object> map2 = Maps.newHashMap();
                map2.put("page",curPage);
                map2.put("size",size);
                map2.put("mid",member.getId());
                map2.put("type","2");
                //已发布
                List<Map<String,Object>> pconsultations2= consultationAttachmentDao.getConsultationAttachmentByConsultationType(map2);
                pconsultations2.stream().distinct().forEach(e->{
                    Long id = Long.parseLong(e.get("id").toString());
                    Map<String,Object> mapParams = Maps.newHashMap();
                    mapParams.put("pid",id);
                    mapParams.put("page",0);
                    mapParams.put("size",3);
                    List<Map<String,Object>> sconsultations= consultationMapper.getConsultationByMap(mapParams);
                    sconsultations.stream().distinct().forEach(ee->{
                        Long sid = Long.parseLong(ee.get("id").toString());
                        //封面图
                        ee.put("covers",consultationAttachmentDao.getConsultationAttachmentCoverAddressByConsultationId(sid));
                    });
                    e.put("rows",sconsultations);
                });
                logger.info("===============口述列表查询结束===============");
                return ResultUtils.returnSuccess(StatusCodeEnums.SUCCESS.getMsg(),pconsultations2);
            //求助
            case "3":
                logger.info("===============进入求助列查询方法===============");
                Map<String,Object> map3 = Maps.newHashMap();
                map3.put("page",curPage);
                map3.put("size",size);
                map3.put("mid",member.getId());
                map3.put("type","4");
                List<Map<String,Object>> helpConsultations= consultationMapper.getConsultationByMap(map3);
                helpConsultations.forEach(e->{
                    Long sid = Long.parseLong(e.get("id").toString());
                    //封面图
                    e.put("covers",consultationAttachmentDao.getConsultationAttachmentCoverAddressByConsultationId(sid));
                });
                logger.info("===============求助列表查询结束===============");
                return ResultUtils.returnSuccess(StatusCodeEnums.SUCCESS.getMsg(),helpConsultations);
            //分享
            case "4":
                logger.info("===============进入分享查询方法===============");
                Map<String,Object> map4 = Maps.newHashMap();
                map4.put("page",curPage);
                map4.put("size",size);
                map4.put("mid",member.getId());
                map4.put("type","6");
                List<Map<String,Object>> shareConsultations= consultationMapper.getConsultationByMap(map4);
                shareConsultations.forEach(e->{
                    Long sid = Long.parseLong(e.get("id").toString());
                    //封面图
                    e.put("covers",consultationAttachmentDao.getConsultationAttachmentCoverAddressByConsultationId(sid));
                    //详情
                });
                logger.info("===============分享列表查询结束=============== ");
                return ResultUtils.returnSuccess(StatusCodeEnums.SUCCESS.getMsg(),shareConsultations);
            default:
        }
        return null;
    }

    /**
     * 封面图片
     * @author huangxin
     * @data 2018/1/18 15:18
     * @Description: 封面图片
     * @Version: 1.0.0
     * @param sid 资讯id
     * @return
     */
    @Override
    public List<Map<String, Object>> getConsultationAttachmentDetailByConsultation(Long sid) {
        logger.info("=============封面图片=============");
        return consultationAttachmentDao.getConsultationAttachmentDetailByConsultation(sid);
    }

    /**
     * 内容详情
     * @author huangxin
     * @data 2018/1/18 15:21
     * @Description: 内容详情
     * @Version: 3.2.0
     * @param mid 资讯id
     * @return
     */
    @Override
    public List<Map<String, Object>> getConsultationAttachmentCoverAddressByConsultationId(Long mid) {
        logger.info("=============内容详情=============");
        return consultationAttachmentDao.getConsultationAttachmentCoverAddressByConsultationId(mid);
    }

    /**
     * 专题
     * @author huangxin
     * @data 2018/1/18 16:35
     * @Description: 专题
     * @Version: 3.2.0
     * @param pid id
     * @param lastType 类型
     * @return
     */
    @Override
    public Map<String, Object> getParentConsultationDetail(long pid, Integer lastType) {
        logger.info("=============专题=============");
        return consultationAttachmentDao.getParentConsultationDetail(pid,lastType);
    }

}
