package com.zc.service.impl.consultation;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.zc.common.core.date.DateUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.consultation.Consultation;
import com.zc.main.entity.member.Member;
import com.zc.main.service.consultation.ConsultationService;
import com.zc.main.service.consultationattachment.ConsultationAttachmentService;
import com.zc.main.service.member.MemberService;
import com.zc.mybatis.dao.ConsultationMapper;
import com.zc.mybatis.dao.collectionConsulation.CollectionConsulationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private ConsultationAttachmentService consultationAttachmentService;

    @Override
    public Result deleteConsultationById(Long id, Member member) {
        try {
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
//                        int collection = collectionContentMapper.findOne(collectionId);
//                        collection.setType(1);//0收藏   1取消收藏
                        //修改资讯收藏状态
                        consultationMapper.updateByType1(collectionId);
                    }
                }
                parentConsultation.setCollectNum(collectNum-sonCollectNum);
                parentConsultation.setFabulousNum(fabulousNum-sonFabulousNum);
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
//                                CollectionConsultation collection = collectionContentDao.findOne(collectionId);
//                                collection.setType(1);//0收藏   1取消收藏
                                consultationMapper.updateByType1(collectionId);
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
//                        CollectionConsultation collection = collectionContentDao.findOne(collectionId);
//                        collection.setType(1);//0收藏   1取消收藏
                        consultationMapper.updateByType1(collectionId);
                    }
                }
            }

            ct.setIsDelete(1);
            consultationMapper.updateById(ct);
            return ResultUtils.returnSuccess("删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResultUtils.returnError("删除失败");
        }
    }

    @Override
    public Result addConsultation(String content, Member member) {
        return null;
    }

    @Override
    public Result updateConsultation(String content, Member member) {
        return null;
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

    @Override
    public Result getMemberConsultation(String type, Member member, Integer page, Integer size) {
        return null;
    }

    @Override
    public Result getParentConsultation(String type, Member member, Integer page, Integer size) {
        return null;
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
        return null;
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

    @Override
    public Result getConsultationDetail(String cid, Member member, int row, String type) {
        return null;
    }

    @Override
    public Result searchConsultationInfo(Integer page, Integer rows, String info, String phone, String uuid, String checktype) {
        return null;
    }

    @Override
    public Result getConsultationSubjectByPage(String cid, int page, int row, Member member) {
        return null;
    }
}
