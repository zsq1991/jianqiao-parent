package com.zc.main.service.consultation;


import com.zc.common.core.result.Result;
import com.zc.main.entity.member.Member;

import java.util.Map;
/**
 * @description ：咨询信息
 * @Created by  : gaoge
 * @Creation Date ： 2018/1/19 15:06
 * @version 1.0.0
 */
public interface ConsultationService {

    /**
     * @description 接口说明 删除资讯===用户中心
     * @author 王鑫涛
     * @date 9:08 2018/1/19
     * @version 版本号
     * @param id 资讯id
     * @param member 用户
     * @return
     */
    Result deleteConsultationById(Long id, Member member);
    /**
     * @description 接口说明 发布资讯===用户中心
     * @author 王鑫涛
     * @date 9:08 2018/1/19
     * @version 版本号
     * @param content 资讯内容
     * @param member 用户
     * @return
     */
    Result addConsultation(String content, Member member);

    /**
     * @description 接口说明 修改资讯
     * @author 王鑫涛
     * @date 9:08 2018/1/19
     * @version 版本号
     * @param content 资讯内容
     * @param member 用户
     * @return
     */
    Result updateConsultation(String content, Member member);

    Result publishConsultation(String id, String type, Member member);

    //Result getConsultationById(String id, Member member);

    // Result getConsultation(String id, Member member);
    Map<String, Object> getConsultationByIdaaa(Long id);

   /**
    * @description ：APP首页内容根据关键词搜索
    * @Created by  : gaoge
    * @Creation Date ： 2018/1/16 14:24
    * @version 1.0.0
    * @param : checktype  1精选  2分享
    * @return :
    */
    Result findconsultationinfo(Integer page, Integer rows, String checktype);

    /**
     * @param page 页码
     * @param rows 每页行数
     * @description ：APP求助  内容根据关键词搜索
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:43
     * @version 1.0.0
     */
    Result findConsultationInfoHelp(Integer page, Integer rows);

    /**
     * @param page      页码
     * @param rows      每页行数
     * @param checktype 类型 ： 1访谈  2口述
     * @description ：APP民间高手  内容根据关键词搜索
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:38
     * @version 1.0.0
     */
    Result findConsultationInfoPeople(Integer page, Integer rows, String checktype);

    /**
     * 个人中心 <我的发布>
     *
     * @param type   类型
     * @param member 用户
     * @param page
     * @param size
     * @return
     * @author huangxin
     * @data 2018/1/9 9:40
     * @Description: 个人中心 <我的发布>
     * @Version: 1.0.0
     */
    Result getMemberConsultation(String type, Member member, Integer page, Integer size);

    /**
     *
     * @description 接口说明 获取父级主题
     * @author 王鑫涛
     * @date 15:55 2018/1/19
     * @version 版本号
     * @param type  资讯类型
     * @param member    用户
     * @param page  当前页
     * @param size  每页显示的数量
     * @return
     */
    Result getParentConsultation(String type, Member member, Integer page, Integer size);

    Result getMemberConsultationByType(String cid, Member member, Integer page, Integer size);

    /**
     * @param page      页码
     * @param rows      行数
     * @param memberId  用户id
     * @param checktype 咨询类型 1 访谈  2 口述   3 求助   4 分享
     * @return
     * @description ：访谈详情页   点用户头像查看访谈
     * @Created by  : gaoge
     * @Creation Date ： 2018/1/16 11:07
     * @version 1.0.0
     */
    Result findConsultationAllByTouxiang(Integer page, Integer rows, String memberId, String checktype);

    /**
     * 用户个人中心   我的发布
     *
     * @param page
     * @param rows
     * @param member
     * @param checktype
     * @return
     * @author huangxin
     * @data 2018/1/8 13:58
     * @Description: 用户个人中心   我的发布
     * @Version: 1.0.0
     */
    Result findConsultationAllByMember(Integer page, Integer rows, Member member, String checktype);

    /**
     * 检测是否是高级用户
     *
     * @return
     */
    Result checkIsUserType(String memberId);

    /**
     * 查看全部  分页加载
     *
     * @return
     */
    Result findConsultationAllByFive(Integer page, Integer rows, String typeId);

    /**
     * 用户个人中心   我的发布   删除资讯信息
     *
     * @param checktype
     * @param member
     * @return
     */
    Result deleteConsultationById(Member member, Long typeId, String checktype);

    /**
     * 编辑回显咨询信息
     * @author huangxin
     * @data 2018/1/19 15:47
     * @Description: 编辑回显咨询信息
     * @Version: 3.2.0
     * @param cid 资讯id
     * @param member 用户
     * @return
     */
    Result backConsultation(String cid, Member member);

    /**
     * 信息详情<访谈、口述、求助、分享>
     * @param cid    资讯id
     * @param member 用户信息
     * @param row
     * @param type
     * @return
     * @author huangxin
     * @data 2018/1/9 15:35
     * @Description: 信息详情<访谈、口述、求助、分享>
     * @Version: 1.0.0
     */
    Result getConsultationDetail(String cid, Member member, int row, String type);

    /**
     * * @author:  wangxueyang[wxueyanghj@163.com]
     * @create:  2018/1/18 13:51
     * @desc:APP首页搜索接口
     * @version 1.0.0
     * @param page 页码
     * @param rows 页大小
     * @param info 关键词
     * @param phone 手机号
     * @param uuid uuid
     * @param checktype 1精选(全部) 2口述  3分享   4求助   5访谈
     * @return
     */
    Result searchConsultationInfo(Integer page, Integer rows, String info, String phone, String uuid,String checktype);

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
    Result getConsultationSubjectByPage(String cid, int page, int row, Member member);
}
