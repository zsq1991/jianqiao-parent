package com.zc.mybatis.dao;


import com.common.util.mybatis.BasicMapper;
import com.zc.common.core.orm.mybatis.MyBatisRepository;
import com.zc.main.entity.consultationcomment.ConsultationComment;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface ConsultationCommentMapper extends BasicMapper<ConsultationComment> {

    List<Map<String, Object>> getCommentList(Map<String, Object> paramMap);

    Map<String, Object> getSonCommentList(Long cid);

    /**
     * @description ：查询咨询评论是否存在，判断删除字段
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 17:07
     * @version 1.0.0
     */
    public int findHasConsultationCommentById(Long commentid);

    /**
     * 查询咨询顶级评论详情
     *
     * @param paramMap
     * @return
     */
    public Map<String, Object> getTopConsultationCommentDetailById(Map<String, Object> params);

    /**
     * 查询咨询顶级评论下的回复列表
     *
     * @param topid
     * @return
     */
    public List<Map<String, Object>> findTopAfterCommentListByTopId(Map<String, Object> params);

    /**
     * 这个是顶级评论下的相互评论回复，给回复评论显示效果需要拼接//@昵称：父级评论内容，此sql查询的就是需要拼接的父级评论
     *
     * @param parentid 父级评论id
     * @return
     */
    public Map<String, Object> getCommentByParentIdDetail(Long parentid);

    /**
     * @param id 访谈id
     * @return
     */
    public Map<String, Object> getConsultationById(String id);

    /**
     * 通过父类id获取子的2个id
     *
     * @param id
     * @return
     */
    public List<Map> getCommentSonIdByPid(Long id);

    /**
     * 保存咨询评论
     *
     * @param consultationComment
     * @return
     */
    public Long saveConsultation(ConsultationComment consultationComment);
}
