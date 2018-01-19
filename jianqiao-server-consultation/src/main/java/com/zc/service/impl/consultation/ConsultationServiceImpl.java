package com.zc.service.impl.consultation;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.statuscodeenums.StatusCodeEnums;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zc.common.core.date.DateUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.attachment.Attachment;
import com.zc.main.entity.collectionconsultation.CollectionConsultation;
import com.zc.main.entity.consultation.Consultation;
import com.zc.main.entity.consultationattachment.ConsultationAttachment;
import com.zc.main.entity.member.Member;
import com.zc.main.entity.membermsg.MemberMsg;
import com.zc.main.service.collectioncontent.CollectionContentService;
import com.zc.main.service.comment.ConsultationCommentService;
import com.zc.main.service.consultation.ConsultationService;
import com.zc.main.service.consultationattachment.ConsultationAttachmentService;
import com.zc.main.service.member.MemberService;
import com.zc.main.service.membermessage.MemberMessageService;
import com.zc.main.service.membersearchconsultation.MembersearchconsultationService;
import com.zc.main.service.membermsg.MemberMsgService;
import com.zc.mybatis.dao.Attachment.AttachmentMapper;
import com.zc.mybatis.dao.ConsultationAttachmentMapper;
import com.zc.mybatis.dao.ConsultationMapper;
import com.zc.mybatis.dao.collectionConsulation.CollectionConsulationMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Service(version = "1.0.0", interfaceClass = ConsultationService.class)
@Transactional(readOnly = true)
public class ConsultationServiceImpl implements ConsultationService {

    private static final Logger logger = LoggerFactory.getLogger(ConsultationServiceImpl.class);

    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private MemberService memberService;

    @Autowired
    private ConsultationMapper consultationMapper;
    @Autowired
    private CollectionConsulationMapper collectionConsulationMapper;
    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private CollectionContentService collectionContentService;
    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private ConsultationAttachmentService consultationAttachmentService;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private ConsultationAttachmentMapper consultationAttachmentMapper;
    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private MemberMsgService memberMsgService;
    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private MemberMessageService memberMessageService;
    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private ConsultationCommentService consultationCommentService;

    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private MembersearchconsultationService membersearchconsultationService;
    /**
     * @description 接口说明 删除资讯===用户中心
     * @author 王鑫涛
     * @date 9:08 2018/1/19
     * @version 版本号
     * @param id 资讯id
     * @param member 用户
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public Result deleteConsultationById(Long id, Member member) {
        try {
            logger.info("==============================进入删除资讯方法============================");
            Consultation ct = consultationMapper.getOne(id);
            if (Objects.isNull(ct)){
                return ResultUtils.returnError("信息不存在");
            }
            if(!Objects.equals(ct.getMemberId(),member.getId())){
                return ResultUtils.returnError("操作异常");
            }
            //0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
            Integer type = ct.getType();
            if ("5".equals(String.valueOf(type)) || "1".equals(String.valueOf(type)) || "3".equals(String.valueOf(type))){
                Consultation parentConsultation = consultationMapper.getOne(ct.getConsultationId());
                if (Objects.isNull(parentConsultation)){
                    return ResultUtils.returnError("信息不存在");
                }
                //收藏
                Long collectNum = Objects.isNull(parentConsultation.getCollectNum())?0L:parentConsultation.getCollectNum();
                Long sonCollectNum = Objects.isNull(ct.getCollectNum())?0L:ct.getCollectNum();
                //点赞
                Long fabulousNum = Objects.isNull(parentConsultation.getFabulousNum())?0L:parentConsultation.getFabulousNum();
                Long sonFabulousNum = Objects.isNull(ct.getFabulousNum())?0L:ct.getFabulousNum();
                if ("5".equals(String.valueOf(type))){
                    Integer num = Objects.isNull(parentConsultation.getNum())?1:parentConsultation.getNum();
                    parentConsultation.setNum(num-1);
                }
                //============================================================修改所有的有关咨询的收藏数@author wudi============================================================	//
                logger.info("-------------------------------获取所有的memberId--------------------------");
                List<Map> memberIdByConsultationId = collectionConsulationMapper.getMemberIdByConsultationId(id);//获取所有的memberId
                logger.info("通过资讯id："+id);
                logger.info("进入删除循环通过资讯ID获取关联数据："+memberIdByConsultationId);
                if (memberIdByConsultationId.size()>0) {

                    for (Map map : memberIdByConsultationId) {

                        Long mId =(Long)map.get("mId");
                        logger.info("删除资讯的时候获取的mId"+mId);
                        Member findOne = memberService.findOne(mId);
                        Long conNumber=findOne.getConsulationNum()==null?0:(Long)(findOne.getConsulationNum());

                        if (conNumber>0) {
                            findOne.setConsulationNum(conNumber-1);
                            logger.info("``````````````````修改发布资讯数量···············");
                            memberService.updateById(findOne);
                        }

                        Long	 collectionId =(Long)map.get("collectionId");
                        logger.info("删除资讯的时候获取需要修改的收藏的资讯id："+collectionId);
                        CollectionConsultation collection = collectionContentService.findOne(collectionId);
                        collection.setType(1);//0收藏   1取消收藏
                        collectionContentService.updateById(collection);
                    }
                }
                parentConsultation.setCollectNum(collectNum-sonCollectNum);
                parentConsultation.setFabulousNum(fabulousNum-sonFabulousNum);
                logger.info("-----------修改发布数量和点赞数量-------------");
                consultationMapper.updateById(parentConsultation);
            }
            //@wudi update
            if("2".equals(String.valueOf(type)) || "0".equals(String.valueOf(type))){
                //主题删除时，删除对应的内容
                try {
                    //============================================================修改所有的有关咨询的收藏数包含主题下所有的资讯@author wudi============================================================	//
                    logger.info("删除主题的资讯："+id);
                    List<Map> consultationIdAllByconsultationId = collectionConsulationMapper.getConsultationIdAllByconsultationId(id);//id
                    if (consultationIdAllByconsultationId.size()>0) {
                        for (Map maps : consultationIdAllByconsultationId) {

                            List<Map> memberIdByConsultationId = collectionConsulationMapper.getMemberIdByConsultationId((Long)maps.get("id"));//获取所有的memberId
                            for (Map map : memberIdByConsultationId) {

                                Long mId =(Long)map.get("mId");
                                logger.info("删除收藏时关联的用户id："+mId);
                                Member findOne = memberService.findOne(mId);
                                Long conNumber=findOne.getConsulationNum()==null?0:(Long)(findOne.getConsulationNum());

                                if (conNumber>0) {
                                    findOne.setConsulationNum(conNumber-1);
                                    memberService.updateById(findOne);
                                }
                                logger.info("获取资讯id");
                                Long	 collectionId =(Long)map.get("collectionId");
                                CollectionConsultation collection = collectionContentService.findOne(collectionId);
                                collection.setType(1);//0收藏   1取消收藏
                                collectionContentService.updateById(collection);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResultUtils.returnError("删除资讯信息失败");
                }
                //主题的时候删除关联的收藏
                consultationMapper.updateConsultationByParentId(ct.getId());
            }
            if ("4".equals(String.valueOf(type)) || "6".equals(String.valueOf(type))) {//分享和求助
                //============================================================修改所有的有关咨询的收藏数@author wudi============================================================	//
                List<Map> memberIdByConsultationId = collectionConsulationMapper.getMemberIdByConsultationId(id);//获取所有的memberId
                logger.info("通过资讯id："+id);
                logger.info("进入删除循环通过资讯ID获取关联数据："+memberIdByConsultationId);
                if (memberIdByConsultationId.size()>0) {

                    for (Map map : memberIdByConsultationId) {

                        Long mId =(Long)map.get("mId");
                        logger.info("删除资讯的时候获取的mId"+mId);
                        Member findOne = memberService.findOne(mId);
                        Long conNumber=findOne.getConsulationNum()==null?0:(Long)(findOne.getConsulationNum());

                        if (conNumber>0) {
                            findOne.setConsulationNum(conNumber-1);
                            memberService.updateById(findOne);
                        }

                        Long	 collectionId =(Long)map.get("collectionId");
                        logger.info("删除资讯的时候获取需要修改的收藏的资讯id："+collectionId);
                        CollectionConsultation collection = collectionContentService.findOne(collectionId);
                        collection.setType(1);//0收藏   1取消收藏
                        collectionContentService.updateById(collection);
                    }
                }
            }

            ct.setIsDelete(1);
            consultationMapper.updateById(ct);
            logger.info("===============================删除资讯方法结束==================");
            return ResultUtils.returnSuccess("删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResultUtils.returnError("删除失败");
        }
    }

    private static boolean  checkConsultation(String content,String opType){
        boolean flag = true;
        JSONObject jsonObject = JSONObject.parseObject(content);
        if (Objects.isNull(jsonObject)){
            return false;
        }
        //修改
        if("2".equals(opType)){
            Long id = jsonObject.getLong("id");
            if (Objects.isNull(id)){
                return false;
            }
        }
        //获取保存类型 0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
        String type =jsonObject.getString("type");
        if (StringUtils.isBlank(type)){
            return false;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("content");
        if (!"0,2".contains(type)){
            //内容
            if (Objects.isNull(jsonArray)){
                return false;
            }
        }
        String title = jsonObject.getString("title");
        if (!"5".equals(String.valueOf(type)) && StringUtils.isBlank(title)){
            return false;
        }
        //0是访谈主题  2口述主题
        if ("0,2".contains(type)){
            //封面主题
            String covers = jsonObject.getString("covers");
            if (StringUtils.isBlank(covers)){
                return false;
            }
            if(covers.split(",").length!=1){
                return false;
            }
            //简介
            String descr =jsonObject.getString("descr");
            if (StringUtils.isBlank(descr)){
                return false;
            }
        }

        //对应咨询ID
        Long id = jsonObject.getLong("id");
        if ("1,3,5".contains(type)) {
            if (Objects.isNull(id)){
                return false;
            }
        }
        //关联咨询ID
		/*Long pid = jsonObject.getLong("pid");
		if ("2".equals(opType) && "1,3,5".contains(type)) {
			if (Objects.isNull(pid)){
				return false;
			}
		}*/
        //图片类型 0 ,1,2
        String modelType =jsonObject.getString("modelType");
        //4求助 5回答  6分享
        if ("1,3,4,5,6".contains(type)){
            if (StringUtils.isBlank(modelType)){
                return false;
            }

            // 图片类型 0 ,1,2
            if (!"0,1,2".contains(modelType)){
                return false;
            }
            //封面主题
            String covers = jsonObject.getString("covers");
            //单、三图模式
            if ("1,2".contains(modelType)){
                //封面主题
                if (StringUtils.isBlank(covers)){
                    return false;
                }
            }
        }
        if (!Objects.isNull(jsonArray)){
            int size = jsonArray.size();
            for (int i=0;i<size;i++){
                JSONObject detail = jsonArray.getJSONObject(i);
                if (!Objects.isNull(detail)){
                    //内容类型
                    String contentType = detail.getString("type");
                    if (StringUtils.isBlank(contentType)){
                        return false;
                    }
                    Integer sortNum= detail.getInteger("sortNum");
                    if (Objects.isNull(sortNum)){
                        return false;
                    }
                    String text = detail.getString("detail");
                    String attachmentId = detail.getString("attachmentId");
                    //文本
                    if ("0".equals(contentType) && StringUtils.isBlank(text)){
                        return false;
                    }
                    //图片、视频
                    if ("1,3".contains(contentType) && StringUtils.isBlank(attachmentId)){
                        return false;
                    }
                }
            }
        }
        return flag;
    }
    /**
     * @description 接口说明 发布资讯===用户中心
     * @author 王鑫涛
     * @date 9:08 2018/1/19
     * @version 版本号
     * @param content 资讯内容
     * @param member 用户
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public Result addConsultation(String content, Member member) {
        //type = 0是访谈主题 2口述主题 1访谈内容 3口述内容 5回答  4求助 6分享
        //topicType : 1 :图文 2：视频
        logger.info("------------进入添加资讯方法------------------");
        logger.info("content:{},member:{}",content,member);
        logger.info("判断参数是否为空");
        if (StringUtils.isBlank(content) || Objects.isNull(member)){
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        }
        try {
            if (!checkConsultation(content,"1")){
                return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
            }
            JSONObject jsonObject = JSONObject.parseObject(content);//获取总的数据
            if (Objects.isNull(jsonObject)){
                return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
            }
            //获取保存类型 0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
            String type =jsonObject.getString("type");
            JSONArray contentArray = jsonObject.getJSONArray("content");
            if (!"0,2".contains(type)){
                if (Objects.isNull(contentArray)){
                    return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
                }
            }
            logger.info("=================获取用户类型===============");
            Integer userType = member.getUserType();//0普通 1认证后用户可以发布访谈 口述 2:认证中
            //非高级用户
            if (!"1".equals(String.valueOf(userType)) && "0,1,2,3".contains(type)){
                return ResultUtils.returnError("非高级用户,不能发布信息.");
            }
            String title=null;
            //获取保存类型 0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
            if (!"5".equals(String.valueOf(type))){
                title = jsonObject.getString("title");
            }
            Consultation consultation = new Consultation();

            consultation.setAuthorInfo(member.getNickname());
            if ("5".equals(type)){//给求助的评论是回答
                //审核通过
                consultation.setStatus(2);
                //求助ID
                Long helpId = jsonObject.getLong("id");
                logger.info("-----------------根据资讯id获取资讯信息------------------");
                Consultation helpConsultation = consultationMapper.getOne(helpId);
                if (!Objects.isNull(helpConsultation)){
                    Integer num = Objects.isNull(helpConsultation.getNum())?0:helpConsultation.getNum();
                    helpConsultation.setNum(num+1);
                    logger.info("-----------------修改主题下面的内容总个数------------------");
                    consultationMapper.updateById(helpConsultation);
                }


            } else {
                //审核中
                consultation.setStatus(1);
            }
            if (StringUtils.isNotBlank(title)){
                consultation.setTitle(title);
            }
            consultation.setMemberId(member.getId());
            consultation.setIsDelete(0);
            consultation.setType(Integer.valueOf(type));
            Long id = jsonObject.getLong("id");
            if (!Objects.isNull(id)){
                Consultation sonConsultation =consultationMapper.getOne(id);
                logger.info("获取资讯"+sonConsultation);
                consultation.setConsultationId(sonConsultation.getId());
            }
            Integer modelType = jsonObject.getInteger("modelType");//模式类型  0无图  1单图 2三图
            if (!Objects.isNull(modelType)){
                consultation.setModelType(modelType);
            }
            Integer topicType = jsonObject.getInteger("topicType");
            if (!Objects.isNull(topicType)){
                consultation.setTopicType(topicType);
            }
            String descr = jsonObject.getString("descr");
            if (StringUtils.isNotBlank(descr)){
                consultation.setDetailSummary(descr);
            }
            //==============================================资讯的添加返回的json，covers封面，维护到consultation附件表中==============================================================
           logger.info("========================添加到资讯表中==============================");
            Long save = consultationMapper.save(consultation);
            logger.info("========================================添加是否成功"+save);
            String covers= jsonObject.getString("covers");
            if (StringUtils.isNotBlank(covers)){
                String[] ids = covers.split(",");
                Arrays.stream(ids).forEach(e->{
                    logger.info("===============================根据id获取附件表中的信息===============================");
                    Attachment attachment = attachmentMapper.findOne(Long.valueOf(e));
                    if (!Objects.isNull(attachment)){
                        ConsultationAttachment consultationAttachment = new ConsultationAttachment();
                        consultationAttachment.setAddress(attachment.getAddress());
                        consultationAttachment.setName(attachment.getName());
                        consultationAttachment.setConsultation(consultation.getId());
                        consultationAttachment.setAttachment(attachment.getId());
                        if (!Objects.isNull(topicType) && "2".equals(topicType)){
                            consultationAttachment.setCover(2);
                        }else{
                            consultationAttachment.setCover(1);
                        }
                        logger.info("===============================资讯信息放入附件表中===============================");
                        consultationAttachmentMapper.insert(consultationAttachment);
                    }
                });
            }
//=====================================================资讯的添加contentArray来自返回json。content==========================================================================
            if (!Objects.isNull(contentArray)){
                int size = contentArray.size();
                for( int i=0;i<size;i++) {
                    ConsultationAttachment consultationAttachment = new ConsultationAttachment();
                    JSONObject obj = contentArray.getJSONObject(i);
                    String attachmentId = obj.getString("attachmentId");
                    if (StringUtils.isNotBlank(attachmentId)){
                        logger.info("------------------------获取附件信息------------------");
                        Attachment attachment = attachmentMapper.findOne(Long.valueOf(attachmentId));
                        if (Objects.isNull(attachment)){
                            return ResultUtils.returnError("附件不存在");
                        }
                        consultationAttachment.setAddress(attachment.getAddress());
                        consultationAttachment.setName(attachment.getName());
                        consultationAttachment.setAttachment(attachment.getId());
                    }
                    //type:  0文本  1分割线  2图片 3视频
                    String operationType = obj.getString("type");
                    consultationAttachment.setType(Integer.valueOf(operationType));
                    consultationAttachment.setConsultation(consultation.getId());
                    Integer sortNum = obj.getInteger("sortNum");
                    consultationAttachment.setSortNum(sortNum);
                    String text = obj.getString("detail");
                    if (StringUtils.isNotBlank(text)){
                        consultationAttachment.setDetailContent(text);
                    }
                    logger.info("--------------------将资讯附件信息放入资讯附件表中-------------------");
                    consultationAttachmentMapper.insert(consultationAttachment);
                }
            }
            Map<String,Object> result = Maps.newHashMap();
            result.put("cid",consultation.getId());
            result.put("type",consultation.getType());
            if ("5".equals(type)){
                //==========================================================维护到MemberMsg表中===============================================
                Long helpId = jsonObject.getLong("id");//求助的id
                MemberMsg memberMsg = new MemberMsg();
                memberMsg.setConsultationId(consultation.getId());
                memberMsg.setCreatedTime(new Date());
                memberMsg.setUpdateTime(new Date());
                memberMsg.setMemberId(consultationMapper.getOne(helpId).getMemberId());
                memberMsg.setMemberBaseId(member.getId());//评论者的id
                memberMsg.setType(4);//资讯评论中状态
                memberMsgService.save(memberMsg);
                logger.info("---------------------------------发布资讯方法结束----------------------------");
                return ResultUtils.returnSuccess("发布成功",result);
            }
            return ResultUtils.returnSuccess("提交成功,内容正在审核中",result);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
        }
    }
    /**
     * @description 接口说明 修改资讯
     * @author 王鑫涛
     * @date 9:08 2018/1/19
     * @version 版本号
     * @param content 资讯内容
     * @param member 用户
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public Result updateConsultation(String content, Member member) {
        try {
            logger.info("------------------------------进入修改资讯方法---------------------------");
            logger.info("content:{},member:{}",content,member);
            if (StringUtils.isBlank(content) || Objects.isNull(member)){
                return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
            }
            try {
                if (!checkConsultation(content,"2")){
                    return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
                }
                JSONObject jsonObject = JSONObject.parseObject(content);
                if (Objects.isNull(jsonObject)){
                    return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
                }
                Long id = jsonObject.getLong("id");
                logger.info("-----------------------根据用户id和资讯id获取资讯信息");
                Consultation consultation = consultationMapper.getConsultationByIdAndMember(id,member.getId());
                if (Objects.isNull(consultation)){
                    return ResultUtils.returnError("信息不存在");
                }

                logger.info("------------------------------获取保存类型 0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享---------------------------");
                //获取保存类型 0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
                String type =jsonObject.getString("type");

                JSONArray contentArray = jsonObject.getJSONArray("content");
                if (!"0,2".contains(type)){
                    if (Objects.isNull(contentArray)){
                        return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
                    }
                }
                String title=null;
                if (!"5".equals(String.valueOf(type))){
                    title = jsonObject.getString("title");
                }
                Integer cstatus = consultation.getStatus();
                logger.info("------------------------------驳回 编辑主题---------------------------");
                //驳回 编辑主题
                if (Objects.equals(cstatus,3) && "0,2".contains(String.valueOf(consultation.getType()))){
                    logger.info("==========================修改主题的内容================================");
                    consultationMapper.updateConsultationByConsultation(consultation.getId());//修改主题的内容
                    //@wudi,删除通知中的资讯,资讯id，memberId，资讯审核状态type为 1认证驳回  2内容驳回,有关咨询中的所有的资讯内容id
                    logger.info("==========================删除通知中的资讯,资讯id，memberId，资讯审核状态type为 1认证驳回  2内容驳回,有关咨询中的所有的资讯内容id=========");
                    Long mId = member.getId();
                    Integer msgType =2;
                    logger.info("=====================通过父类的资讯id获取资讯未审核资讯内容==========================");
                    List<Map> consultationListByParentId = consultationMapper.getConsultationListByParentId(consultation.getId(),mId,3);
                    if (consultationListByParentId.size()>0) {
                        for (int i = 0; i < consultationListByParentId.size(); i++) {

                            Long conId= (Long)consultationListByParentId.get(i).get("id");
                            logger.info("删除MemberMsg的基本信息："+msgType+"===:"+conId+"===:"+mId);
                            logger.info("在修改编辑资讯的时候删除审核失败通知信息："+msgType+"===:"+conId+"===:"+mId);
                            consultationMapper.deleteMemberMsgByConId(conId,mId,msgType);
                        }

                    }

                }
                logger.info("=================驳回 编辑内容0未发布  1审核中 2已发布 3驳回====================");
                //驳回 编辑内容0未发布  1审核中 2已发布 3驳回 @wudi
                if(Objects.equals(cstatus,3) && "1,3".contains(String.valueOf(consultation.getType()))){
                    if (consultation.getConsultationId()!=0) {
                        Consultation findOne = consultationMapper.findOne(consultation.getConsultationId());
                        if (findOne==null) {
                            return ResultUtils.returnError("主题数据错误");
                        }
                        Integer status=	findOne.getStatus()==null?0:findOne.getStatus();
                        if(Objects.equals(status,3)){
                            logger.info("=================修改主题===================");
                            consultationMapper.updateConsultationStatusById(consultation.getConsultationId());//修改主题
                            //@wudi,删除通知中的资讯,资讯id，memberId，资讯审核状态type为 1认证驳回  2内容驳回
                            Integer msgType =2;
                            Long conId= consultation.getId();
                            Long mId = member.getId();
                            logger.info("删除MemberMsg的基本信息："+msgType+"===:"+conId+"===:"+mId);
                            consultationMapper.deleteMemberMsgByConId(conId,mId,msgType);
                        }

                    }

                }

                consultation.setAuthorInfo(member.getNickname());
                if ("5".equals(type)){
                    //审核通过
                    consultation.setStatus(2);
                } else {
                    //审核中
                    consultation.setStatus(1);
                    Integer msgType =2;
                    Long conId= consultation.getId();
                    Long mId = member.getId();
                    logger.info("删除MemberMsg的基本信息："+msgType+"===:"+conId+"===:"+mId);
                    consultationMapper.deleteMemberMsgByConId(conId,mId,msgType);
                }
                if (StringUtils.isNotBlank(title)){
                    consultation.setTitle(title);
                }
                consultation.setType(Integer.valueOf(type));
                Integer modelType = jsonObject.getInteger("modelType");
                if (!Objects.isNull(modelType)){
                    consultation.setModelType(modelType);
                }
                Integer topicType = jsonObject.getInteger("topicType");
                if (!Objects.isNull(topicType)){
                    consultation.setTopicType(topicType);
                }
                String descr = jsonObject.getString("descr");
                if (StringUtils.isNotBlank(descr)){
                    consultation.setDetailSummary(descr);
                }
                consultation.setCreatedTime(new Date());
                //父ID
                Long pid = jsonObject.getLong("pid");
                if (!Objects.isNull(pid)){
                    logger.info("=====================获取父资讯=================");
                    Consultation sonConsultation = consultationMapper.findOne(pid);
                    consultation.setConsultationId(sonConsultation.getId());
                }
                logger.info("===================修改资讯信息=================");
                consultationMapper.updateById(consultation);
                //删除
                consultationAttachmentMapper.deleteConsultationAttachmentByConsultation(consultation);
                String covers= jsonObject.getString("covers");
                if (StringUtils.isNotBlank(covers)){
                    String[] ids = covers.split(",");
                    Arrays.stream(ids).forEach(e->{
                        Attachment attachment = attachmentMapper.findOne(Long.valueOf(e));
                        if (!Objects.isNull(attachment)){
                            ConsultationAttachment consultationAttachment = new ConsultationAttachment();
                            consultationAttachment.setAddress(attachment.getAddress());
                            consultationAttachment.setName(attachment.getName());
                            consultationAttachment.setConsultation(consultation.getId());
                            consultationAttachment.setAttachment(attachment.getId());
                            consultationAttachment.setCover(1);
                            logger.info("==============保存附件================");
                            consultationAttachmentMapper.save(consultationAttachment);
                        }
                    });
                }
                if (!Objects.isNull(contentArray)){
                    int size = contentArray.size();
                    for( int i=0;i<size;i++) {
                        ConsultationAttachment consultationAttachment = new ConsultationAttachment();
                        JSONObject obj = contentArray.getJSONObject(i);
                        String attachmentId = obj.getString("attachmentId");
                        if (StringUtils.isNotBlank(attachmentId)){
                            Attachment attachment = attachmentMapper.findOne(Long.valueOf(attachmentId));
                            if (Objects.isNull(attachment)){
                                return ResultUtils.returnError("附件不存在");
                            }
                            consultationAttachment.setAddress(attachment.getAddress());
                            consultationAttachment.setName(attachment.getName());
                            consultationAttachment.setAttachment(attachment.getId());
                        }
                        String operationType = obj.getString("type");
                        consultationAttachment.setType(Integer.valueOf(operationType));
                        consultationAttachment.setConsultation(consultation.getId());
                        Integer sortNum = obj.getInteger("sortNum");
                        consultationAttachment.setSortNum(sortNum);
                        String text = obj.getString("detail");
                        if (StringUtils.isNotBlank(text)){
                            consultationAttachment.setDetailContent(text);
                        }
                        logger.info("维护到 consultationAttachment表中");
                        consultationAttachmentMapper.insert(consultationAttachment);
                    }
                }
                Map<String,Object> result = Maps.newHashMap();
                result.put("cid",consultation.getId());
                if ("5".equals(type)){
                    return ResultUtils.returnSuccess("发布成功",result);
                }
                logger.info("===============结束修改资讯方法========================");
                return ResultUtils.returnSuccess("提交成功,内容正在审核中",result);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
        }
    }

    @Override
    public Result publishConsultation(String id, String type, Member member) {
        return null;
    }

    @Override
    public Map<String, Object> getConsultationByIdaaa(Long id) {
        return null;
    }

    @Override
    public Result findconsultationinfo(Integer page, Integer rows, String checktype) {
        logger.info("====================APP首页  内容根据关键词搜索开始===========");
        HashMap<String, Object> param = new HashMap<String, Object>();

        if (null == checktype || "".equals(checktype)) {
            return ResultUtils.returnError("类型不能为空");
        }

        //查询资讯列表
        param.put("checktype", checktype);
        param.put("startIndex", (page - 1) * rows);
        param.put("endIndex", rows);

        List<Map<String, Object>> consultationInfoListss = consultationMapper.findconsultationinfo(param);
        List<Map<String, Object>> consultationInfoList = new ArrayList<Map<String, Object>>();
        if (consultationInfoListss.size() > 0) {
            for (Map<String, Object> consultationInfo : consultationInfoListss) {
                //按类型查询
                String type = consultationInfo.get("type").toString();
                //是否是主题或访谈的标识
                boolean isMore = false;//是否是主题或访谈的标识    false 不是   true 是
                List<Map<String, Object>> consultationChidList = new ArrayList<Map<String, Object>>();
                if ("0".equals(type) || "2".equals(type)) {//0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
                    //判断访谈和口述是否有内容
                    Integer count = consultationMapper.getCountById(Long.valueOf(consultationInfo.get("id").toString()));
                    if (count == 0) {
                        continue;
                    }

                    isMore = true;

                    consultationChidList = consultationMapper.findConsultationChidById(Long.valueOf(consultationInfo.get("id").toString()));//根据访谈或口述的id查询其子类
                    //取详情内容
                    Object detailContentChid = "";
                    if (consultationChidList.size() > 0) {
                        for (Map<String, Object> consultationChidInfo : consultationChidList) {
                            //处理时间格式
                            String chidAlreadyTime = DateUtils.dateFormat((Date) consultationChidInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                            SimpleDateFormat chiddf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");//设置日期格式
                            String chidNowTime = chiddf.format(new Date());
                            String chidTime1 = chidAlreadyTime.subSequence(0, 10).toString();
                            String chidTime2 = chidNowTime.subSequence(0, 10).toString();
                            if (chidTime1.equals(chidTime2)) {//同一天
                                String chidcreatedTime = chidAlreadyTime.subSequence(11, 16).toString();//截取当天   时，分
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            } else {//不同一天
                                String chidcreatedTime = chidAlreadyTime.subSequence(0, 10).toString();//截取当天   年，月，日
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            }

                            //图片地址
                            String addressChid = "";

                            //处理咨询图片
                            List<Map<String, Object>> consultationChidAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationChidInfo.get("id").toString()));
                            consultationChidInfo.put("address", consultationChidAttachmentList);

                            //获取视频地址
                            String video = consultationAttachmentService.findConsultationAttachmentVideoAddressByConsultationId(Long.valueOf(consultationChidInfo.get("id").toString()));
                            if (video != null) {
                                consultationChidInfo.put("video", video);
                            }

                            //处理访谈和口述专题的详情内容
                            Map<String, Object> map = consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                            if (null == map || map.size() == 0) {
                                detailContentChid = "";
                            } else {
                                detailContentChid = map.get("detailContent");
                            }
                            consultationChidInfo.put("detailContentChid", detailContentChid);
                        }
                    }

                    consultationInfo.put("isMore", isMore);
                    consultationInfo.put("consultationChidList", consultationChidList);

                } else {

                    consultationInfo.put("isMore", isMore);
                    consultationInfo.put("consultationChidList", consultationChidList);
                }
                //处理时间格式
                String alreadyTime = DateUtils.dateFormat((Date) consultationInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");//设置日期格式
                String nowTime = df.format(new Date());
                String time1 = alreadyTime.subSequence(0, 10).toString();
                String time2 = nowTime.subSequence(0, 10).toString();
                if (time1.equals(time2)) {//同一天
                    String createdTime = alreadyTime.subSequence(11, 16).toString();//截取当天   时，分
                    consultationInfo.put("createdTime", createdTime);
                } else {//不同一天
                    String createdTime = alreadyTime.subSequence(0, 10).toString();//截取当天   年，月，日
                    consultationInfo.put("createdTime", createdTime);
                }

                //图片地址
                String address = "";

                //处理咨询图片
                List<Map<String, Object>> consultationAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                consultationInfo.put("address", consultationAttachmentList);

                //获取资讯的视频地址
                String video = consultationAttachmentService.findConsultationAttachmentVideoAddressByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                if (video != null) {
                    consultationInfo.put("video", video);
                }


                //取详情内容
                Object detailContent = "";
                if ("4".equals(type) || "6".equals(type)) {
                    Map<String, Object> map = consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                    if (null == map || map.size() == 0) {
                        detailContent = "";
                    } else {
                        detailContent = map.get("detailContent");
                    }
                }
                consultationInfo.put("detailContent", detailContent);

                consultationInfoList.add(consultationInfo);
            }
            logger.info("====================APP首页  内容根据关键词搜索结束===========");
            return ResultUtils.returnSuccess("请求成功", consultationInfoList);
        } else {
            logger.info("====================APP首页  内容根据关键词搜索结束===========");
            return ResultUtils.returnError("没有数据");
        }
    }

    @Override
    public Result findConsultationInfoHelp(Integer page, Integer rows) {
        logger.info("===========APP求助  内容根据关键词搜索开始============");
        HashMap<String, Object> param = new HashMap<String, Object>();

        //查询资讯列表
        param.put("startIndex", (page - 1) * rows);
        param.put("endIndex", rows);

        List<Map<String, Object>> consultationInfoHelpList = consultationMapper.findConsultationInfoHelp(param);

        if (consultationInfoHelpList.size() > 0) {
            for (Map<String, Object> consultationInfoHelp : consultationInfoHelpList) {
                //处理时间格式
                String alreadyTime = DateUtils.dateFormat((Date) consultationInfoHelp.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");//设置日期格式
                String nowTime = df.format(new Date());
                String time1 = alreadyTime.subSequence(0, 10).toString();
                String time2 = nowTime.subSequence(0, 10).toString();
                if (time1.equals(time2)) {//同一天
                    String createdTime = alreadyTime.subSequence(11, 16).toString();//截取当天   时，分
                    consultationInfoHelp.put("createdTime", createdTime);
                } else {//不同一天
                    String createdTime = alreadyTime.subSequence(0, 10).toString();//截取当天   年，月，日
                    consultationInfoHelp.put("createdTime", createdTime);
                }

                //图片地址
                String address = "";

                //处理咨询图片
                List<Map<String, Object>> consultationAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationInfoHelp.get("id").toString()));
                consultationInfoHelp.put("address", consultationAttachmentList);
                //取详情内容
                Map<String, Object> map = consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consultationInfoHelp.get("id").toString()));
                Object detailContent = "";
                if (null == map || map.size() == 0) {
                    detailContent = "";
                } else {
                    detailContent = map.get("detailContent");
                }
                consultationInfoHelp.put("detailContent", detailContent);
            }
            logger.info("===========APP求助  内容根据关键词搜索结束============");
            return ResultUtils.returnSuccess("请求成功", consultationInfoHelpList);
        } else {
            logger.info("===========APP求助  内容根据关键词搜索结束============");
            return ResultUtils.returnError("没有数据");
        }
    }

    @Override
    public Result findConsultationInfoPeople(Integer page, Integer rows, String checktype) {
        logger.info("============APP民间高手  内容根据关键词搜索开始==============");
        HashMap<String, Object> param = new HashMap<String, Object>();

        if (null == checktype || "".equals(checktype)) {
            return ResultUtils.returnError("类型不能为空");
        }

        //查询资讯列表
        param.put("checktype", checktype);
        param.put("startIndex", (page - 1) * rows);
        param.put("endIndex", rows);

        List<Map<String, Object>> consultationInfoPeopleListss = consultationMapper.findConsultationInfoPeople(param);
        List<Map<String, Object>> consultationInfoPeopleList = new ArrayList<Map<String, Object>>();
        if (consultationInfoPeopleListss.size() > 0) {
            for (Map<String, Object> consultationInfo : consultationInfoPeopleListss) {
                //判断访谈和口述是否有内容
                Integer count = consultationMapper.getCountById(Long.valueOf(consultationInfo.get("id").toString()));
                if (count == 0) {
                    continue;
                }

                //处理时间格式
                String alreadyTime = DateUtils.dateFormat((Date) consultationInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");//设置日期格式
                String nowTime = df.format(new Date());
                String time1 = alreadyTime.subSequence(0, 10).toString();
                String time2 = nowTime.subSequence(0, 10).toString();
                if (time1.equals(time2)) {//同一天
                    String createdTime = alreadyTime.subSequence(11, 16).toString();//截取当天   时，分
                    consultationInfo.put("createdTime", createdTime);
                } else {//不同一天
                    String createdTime = alreadyTime.subSequence(0, 10).toString();//截取当天   年，月，日
                    consultationInfo.put("createdTime", createdTime);
                }

                //图片地址
                String address = "";

                //处理咨询图片
                List<Map<String, Object>> consultationAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                consultationInfo.put("address", consultationAttachmentList);

                //访谈和口述主题下的子类
                List<Map<String, Object>> consultationChidList = new ArrayList<Map<String, Object>>();

                consultationChidList = consultationMapper.findConsultationChidById(Long.valueOf(consultationInfo.get("id").toString()));//根据访谈或口述的id查询其子类
                //取详情内容
                Object detailContentChid = "";
                if (consultationChidList.size() > 0) {
                    for (Map<String, Object> consultationChidInfo : consultationChidList) {
                        //处理时间格式
                        String chidAlreadyTime = DateUtils.dateFormat((Date) consultationChidInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                        SimpleDateFormat chiddf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");//设置日期格式
                        String chidNowTime = chiddf.format(new Date());
                        String chidTime1 = chidAlreadyTime.subSequence(0, 10).toString();
                        String chidTime2 = chidNowTime.subSequence(0, 10).toString();
                        if (chidTime1.equals(chidTime2)) {//同一天
                            String chidcreatedTime = chidAlreadyTime.subSequence(11, 16).toString();//截取当天   时，分
                            consultationChidInfo.put("createdTime", chidcreatedTime);
                        } else {//不同一天
                            String chidcreatedTime = chidAlreadyTime.subSequence(0, 10).toString();//截取当天   年，月，日
                            consultationChidInfo.put("createdTime", chidcreatedTime);
                        }

                        //图片地址
                        String addressChid = "";

                        //处理咨询图片
                        List<Map<String, Object>> consultationChidAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationChidInfo.get("id").toString()));
                        consultationChidInfo.put("address", consultationChidAttachmentList);

                        //获取视频的地址
                        String video = consultationAttachmentService.findConsultationAttachmentVideoAddressByConsultationId(Long.valueOf(consultationChidInfo.get("id").toString()));
                        if (video != null) {
                            consultationChidInfo.put("video", video);
                        }

                        //处理访谈和口述专题的详情内容
                        Map<String, Object> map = consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                        if (null == map || map.size() == 0) {
                            detailContentChid = "";
                        } else {
                            detailContentChid = map.get("detailContent");
                        }

                        consultationChidInfo.put("detailContentChid", detailContentChid);
                    }
                }

                consultationInfo.put("consultationChidList", consultationChidList);
                consultationInfoPeopleList.add(consultationInfo);
            }
            logger.info("============APP民间高手  内容根据关键词搜索结束==============");
            return ResultUtils.returnSuccess("请求成功", consultationInfoPeopleList);
        } else {
            logger.info("============APP民间高手  内容根据关键词搜索结束==============");
            return ResultUtils.returnError("没有数据");
        }
    }

    /**
     * 个人中心 <我的发布>
     * @author huangxin
     * @data 2018/1/17 11:28
     * @Description: 个人中心 <我的发布>
     * @Version: 1.0.0
     * @param type   类型
     * @param member 用户
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result getMemberConsultation(String type, Member member, Integer page, Integer size) {
        logger.info("===============个人中心，我的发布列表===============");
        return consultationAttachmentService.getConsultationAttachmentByConsultation(type, member, page, size);
    }
    /**
     * 获取父级专题信息
     * @param type  资讯类型
     * @param member    用户
     * @param page  当前页数
     * @param size  每页显示的数量
     * @return
     */
    @Override
    public Result getParentConsultation(String type, Member member, Integer page, Integer size) {
        logger.info("==============进入获取资讯列表方法===============");
        Map<String,Object> map = Maps.newHashMap();
        map.put("mid",member.getId());
        Integer curPage = (page-1)*size;
        map.put("page",curPage);
        map.put("size",size);
        //已发布
        map.put("status","2");
        List<Map<String,Object>> result ;
        //访谈
        try {
            if ("1".equals(type)){
                map.put("type","0");
                result = consultationAttachmentService.getConsultationAttachmentByConsultationType(map);
            }  else {
                map.put("type","2");
                result = consultationAttachmentService.getConsultationAttachmentByConsultationType(map);
            }
            logger.info("==============获取资讯列表方法结束===============");
            return ResultUtils.returnSuccess("成功",result);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResultUtils.returnError("失败");
        }
    }

    @Override
    public Result getMemberConsultationByType(String cid, Member member, Integer page, Integer size) {
        return null;
    }

    @Override
    public Result findConsultationAllByTouxiang(Integer page, Integer rows, String memberId, String checktype) {
        logger.info("==========访谈详情页,点用户头像查看访谈开始==========");
        HashMap<String, Object> param = new HashMap<String, Object>();

        if (null == memberId || "".equals(memberId)) {
            return ResultUtils.returnError("用户id不能为空");
        }
        //checktype  1 访谈  2 口述   3 求助   4 分享
        if (null == checktype || "".equals(checktype)) {
            return ResultUtils.returnError("类型不能为空");
        }
        //验证用户是否存在
        Member member = memberService.getMerberById(Long.valueOf(memberId));
        if (null == member) {
            return ResultUtils.returnError("用户不存在");
        }

        //查询资讯列表
        param.put("memberId", memberId);
        param.put("checktype", checktype);
        param.put("startIndex", (page - 1) * rows);
        param.put("endIndex", rows);

        List<Map<String, Object>> consultationAllTypeListss = consultationMapper.findConsultationAllByTouxiang(param);
        List<Map<String, Object>> consultationAllTypeList = new ArrayList<Map<String, Object>>();
        if (consultationAllTypeListss.size() > 0) {
            for (Map<String, Object> consultationInfo : consultationAllTypeListss) {
                //按类型查询
                String type = consultationInfo.get("type").toString();
                //是否是主题或访谈的标识  false 不是   true 是
                boolean isMore = false;
                List<Map<String, Object>> consultationChidList = new ArrayList<Map<String, Object>>();
                //0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
                if ("0".equals(type) || "2".equals(type)) {
                    //判断访谈和口述是否有内容
                    Integer count = consultationMapper.getCountById(Long.valueOf(consultationInfo.get("id").toString()));
                    if (count == 0) {
                        continue;
                    }
                    isMore = true;
                    //根据访谈或口述的id查询其子类
                    consultationChidList = consultationMapper.findConsultationChidById(Long.valueOf(consultationInfo.get("id").toString()));
                    //取详情内容
                    Object detailContentChid = "";
                    if (consultationChidList.size() > 0) {
                        for (Map<String, Object> consultationChidInfo : consultationChidList) {
                            //处理时间格式
                            String chidAlreadyTime = DateUtils.dateFormat((Date) consultationChidInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                            //设置日期格式
                            SimpleDateFormat chiddf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
                            String chidNowTime = chiddf.format(new Date());
                            String chidTime1 = chidAlreadyTime.subSequence(0, 10).toString();
                            String chidTime2 = chidNowTime.subSequence(0, 10).toString();
                            if (chidTime1.equals(chidTime2)) {
                                //同一天
                                //截取当天   时，分
                                String chidcreatedTime = chidAlreadyTime.subSequence(11, 16).toString();
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            } else {
                                //不同一天
                                //截取当天   年，月，日
                                String chidcreatedTime = chidAlreadyTime.subSequence(0, 10).toString();
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            }

                            //图片地址
                            String addressChid = "";

                            //处理咨询图片
                            List<Map<String, Object>> consultationChidAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationChidInfo.get("id").toString()));
                            consultationChidInfo.put("address", consultationChidAttachmentList);

                            //获取视频地址
                            String video = consultationAttachmentService.findConsultationAttachmentVideoAddressByConsultationId(Long.valueOf(consultationChidInfo.get("id").toString()));
                            if (video != null) {
                                consultationChidInfo.put("video", video);
                            }
                            //处理访谈和口述专题的详情内容
                            Map<String, Object> map = consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                            if (null == map || map.size() == 0) {
                                detailContentChid = "";
                            } else {
                                detailContentChid = map.get("detailContent");
                            }

                            consultationChidInfo.put("detailContentChid", detailContentChid);

                        }
                    }

                    consultationInfo.put("isMore", isMore);
                    consultationInfo.put("consultationChidList", consultationChidList);

                } else {

                    consultationInfo.put("isMore", isMore);
                    consultationInfo.put("consultationChidList", consultationChidList);
                }
                //处理时间格式
                String alreadyTime = DateUtils.dateFormat((Date) consultationInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");//设置日期格式
                String nowTime = df.format(new Date());
                String time1 = alreadyTime.subSequence(0, 10).toString();
                String time2 = nowTime.subSequence(0, 10).toString();
                if (time1.equals(time2)) {//同一天
                    String createdTime = alreadyTime.subSequence(11, 16).toString();//截取当天   时，分
                    consultationInfo.put("createdTime", createdTime);
                } else {//不同一天
                    String createdTime = alreadyTime.subSequence(0, 10).toString();//截取当天   年，月，日
                    consultationInfo.put("createdTime", createdTime);
                }

                //图片地址
                String address = "";

                //处理咨询图片
                List<Map<String, Object>> consultationAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                consultationInfo.put("address", consultationAttachmentList);

                //获取视频地址
                String video = consultationAttachmentService.findConsultationAttachmentVideoAddressByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                if (video != null) {
                    consultationInfo.put("video", video);
                }

                //取详情内容
                Object detailContent = "";
                if ("4".equals(type) || "6".equals(type)) {
                    Map<String, Object> map = consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                    if (null == map || map.size() == 0) {
                        detailContent = "";
                    } else {
                        detailContent = map.get("detailContent");
                    }
                }

                consultationInfo.put("detailContent", detailContent);

                consultationAllTypeList.add(consultationInfo);
            }
            logger.info("==========访谈详情页,点用户头像查看访谈借宿==========");
            return ResultUtils.returnSuccess("请求成功", consultationAllTypeList);
        } else {
            logger.info("==========访谈详情页,点用户头像查看访谈结束==========");
            return ResultUtils.returnError("没有数据");
        }
    }

    @Override
    public Result findConsultationAllByMember(Integer page, Integer rows, Member member, String checktype) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        Long memberId=member.getId();
        if(null==memberId){
            return ResultUtils.returnError("用户未登录，请先登录");
        }
        //checktype  1 访谈  2 口述   3 求助   4 分享
        if (null == checktype || "".equals(checktype)) {
            return ResultUtils.returnError("类型不能为空");
        }
        //验证用户是否存在
        Member member1=memberService.getMerberById(memberId);
        if(null==member1){
            return ResultUtils.returnError("用户不存在");
        }
        //查询资讯列表
        param.put("memberId", memberId);
        param.put("checktype", checktype);
        param.put("startIndex", (page - 1) * rows);
        param.put("endIndex", rows);
        List<Map<String, Object>> consultationAllTypeList = consultationMapper.findConsultationAllByMember(param);
        if (consultationAllTypeList.size() > 0) {
            for (Map<String, Object> consultationInfo : consultationAllTypeList) {
                //处理时间格式
                String alreadyTime = DateUtils.dateFormat((Date) consultationInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                //设置日期格式
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
                String nowTime = df.format(new Date());
                String time1 = alreadyTime.subSequence(0, 10).toString();
                String time2 = nowTime.subSequence(0, 10).toString();
                //同一天
                if (time1.equals(time2)) {
                    //截取当天   时，分
                    String createdTime = alreadyTime.subSequence(11, 16).toString();
                    consultationInfo.put("createdTime", createdTime);
                } else {
                    //不同一天
                    //截取当天   年，月，日
                    String createdTime = alreadyTime.subSequence(0, 10).toString();
                    consultationInfo.put("createdTime", createdTime);
                }
                //图片地址
                String address = "";
                //处理咨询图片
                List<Map<String, Object>> consultationAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                consultationInfo.put("address", consultationAttachmentList);
                //按类型查询
                String type = consultationInfo.get("type").toString();
                //取详情内容
                Object detailContent="";
                if(type.equals("4") || type.equals("6")){
                    Map<String, Object> map=consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                    if(null==map || map.size()==0){
                        detailContent="";
                    }else{
                        detailContent=map.get("detailContent");
                    }
                }
                consultationInfo.put("detailContent", detailContent);
                //是否是主题或访谈的标识 false 不是   true 是
                boolean isMore = false;
                List<Map<String, Object>> consultationChidList = new ArrayList<Map<String,Object>>();
                //0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
                if (type.equals("0") || type.equals("2")) {
                    isMore = true;
                    //根据访谈或口述的id查询其子类
                    consultationChidList = consultationMapper.findConsultationChidByIdByMember(Long.valueOf(consultationInfo.get("id").toString()));
                    Integer count=consultationChidList.size();
                    consultationInfo.put("count", count);
                    //取详情内容
                    Object detailContentChid="";
                    if (consultationChidList.size() > 0) {
                        for (Map<String, Object> consultationChidInfo : consultationChidList) {
                            //处理时间格式
                            String chidAlreadyTime = DateUtils.dateFormat((Date) consultationChidInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                            SimpleDateFormat chiddf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");//设置日期格式
                            String chidNowTime = chiddf.format(new Date());
                            String chidTime1 = chidAlreadyTime.subSequence(0, 10).toString();
                            String chidTime2 = chidNowTime.subSequence(0, 10).toString();
                            if (chidTime1.equals(chidTime2)) {//同一天
                                String chidcreatedTime = chidAlreadyTime.subSequence(11, 16).toString();//截取当天   时，分
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            } else {//不同一天
                                String chidcreatedTime = chidAlreadyTime.subSequence(0, 10).toString();//截取当天   年，月，日
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            }
                            //图片地址
                            String addressChid = "";
                            //处理咨询图片
                            List<Map<String, Object>> consultationChidAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationChidInfo.get("id").toString()));
                            consultationChidInfo.put("address", consultationChidAttachmentList);
                            //处理访谈和口述专题的详情内容
                            Map<String, Object> map=consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                            if(null==map || map.size()==0){
                                detailContentChid="";
                            }else{
                                detailContentChid=map.get("detailContent");
                            }
                            consultationChidInfo.put("detailContentChid", detailContentChid);
                        }
                    }
                    consultationInfo.put("isMore", isMore);
                    consultationInfo.put("consultationChidList", consultationChidList);
                } else {
                    consultationInfo.put("isMore", isMore);
                    consultationInfo.put("consultationChidList", consultationChidList);
                }
            }
            return ResultUtils.returnSuccess("请求成功", consultationAllTypeList);
        } else {
            return ResultUtils.returnError("没有数据");
        }
    }

    /**
     * @description ：检测是否是高级用户
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/17 13:44
     * @param memberId
     * @return
     */
    @Override
    public Result checkIsUserType(String memberId) {

        Map<String, Object> memberMap=new HashMap<>();

        if (null == memberId || "".equals(memberId)) {
            return ResultUtils.returnError("用户id不能为空");
        }

        //验证用户是否存在
        Member member=memberService.checkMemberById(Long.valueOf(memberId));
        if(null==member){
            return ResultUtils.returnError("用户不存在");
        }
        Object isDelete=member.getIsDelete();//0或null未禁用，1禁用
        if(null==isDelete){isDelete=0;}
        if("1".equals(isDelete)){
            return ResultUtils.returnError("此用户已被禁用");
        }
        Object userType=member.getUserType();//0普通  1认证后用户可以发布访谈 口述
        if(null==userType){userType=0;}
        boolean isUserType=false;
        if("1".equals(userType.toString())){
            isUserType=true;
        }
        memberMap.put("isUserType", isUserType);
        return ResultUtils.returnSuccess("请求成功", memberMap);
    }

    /**
     * @description ：查看全部  分页加载
     * @Created by  : 朱军
     * @version
     * @Creation Date ： 2018/1/17 11:46
     * @param page
     * @param rows
     * @param typeId
     * @return
     */
    @Override
    public Result findConsultationAllByFive(Integer page, Integer rows, String typeId) {

        HashMap<String, Object> param = new HashMap<String, Object>();

        if (null == typeId || "".equals(typeId)) {
            return ResultUtils.returnError("资讯id不能为空");
        }
        List<Map<String, Object>> consultationAllList = consultationAllList = consultationMapper.findConsultationByIdAll(Long.valueOf(typeId));//根据访谈或口述的id查询其子类列表
        if(consultationAllList.size() <3){
            return ResultUtils.returnError("没有更多数据");
        }
        String id1=consultationAllList.get(0).get("id").toString();
        String id2=consultationAllList.get(1).get("id").toString();
        String id3=consultationAllList.get(2).get("id").toString();
        if(null !=id1 && !"".equals(id1) && null !=id2 && !"".equals(id2) && null !=id3 && !"".equals(id3) ){
            param.put("id1", id1);
            param.put("id2", id2);
            param.put("id3", id3);
            param.put("typeId", typeId);
            param.put("startIndex", (page - 1) * rows);
            param.put("endIndex", rows);
        }

        List<Map<String, Object>> consultationList = consultationAllList = consultationMapper.findConsultationAllByFive(param);
        if (consultationList.size() > 0) {
            for (Map<String, Object> consultationInfo : consultationList) {
                //处理时间格式
                String alreadyTime = DateUtils.dateFormat((Date) consultationInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat chiddf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");//设置日期格式
                String chidNowTime = chiddf.format(new Date());
                String chidTime1 = alreadyTime.subSequence(0, 10).toString();
                String chidTime2 = chidNowTime.subSequence(0, 10).toString();
                if (chidTime1.equals(chidTime2)) {//同一天
                    String chidcreatedTime = alreadyTime.subSequence(11, 16).toString();//截取当天   时，分
                    consultationInfo.put("createdTime", chidcreatedTime);
                } else {//不同一天
                    String chidcreatedTime = alreadyTime.subSequence(0, 10).toString();//截取当天   年，月，日
                    consultationInfo.put("createdTime", chidcreatedTime);
                }

                //图片地址
                String addressChid = "";

                //处理咨询图片
                List<Map<String, Object>> consultationChidAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                consultationInfo.put("address", consultationChidAttachmentList);
						/*if (consultationChidAttachmentList.size() > 0) {
							//循环拼接图片地址
							for (Map<String, Object> consultationChidAttachment : consultationChidAttachmentList) {
								Object addrs=consultationChidAttachment.get("address");
								if(null==addrs){addrs="";};
								addressChid += addrs.toString() + ",";
							}
							consultationInfo.put("address", addressChid);
						} else {
							consultationInfo.put("address", "");
						}*/
            }

            return ResultUtils.returnSuccess("请求成功", consultationList);
        } else {
            return ResultUtils.returnError("没有数据");
        }
    }

    @Override
    public Result deleteConsultationById(Member member, Long typeId, String checktype) {
        return null;
    }

    @Override
    public Result backConsultation(String cid, Member member) {
        return null;
    }

    /**
     * 信息详情<访谈、口述、求助、分享>
     * @author huangxin
     * @data 2018/1/18 15:52
     * @Description: 信息详情<访谈、口述、求助、分享>
     * @Version: 3.2.0
     * @param cid    资讯id
     * @param member 用户信息
     * @param row
     * @param inType 类型
     * @return
     */
    @Override
    public Result getConsultationDetail(String cid, Member member, int row, String inType){
        logger.info("===============咨询信息详情开始===============");
        if (StringUtils.isBlank(cid)){
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        }
        /**
         * 1. 获取专题信息
         */
        Map<String,Object> consultation = consultationMapper.getConsultationById(cid);
        if (Objects.isNull(consultation)){
            logger.info("===============信息不存在===============");
            return ResultUtils.returnError("信息不存在");
        }

        Long mid = (Long) consultation.get("mid");
        if (Objects.isNull(mid)){
            logger.info("===============咨询用户信息不存在===============");
            return ResultUtils.returnError("咨询用户信息不存在");
        }
        //状态
        Integer status = (Integer) consultation.get("status");

        //未发布
        if (!"2".equals(String.valueOf(status)) && Objects.isNull(member)){
            logger.info("===============信息异常===============");
            return ResultUtils.returnError("信息异常");
        }

        Map<String,Object> map = Maps.newHashMap();
        //操作类型
        Integer type = (Integer) consultation.get("type");

        String lastId=cid;
        Integer lastType=type;
        String commentId =cid;

        //驳回原因
        String reject="";

        /**
         * 健桥三期需求，暂时注释代码
         */
		/*//点击阅读的时候维护权重值
		rpcConsultationService.saveConsultationByRead(Long.valueOf(cid), member);*/

        //访谈、口述内容
        if ("1".equals(String.valueOf(type)) || "3".equals(String.valueOf(type))){
            lastId = String.valueOf(consultation.get("pid"));
            Map<String,Object> parentConsultation = consultationMapper.getConsultationById(lastId);

            if (!Objects.isNull(parentConsultation)){
                //对应主题 操作类型
                logger.info("===============对应主题 操作类型===============");
                lastType = (Integer) parentConsultation.get("type");
            }
        }
        //包含专题
        if ("0".equals(String.valueOf(type)) || "2".equals(String.valueOf(type))
                || "1".equals(String.valueOf(type)) || "3".equals(String.valueOf(type))){
            //专题
            logger.info("===============包含专题===============");
            Map<String,Object> parentConsultation = consultationAttachmentService.getParentConsultationDetail(Long.parseLong(lastId),lastType);
            map.put("subject",parentConsultation);
        }

        /**
         * 2. 获取推荐信息
         */
        //专题类型
        if ( "0".equals(String.valueOf(type)) || "2".equals(String.valueOf(type))){
            Map<String,Object> paramMap = Maps.newHashMap();
            paramMap.put("pid",cid);
            if (Objects.isNull(member)){
                //已发布
                paramMap.put("status",2);
            }
            if (!Objects.isNull(member) && !Objects.equals(member.getId(),mid)){
                //已发布
                paramMap.put("status",2);
            }
            if (!Objects.isNull(member)){
                paramMap.put("mid",member.getId());
            }
            //首页点击进入
            if ("1".equals(inType)){
                //已发布
                paramMap.put("status",2);
            }
            List<Map<String,Object>>  defaultConsultation = consultationMapper.getConsultationByMap(paramMap);
            if (!Objects.isNull(defaultConsultation) && defaultConsultation.size()>0){
                Map<String,Object> firstConsultation = defaultConsultation.get(0);
                Long sid = Long.parseLong(firstConsultation.get("id").toString());
                //驳回原因
                logger.info("===============咨询信息详情开始===============");
                String statuss=firstConsultation.get("status").toString();
                if("3".equals(statuss)){
                    Map<String, Object> map3=memberMessageService.getContentById(sid);
                    if(map3.size()>0){
                        reject=map3.get("content").toString();
                    }
                    firstConsultation.put("reject", reject);
                }else{
                    firstConsultation.put("reject", reject);
                }
                //评论咨询ID
                commentId = String.valueOf(sid);
                //封面图
                firstConsultation.put("covers",consultationAttachmentService.getConsultationAttachmentCoverAddressByConsultationId(sid));
                //详情
                firstConsultation.put("content",consultationAttachmentService.getConsultationAttachmentDetailByConsultation(sid));
                //视频地址
                firstConsultation.put("video", consultationAttachmentService.findConsultationAttachmentVideoAddressByConsultationId(sid));
            }
            map.put("recommend",defaultConsultation);
        }
        //驳回原因
        if ("1".equals(String.valueOf(type)) || "3".equals(String.valueOf(type))){
            logger.info("===============咨询信息驳回原因===============");
            Map<String,Object> paramMap = Maps.newHashMap();
            paramMap.put("pid",lastId);
            if (Objects.isNull(member)){
                //已发布
                paramMap.put("status",2);
            }
            if (!Objects.isNull(member) && !Objects.equals(member.getId(),mid)){
                //已发布
                paramMap.put("status",2);
            }
            if (!Objects.isNull(member)){
                paramMap.put("mid",member.getId());
            }
            //首页点击进入
            if ("1".equals(inType)){
                //已发布
                paramMap.put("status",2);
            }
            List<Map<String,Object>>  detailConsultation =consultationMapper.getConsultationByMap(paramMap);
            List<Map<String,Object>> subjectConsultation = Lists.newArrayList();
            if (!Objects.isNull(detailConsultation)){

                for (Map<String,Object> e_:detailConsultation) {
                    Long sid = Long.parseLong(e_.get("id").toString());
                    //驳回原因
                    logger.info("===============咨询信息驳回原因===============");
                    String statuss=e_.get("status").toString();
                    if("3".equals(statuss)){
                        Map<String, Object> map3=memberMessageService.getContentById(sid);
                        if(map3.size()>0){
                            reject=map3.get("content").toString();
                        }
                        e_.put("reject", reject);
                    }else{
                        e_.put("reject", reject);
                    }

                    if (cid.equals(String.valueOf(sid))) {
                        //封面图
                        e_.put("covers",consultationAttachmentService.getConsultationAttachmentCoverAddressByConsultationId(sid));

                        e_.put("content", consultationAttachmentService.getConsultationAttachmentDetailByConsultation(sid));
                        //视频地址
                        e_.put("video", consultationAttachmentService.findConsultationAttachmentVideoAddressByConsultationId(sid));
                        subjectConsultation.add(e_);
                    }
                }
                for (Map<String,Object> e_:detailConsultation) {
                    Long sid = Long.parseLong(e_.get("id").toString());
                    if (!cid.equals(String.valueOf(sid))) {
                        subjectConsultation.add(e_);
                    }
                }
            }
            map.put("recommend",subjectConsultation);
        }
        logger.info("===============分享===============");
        if ("6".equals(String.valueOf(type))){
            Map<String,Object> paramMap = Maps.newHashMap();
            paramMap.put("id",cid);
            paramMap.put("type",type);
            if (!Objects.isNull(member)){
                paramMap.put("mid",member.getId());
            }
            //已发布
            if (Objects.isNull(member)){
                paramMap.put("status",2);
            }
            if (!Objects.isNull(member) && !Objects.equals(member.getId(),mid)){
                //已发布
                paramMap.put("status",2);
            }
            List<Map<String,Object>>  shareConsultation = consultationMapper.getConsultationByMap(paramMap);
            if (!Objects.isNull(shareConsultation) && shareConsultation.size()>0){
                Map<String,Object> firstConsultation = shareConsultation.get(0);
                //封面图
                firstConsultation.put("covers",consultationAttachmentService.getConsultationAttachmentCoverAddressByConsultationId(Long.parseLong(cid)));
                //详情
                firstConsultation.put("content",consultationAttachmentService.getConsultationAttachmentDetailByConsultation(Long.parseLong(cid)));
                logger.info("===============分享驳回原因===============");
                Long conid = Long.parseLong(firstConsultation.get("id").toString());
                String statuss=firstConsultation.get("status").toString();
                if("3".equals(statuss)){
                    Map<String, Object> map3=memberMessageService.getContentById(conid);
                    if(map3.size()>0){
                        reject=map3.get("content").toString();
                    }
                    firstConsultation.put("reject", reject);
                }else{
                    firstConsultation.put("reject", reject);
                }
            }
            map.put("share",shareConsultation);
        }
        logger.info("===============求助 (问答列表)开始===============");
        if ("4".equals(String.valueOf(type))){
            Map<String,Object> paramMap = Maps.newHashMap();
            paramMap.put("id",cid);
            paramMap.put("type",type);
            if (!Objects.isNull(member)){
                paramMap.put("mid",member.getId());
            }
            //已发布
            if (Objects.isNull(member)){
                paramMap.put("status",2);
            }
            if (!Objects.isNull(member) && !Objects.equals(member.getId(),mid)){
                //已发布
                paramMap.put("status",2);
            }
            List<Map<String,Object>>  shareConsultation =  consultationMapper.getConsultationByMap(paramMap);
            if (!Objects.isNull(shareConsultation) && shareConsultation.size()>0){
                Map<String,Object> firstConsultation = shareConsultation.get(0);
                //封面图
                firstConsultation.put("covers",consultationAttachmentService.getConsultationAttachmentCoverAddressByConsultationId(Long.parseLong(cid)));
                //详情
                firstConsultation.put("content",consultationAttachmentService.getConsultationAttachmentDetailByConsultation(Long.parseLong(cid)));
                Map<String,Object> sonParamMap = Maps.newHashMap();
                sonParamMap.put("page",0);
                sonParamMap.put("size",10);
                sonParamMap.put("pid",cid);
                //已发布
                if (Objects.isNull(member)){
                    sonParamMap.put("status",2);
                }
                if (!Objects.isNull(member) && !Objects.equals(member.getId(),mid)){
                    //已发布
                    sonParamMap.put("status",2);
                }
                if (!Objects.isNull(member)){
                    sonParamMap.put("mid",member.getId());
                }
                List<Map<String,Object>>  askConsultation =  consultationMapper.getConsultationByMap(sonParamMap);
                askConsultation.forEach(a->{
                    Long sid = Long.parseLong(a.get("id").toString());
                    //封面图
                    a.put("covers",consultationAttachmentService.getConsultationAttachmentCoverAddressByConsultationId(sid));
                    //详情
                    a.put("content",consultationAttachmentService.getConsultationAttachmentDetailByConsultation(sid));
                });
                firstConsultation.put("rows",askConsultation);
                logger.info("===============求助 (问答列表)驳回原因===============");
                Long conid = Long.parseLong(firstConsultation.get("id").toString());
                String statuss=firstConsultation.get("status").toString();
                if("3".equals(statuss)){
                    Map<String, Object> map3=memberMessageService.getContentById(conid);
                    if(map3.size()>0){
                        reject=map3.get("content").toString();
                    }
                    firstConsultation.put("reject", reject);
                }else{
                    firstConsultation.put("reject", reject);
                }
                map.put("help",firstConsultation);
            }
        }
        logger.info("===============回答===============");
        if ("5".equals(String.valueOf(type))){
            Map<String,Object> paramMap = Maps.newHashMap();
            paramMap.put("id",cid);
            if (!Objects.isNull(member)){
                paramMap.put("mid",member.getId());
            }
            List<Map<String,Object>>  askConsultation =  consultationMapper.getConsultationByMap(paramMap);
            if (!Objects.isNull(askConsultation) && askConsultation.size()>0){
                Map<String,Object> firstConsultation = askConsultation.get(0);
                String  pid =firstConsultation.get("pid").toString();
                Map<String,Object> helpconsultation = consultationMapper.getConsultationById(pid);
                if (!Objects.isNull(helpconsultation)){
                    firstConsultation.put("ptitle",helpconsultation.get("title").toString());
                }
                //详情
                firstConsultation.put("content",consultationAttachmentService.getConsultationAttachmentDetailByConsultation(Long.parseLong(cid)));
                logger.info("===============回答驳回原因===============");
                Long conid = Long.parseLong(firstConsultation.get("id").toString());
                String statuss=firstConsultation.get("status").toString();
                if("3".equals(statuss)){
                    Map<String, Object> map3=memberMessageService.getContentById(conid);
                    if(map3.size()>0){
                        reject=map3.get("content").toString();
                    }
                    firstConsultation.put("reject", reject);
                }else{
                    firstConsultation.put("reject", reject);
                }
                map.put("ask",firstConsultation);
            }
        }
        /**
         * 3. 获取评论列表
         */
        //非求助 且 为发布状态
        logger.info("===============获取评论列表===============");
        if (!"4".equals(String.valueOf(type)) && "2".equals(String.valueOf(status)) ){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String dateString = formatter.format(new Date());
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("cid", commentId);
            //登录/非登录 区分
            if (Objects.isNull(member)){
                paramMap.put("mid",0);
            } else {
                paramMap.put("mid",member.getId());
            }
            paramMap.put("type",1);
            //评论列表,getCommentList获取到的时顶级评论，firt_reply_comment是第一评论的id
            List<Map<String, Object>> commentList = consultationCommentService.getCommentList(paramMap);
            if (!Objects.isNull(commentList)){
                commentList.forEach(e -> {
                    //获取时间,当天时间保存是时间，不是当天保存的时时间
                    if(!Objects.isNull(e.get("created_time")) && e.get("created_time").toString().contains(dateString)){
                        e.put("created_time", (e.get("created_time").toString().substring(11,16)));
                    }else  {
                        if (!Objects.isNull(e.get("created_time"))) {
                            e.put("created_time", (e.get("created_time").toString().substring(0,11)));
                        }
                    }
                    if (!Objects.isNull(e.get("id"))){
                        //子类评论展示2条
                        //获取前2条的数据
                        logger.info("===============获取前2条的数据===============");
                        List arr = new ArrayList();
                        List<Map> commentSonIdByPid = consultationCommentService.getCommentSonIdByPid((Long)e.get("id"));
                        if(commentSonIdByPid!=null &&commentSonIdByPid.size()>0 ){
                            for(int i=0;commentSonIdByPid.size()>i;i++){
                                Map<String, Object> sonCommentList = consultationCommentService.getSonCommentList((Long)commentSonIdByPid.get(i).get("id"));
                                if(!Objects.isNull(sonCommentList) && !Objects.isNull(sonCommentList.get("created_time")) && sonCommentList.get("created_time").toString().contains(dateString)){
                                    sonCommentList.put("created_time", (sonCommentList.get("created_time").toString().substring(11,16)));
                                    String cString=sonCommentList.get("created_time").toString();
                                }else  {
                                    if (!Objects.isNull(sonCommentList) && !Objects.isNull(sonCommentList.get("created_time"))) {
                                        sonCommentList.put("created_time", (sonCommentList.get("created_time").toString().substring(0,11)));
                                    }
                                }
                                arr.add(sonCommentList);
                            }
                        }
                        logger.info("son comment list :{}",arr);
                        e.put("firstComment", arr);
                    }
                });
            }
            map.put("comment", commentList);
        }
        logger.info("===============咨询信息详情end===============");
        return ResultUtils.returnSuccess(StatusCodeEnums.SUCCESS.getMsg(),map);
    }

    @Override
    public Result searchConsultationInfo(Integer page, Integer rows, String info, String phone, String uuid, String checktype) {
        HashMap<String, Object> param = new HashMap<String, Object>();

        if (!"1".equals(phone) && !"1".equals(uuid) && StringUtils.isNotBlank(info)) {//用户已登录且搜索不为空 保存记录

            param.put("phone", phone);
            param.put("uuid", uuid);
            Member member = memberService.getMemberByIdAndUuid(param);//根据phone和uuid查询用户信息
            Long id = member.getId();//用户ID
            if (null == member || id == null) {//此用户存在
                return ResultUtils.returnError("用户不存在，请核对信息后重新访问");
            }
            if (StringUtils.isNotBlank(info)) {//关键词不为空则保存历史关键词

                //查询当前用户已有的搜索历史
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("memberId", id);
                map.put("info", info);
                Integer searchConsultationCount = membersearchconsultationService.getSearchConsultationByInfo(map);
                if(searchConsultationCount==0){
                    logger.info("当前检索关键词不存在，开始保存对应会员的检索关键词记录");
                    Result result = membersearchconsultationService.saveMemberSearchConsultation(id, info);
                    logger.info("保存历史检索成功!");
                }
            }
        }
        if (StringUtils.isBlank(info)) {
            return ResultUtils.returnError("搜索内容不能为空");
        }

        if (StringUtils.isBlank(checktype)) {
            return ResultUtils.returnError("类型不能为空");
        }
        logger.info("+++++++++++++  开始检索资讯列表  +++++++++++++++++");
        param.put("info", info);
        param.put("checktype", checktype);
        param.put("startIndex", (page - 1) * rows);
        param.put("endIndex", rows);
        List<Map<String, Object>> consultationAllTypeListss = new ArrayList<Map<String,Object>>();
        consultationAllTypeListss = consultationMapper.searchConsultationInfo(param);
        List<String> list = new ArrayList<String>();
        //当查询关键词查询不出来结果的时候,开始进行分词
       /* if(consultationAllTypeListss.size() == 0){
            logger.info("当前关键词没有查询出结果,开始进行分词操作!");
            try {
                //创建分词对象
                Analyzer anal=new IKAnalyzer(true);
                StringReader reader=new StringReader(info);
                //分词
                TokenStream ts=anal.tokenStream("", reader);
                CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
                //遍历分词数据
                while(ts.incrementToken()){
                    list.add(term.toString());
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map<String,Object> m = new HashMap<String,Object>();

            m.put("info", list);
            m.put("checktype", checktype);
            m.put("startIndex", (page - 1) * rows);
            m.put("endIndex", rows);
            //根据关键词分词后进行查询
            consultationAllTypeListss = consultationMapper.searchConsultationByInfoList(m);
            logger.info("查询出来的长度为:"+consultationAllTypeListss.size());
        }*/
        List<Map<String, Object>> consultationAllTypeList =new ArrayList<Map<String, Object>>();
        if (consultationAllTypeListss.size() > 0) {
            for (Map<String, Object> consultationInfo : consultationAllTypeListss) {
                //按类型查询
                String type = consultationInfo.get("type").toString();
                //是否是主题或访谈的标识
                boolean isMore = false;//是否是主题或访谈的标识    false 不是   true 是
                List<Map<String, Object>> consultationChidList = new ArrayList<Map<String,Object>>();
                if (type.equals("0") || type.equals("2")) {//0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
                    //判断访谈和口述是否有内容
                    Integer count=consultationMapper.getCountById(Long.valueOf(consultationInfo.get("id").toString()));
                    if(count==0){
                        continue;
                    }

                    isMore = true;

                    consultationChidList = consultationMapper.findConsultationChidById(Long.valueOf(consultationInfo.get("id").toString()));//根据访谈或口述的id查询其子类
                    //取详情内容
                    Object detailContentChid="";
                    if (consultationChidList.size() > 0) {
                        for (Map<String, Object> consultationChidInfo : consultationChidList) {
                            //处理时间格式
                            String chidAlreadyTime = DateUtils.dateFormat((Date) consultationChidInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                            SimpleDateFormat chiddf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");//设置日期格式
                            String chidNowTime = chiddf.format(new Date());
                            String chidTime1 = chidAlreadyTime.subSequence(0, 10).toString();
                            String chidTime2 = chidNowTime.subSequence(0, 10).toString();
                            if (chidTime1.equals(chidTime2)) {//同一天
                                String chidcreatedTime = chidAlreadyTime.subSequence(11, 16).toString();//截取当天   时，分
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            } else {//不同一天
                                String chidcreatedTime = chidAlreadyTime.subSequence(0, 10).toString();//截取当天   年，月，日
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            }

                            //图片地址
                            String addressChid = "";

                            //处理咨询图片
                            List<Map<String, Object>> consultationChidAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationChidInfo.get("id").toString()));
                            consultationChidInfo.put("address", consultationChidAttachmentList);
                            //处理访谈和口述专题的详情内容
                            Map<String, Object> map=consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                            if(null==map || map.size()==0){
                                detailContentChid="";
                            }else{
                                detailContentChid=map.get("detailContent");
                            }

                            consultationChidInfo.put("detailContentChid", detailContentChid);
                        }
                    }

                    consultationInfo.put("isMore", isMore);
                    consultationInfo.put("consultationChidList", consultationChidList);

                } else {

                    consultationInfo.put("isMore", isMore);
                    consultationInfo.put("consultationChidList", consultationChidList);
                }

                //处理时间格式
                String alreadyTime = DateUtils.dateFormat((Date) consultationInfo.get("createdTime"), "yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");//设置日期格式
                String nowTime = df.format(new Date());
                String time1 = alreadyTime.subSequence(0, 10).toString();
                String time2 = nowTime.subSequence(0, 10).toString();
                if (time1.equals(time2)) {//同一天
                    String createdTime = alreadyTime.subSequence(11, 16).toString();//截取当天   时，分
                    consultationInfo.put("createdTime", createdTime);
                } else {//不同一天
                    String createdTime = alreadyTime.subSequence(0, 10).toString();//截取当天   年，月，日
                    consultationInfo.put("createdTime", createdTime);
                }

                //图片地址
                String address = "";

                //处理咨询图片
                List<Map<String, Object>> consultationAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                consultationInfo.put("address", consultationAttachmentList);

                //取详情内容
                Object detailContent="";
                if(type.equals("4") || type.equals("6")){
                    Map<String, Object> map=consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consultationInfo.get("id").toString()));
                    if(null==map || map.size()==0){
                        detailContent="";
                    }else{
                        detailContent=map.get("detailContent");
                    }
                }
                consultationInfo.put("detailContent", detailContent);
                consultationAllTypeList.add(consultationInfo);
            }

            return ResultUtils.returnSuccess("请求成功", consultationAllTypeList);
        } else {
            return ResultUtils.returnError("没有数据");
        }
    }

    /**
     * 信息详情 分页加载专题下信息
     * @author huangxin
     * @data 2018/1/18 14:42
     * @Description: 信息详情 分页加载专题下信息
     * @Version: 1.0.0
     * @param cid 专题ID或求助ID
     * @param page
     * @param row
     * @param member 用户
     * @return
     */
    @Override
    public Result getConsultationSubjectByPage(String cid, int page, int row, Member member) {
        logger.info("=============信息详情 分页加载专题下信息开始=============");
        try {
            Map<String,Object> consultation = consultationMapper.getConsultationById(cid);
            if (Objects.isNull(consultation)){
                logger.info("=============查询的资讯不存在=============");
                return ResultUtils.returnError("信息不存在");
            }
            Long mid = (Long) consultation.get("mid");
            if (Objects.isNull(mid)){
                logger.info("=============查询资讯的用户信息错误=============");
                return ResultUtils.returnError("咨询用户信息不存在");
            }
            Map<String,Object> map = Maps.newHashMap();
            //操作类型
            Integer type = (Integer) consultation.get("type");
            Map<String,Object> paramMap = Maps.newHashMap();
            paramMap.put("pid",cid);
            paramMap.put("page",(page-1)*row);
            paramMap.put("size",row);
            if (!Objects.isNull(member) && !Objects.equals(mid,member.getId())){
                //已发布
                paramMap.put("status",2);
            }
            if (!Objects.isNull(member)){
                paramMap.put("mid",member.getId());
            }
            logger.info("=============开始查询=============");
            List<Map<String,Object>>  defaultConsultation = consultationMapper.getConsultationByMap(paramMap);
            if ("0".equals(String.valueOf(type)) || "2".equals(String.valueOf(type))){
                if (!Objects.isNull(defaultConsultation) && defaultConsultation.size()>0){
                    Map<String,Object> firstConsultation = defaultConsultation.get(0);
                    Long sid = Long.parseLong(firstConsultation.get("id").toString());
                    //评论咨询ID
                    //详情
                    firstConsultation.put("content",consultationAttachmentService.getConsultationAttachmentDetailByConsultation(sid));
                }
            }
            if ("4".equals(String.valueOf(type))){
                logger.info("=============查询资讯状态为4的信息=============");
                defaultConsultation.forEach(a->{
                    Long sid = Long.parseLong(a.get("id").toString());
                    //封面图
                    a.put("covers",consultationAttachmentService.getConsultationAttachmentCoverAddressByConsultationId(sid));
                    //详情
                    a.put("content",consultationAttachmentService.getConsultationAttachmentDetailByConsultation(sid));
                });
            }
            logger.info("=============信息详情 分页加载专题下信息结束=============");
            return ResultUtils.returnSuccess("成功",defaultConsultation);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            logger.info("=============信息详情 分页加载专题下信息失败=============");
            return ResultUtils.returnError("失败");
        }
    }
}
