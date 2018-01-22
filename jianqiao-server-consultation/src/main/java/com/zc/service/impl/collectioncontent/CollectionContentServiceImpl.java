package com.zc.service.impl.collectioncontent;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.zc.common.core.date.DateUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.main.entity.collectionconsultation.CollectionConsultation;
import com.zc.main.entity.consultation.Consultation;
import com.zc.main.entity.member.Member;
import com.zc.main.service.collectioncontent.CollectionContentService;
import com.zc.main.service.consultationattachment.ConsultationAttachmentService;
import com.zc.main.service.member.MemberService;
import com.zc.mybatis.dao.ConsultationMapper;
import com.zc.mybatis.dao.CollectionContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : wangxueyang[wxueyanghj@163.com]
 * @version ： 1.0.0
 * @package : com.zc.service.impl.collectioncontent
 * @progect : jianqiao-parent
 * @Description : 收藏  业务实现
 * @Creation Date ：2018年01月17日11:46
 */
@Component
@Service(version = "1.0.0", interfaceClass = CollectionContentService.class)
@Transactional(readOnly = true,rollbackFor=Exception.class)
public class CollectionContentServiceImpl implements CollectionContentService {

    private static Logger logger = LoggerFactory.getLogger(CollectionContentServiceImpl.class);

    @Autowired
    private CollectionContentMapper collectionContentMapper;

    @Autowired
    private ConsultationMapper consultationMapper;

    @DubboConsumer(version = "1.0.0",timeout = 30000)
    private ConsultationAttachmentService consultationAttachmentService;

    @DubboConsumer(version = "1.0.0", timeout = 30000, check = false)
    private MemberService memberService;

    @Override
    public Result mycollection(Member member, Integer page, Integer rows) {
        logger.info("获取收藏列表传入参数==》 member：" + member.toString() + " page：" + page + "rows:" + rows);
        List<Map<String, Object>> consultationInfoList = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> param = Maps.newHashMap();
            param.put("id", member.getId());
            param.put("startIndex", page);
            param.put("endIndex", rows);
            // 得到当前用户的收藏信息
            List<Map<String, Object>> myCollentent = collectionContentMapper.getMyCollentent(param);
            if (myCollentent.size() <= 0) {
                return ResultUtils.returnError("没有收藏信息");
            }
            // 获取当前所有子类的父id
            Set<String> set = new HashSet<String>();
            for (Map<String, Object> consultationId : myCollentent) {
                Map<String, Object> consulationDetail = consultationMapper.getConsultationById(consultationId.get("consultation_id").toString());
                if (null == consulationDetail) {
                    continue;
                }
                String type = (String) consulationDetail.get("type").toString();
                if ("1".equals(type) || "3".equals(type)) {
                    String consultationParentId = consulationDetail.get("pid").toString();
                    set.add(consultationParentId);
                } else if ("0".equals(type) || "2".equals(type) || "4".equals(type) || "6".equals(type)) {
                    String consultationParentId = consulationDetail.get("id").toString();
                    set.add(consultationParentId);
                }
            }


            // 判断当前
            for (String consultationParentId : set) {
                // String consultation_id = map.get("consultation_id");  咨询详情
                Map<String, Object> consulationDetail = consultationMapper.getConsultationById(consultationParentId);
                if (null == consulationDetail) {
                    continue;
                }
                // 处理时间格式
                String chidAlreadyTime1 = DateUtils.dateFormat((Date) consulationDetail.get("createdTime"),"yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat chiddf1 = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
                String chidNowTime1 = chiddf1.format(new Date());
                String chidTime11 = chidAlreadyTime1.subSequence(0, 10).toString();
                String chidTime22 = chidNowTime1.subSequence(0, 10).toString();
                if (chidTime11.equals(chidTime22)) {// 同一天
                    String chidcreatedTime = chidAlreadyTime1.subSequence(11, 16).toString();// 截取当天
                    // 时，分
                    consulationDetail.put("createdTime", chidcreatedTime);
                } else {// 不同一天
                    String chidcreatedTime = chidAlreadyTime1.subSequence(0, 10).toString();// 截取当天
                    // 年，月，日
                    consulationDetail.put("createdTime", chidcreatedTime);
                }
                // 获取当前咨询的type
                String type = (String) consulationDetail.get("type").toString();
                boolean isMore = false;// 是否是主题或访谈的标识 false 不是 true 是
                List<Map<String, Object>> consultationChidList = new ArrayList<Map<String, Object>>();
                if ("0".equals(type) || "2".equals(type)) {// 0是访谈主题 1访谈内容 2口述主题
                    // 3口述内容 4求助 5回答 6分享
                    // 判断访谈和口述是否有内容
                    Integer count = consultationMapper.getCountById(Long.valueOf(consulationDetail.get("id").toString()));
                    if (count == 0) {
                        continue;
                    }
                    isMore = true;
                    consultationChidList = consultationMapper.findConsultationChidById(Long.valueOf(consulationDetail.get("id").toString()));// 根据访谈或口述的id查询其子类
                    // 取详情内容
                    Object detailContentChid = "";
                    if (consultationChidList.size() > 0) {
                        for (Map<String, Object> consultationChidInfo : consultationChidList) {
                            // 处理时间格式
                            String chidAlreadyTime = DateUtils.dateFormat((Date) consultationChidInfo.get("createdTime"),"yyyy/MM/dd HH:mm:ss");
                            SimpleDateFormat chiddf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");// 设置日期格式
                            String chidNowTime = chiddf.format(new Date());
                            String chidTime1 = chidAlreadyTime.subSequence(0, 10).toString();
                            String chidTime2 = chidNowTime.subSequence(0, 10).toString();
                            if (chidTime1.equals(chidTime2)) {
                                // 同一天
                                String chidcreatedTime = chidAlreadyTime.subSequence(11, 16).toString();// 截取当天
                                // 时，分
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            } else {
                                // 不同一天
                                String chidcreatedTime = chidAlreadyTime.subSequence(0, 10).toString();// 截取当天
                                // 年，月，日
                                consultationChidInfo.put("createdTime", chidcreatedTime);
                            }

                            // 图片地址
                            String addressChid = "";
                            // 处理咨询图片
                            List<Map<String, Object>> consultationChidAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consultationChidInfo.get("id").toString()));
                            consultationChidInfo.put("address", consultationChidAttachmentList);

                            // 处理访谈和口述专题的详情内容
                            Map<String, Object> map1 = consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consulationDetail.get("id").toString()));
                            if (null == map1 || map1.size() == 0) {
                                detailContentChid = "";
                            } else {
                                detailContentChid = map1.get("detailContent");
                            }
                            consultationChidInfo.put("detailContentChid", detailContentChid);
                        }
                    }

                    consulationDetail.put("isMore", isMore);
                    consulationDetail.put("consultationChidList", consultationChidList);

                } else {

                    consulationDetail.put("isMore", isMore);
                    consulationDetail.put("consultationChidList", consultationChidList);
                }

                // 处理咨询图片
                List<Map<String, Object>> consultationAttachmentList = consultationAttachmentService.findConsultationAttachmentByConsultationId(Long.valueOf(consulationDetail.get("id").toString()));
                consulationDetail.put("address", consultationAttachmentList);

                // 取详情内容
                Object detailContent = "";
                if ("4".equals(type) || "6".equals(type)) {
                    Map<String, Object> map2 = consultationAttachmentService.findDetailContentByConsultationId(Long.valueOf(consulationDetail.get("id").toString()));
                    if (null == map2 || map2.size() == 0) {
                        detailContent = "";
                    } else {
                        detailContent = map2.get("detailContent");
                    }
                }
                consulationDetail.put("detailContent", detailContent);
                consultationInfoList.add(consulationDetail);
            }
        }catch (Exception e){
            logger.info("获取收藏列表异常："+e.getMessage());
            e.printStackTrace();
        }
        logger.info("获取收藏列表成功!");
        return ResultUtils.returnSuccess("请求成功", consultationInfoList);
    }
    /**
     * @description 接口说明 根据资讯id查询收藏记录
     * @author 王鑫涛
     * @date 9:24 2018/1/18
     * @version 版本号
     * @param consulationId 资讯id
     * @return
     */
    @Override
    public CollectionConsultation findOne(Long consulationId) {
        return collectionContentMapper.findOne(consulationId);
    }
    /**
     * @description 接口说明 修改收藏资讯状态
     * @author 王鑫涛
     * @date 9:33 2018/1/18
     * @version 版本号
     * @param collectionConsultation 收藏资讯
     * @return
     */
    @Override
    public int updateById(CollectionConsultation collectionConsultation) {
        return collectionContentMapper.updateById(collectionConsultation);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result collectionContent(Member member, Long consultationId) {
        logger.info("收藏内容传入参数==》member:"+member.toString()+ " consultationId:" +consultationId);
        Result result = new Result();
        if(Objects.isNull(member.getId())||Objects.isNull(consultationId)){
            return ResultUtils.returnError("参数为空");
        }
        //加锁用户
        Member rowLock = memberService.getLockOne(member.getId());
        //根据资讯的id获取到资讯的member的id【作者】
        Consultation consultationinfo = consultationMapper.getOne(consultationId);
        if(consultationinfo==null){
            return ResultUtils.returnError("当前收藏的资讯不存在或已被删除");
        }
        Long consultationmemberid = consultationinfo.getMemberId();
        logger.info("consultationmemberid : "+consultationmemberid);
        //0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享 当typeflag=1和3的时候，collectNum将对应的主题0和2也要加1，其他不变！
        Integer typeflag = consultationinfo.getType();
        try {
            //判断收藏表有没有记录
            CollectionConsultation cctype = collectionContentMapper.getCollectionByMemberIdAndConsultationId(member.getId(),consultationId);
            //收藏	0收藏   1取消收藏
            if(cctype==null||cctype.getType()==null){
                //1保存type... 实例化CollectionConsultation
                CollectionConsultation ccdb = new CollectionConsultation();
                //2资讯的id--资讯
                Consultation consultation = new Consultation();
                consultation.setId(consultationId);
                //3哪个用户发的资讯--作者
                Member consultationMember = new Member();
                consultationMember.setId(consultationmemberid);
                //4哪个用户的收藏--用户
                Member memberdb = new Member();
                memberdb.setId(member.getId());
                //当typeflag=1和3的时候，collectNum将对应的主题0和2也要1，其他不变！ 0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享
                if(typeflag==1 ||typeflag==3 ){
                    //获取到当前资讯信息-得到父级的资讯ID
                    Long consultationParentId = consultationinfo.getConsultationId();
                    Consultation con = consultationMapper.findOne(consultationParentId);//洋大侠
                    con.setCollectNum(con.getCollectNum()==null?0+1:con.getCollectNum()+1);
                    con.setUpdateTime(new Date());
                    if (consultationMapper.updateConsultationById(con)>0){
                        logger.info("更新文章收藏次数成功！");
                    }
                }
                //====================================================================
                //维护到CollectionConsultation表中
                ccdb.setType(0);
                ccdb.setConsultationId(consultation.getId());
                ccdb.setConsultationMemberId(consultationMember.getId());
                ccdb.setMemberId(memberdb.getId());
                //维护到member表中
                rowLock.setConsulationNum(rowLock.getConsulationNum()==null?1:rowLock.getConsulationNum()+1);
                //做保存
                rowLock.setUpdateTime(new Date());
                if ( memberService.updateById(rowLock) > 0 ){
                    logger.info("更新用户收藏咨询数量成功!");
                }
                //维护到Consultation表中
                consultationinfo.setCollectNum(consultationinfo.getCollectNum()==null?1:consultationinfo.getCollectNum()+1);
                consultationinfo.setUpdateTime(new Date());
                if (consultationMapper.updateConsultationById(consultationinfo) > 0){
                    logger.info("更新咨询收藏数成功");
                }
                ccdb.setUpdateTime(new Date());
                if (collectionContentMapper.insert(ccdb)>0){
                    logger.info("保存收藏记录成功!");
                }
                return ResultUtils.returnSuccess("收藏成功");
            }
            //type为1取消收藏，0是已经收藏
            if(cctype!=null && cctype.getType()!=null){
                Integer type = cctype.getType();

                if(type==0){
                    if(typeflag==1 ||typeflag==3 ){
                        //获取到当前资讯信息-得到父级的资讯ID
                        Long consultationParentId = consultationinfo.getConsultationId();
                        Consultation con = consultationMapper.findOne(consultationParentId);//洋大侠
                        Long collectNum = con.getCollectNum()==null?0L:con.getCollectNum();
                        con.setCollectNum(collectNum-1<0?0:collectNum-1);
                        con.setUpdateTime(new Date());
                        if (consultationMapper.updateConsultationById(con)>0){
                            logger.info("更新文章收藏次数成功！");
                        }
                    }
                    //维护到Consultation表中
                    consultationinfo.setCollectNum(consultationinfo.getCollectNum()==null?0:consultationinfo.getCollectNum()-1);
                    //维护到member中
                    Long number=rowLock.getConsulationNum()==null?0:rowLock.getConsulationNum()-1;
                    cctype.setType(1);
                    rowLock.setConsulationNum(number);
                    rowLock.setUpdateTime(new Date());
                    if ( memberService.updateById(rowLock) > 0 ){
                        logger.info("更新用户收藏咨询数量成功!");
                    }
                    consultationinfo.setUpdateTime(new Date());
                    if (consultationMapper.updateConsultationById(consultationinfo) > 0){
                        logger.info("更新咨询收藏数成功");
                    }
                    cctype.setUpdateTime(new Date());
                    if (collectionContentMapper.updateById(cctype)>0){
                        logger.info("更新收藏记录成功!");
                    }
                    result.setCode(1);
                    result.setMsg("取消收藏");
                }else if(type==1){
                    //type 0是访谈主题  1访谈内容 2口述主题  3口述内容 4求助 5回答  6分享 当typeflag=1和3的时候，collectNum将对应的主题0和2也要1，其他不变
                    if(typeflag==1 ||typeflag==3 ){
                        //获取到当前资讯信息-得到父级的资讯ID
                        Long consultationParentId = consultationinfo.getConsultationId();
                        Consultation con = consultationMapper.findOne(consultationParentId);//洋大侠
                        Long collectNum = con.getCollectNum()==null?0L:con.getCollectNum();
                        con.setCollectNum(collectNum-1<0?0:collectNum-1);
                        con.setUpdateTime(new Date());
                        if (consultationMapper.updateConsultationById(con)>0){
                            logger.info("更新文章收藏次数成功！");
                        }
                    }
                    //维护到Consultation表中
                    consultationinfo.setCollectNum(consultationinfo.getCollectNum()==null?0+1:consultationinfo.getCollectNum()+1);
                    //维护到member中
                    Long number=rowLock.getConsulationNum()==null?0+1:rowLock.getConsulationNum()+1;
                    cctype.setType(0);
                    rowLock.setConsulationNum(number);
                    rowLock.setConsulationNum(number);
                    rowLock.setUpdateTime(new Date());
                    if ( memberService.updateById(rowLock) > 0 ){
                        logger.info("更新用户收藏咨询数量成功!");
                    }
                    consultationinfo.setUpdateTime(new Date());
                    if (consultationMapper.updateConsultationById(consultationinfo) > 0){
                        logger.info("更新咨询收藏数成功");
                    }
                    cctype.setUpdateTime(new Date());
                    if (collectionContentMapper.updateById(cctype)>0){
                        logger.info("更新收藏记录成功!");
                    }
                    result.setCode(1);
                    result.setMsg("收藏成功");
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            logger.error(e.getMessage(),e);
            return ResultUtils.returnError("收藏内容异常");
        }
        logger.info("成功");
        return result;
    }
}
