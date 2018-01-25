package com.zc.main.service.attachment;

import com.zc.common.core.result.Result;
import com.zc.main.entity.attachment.Attachment;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author
 * @package : com.zc.main.service.attachment
 * @progect : jianqiao-parent
 * @Description : 上传图片
 * @Created by : 三只小金
 * @Creation Date ：2018年01月18日17:22
 */
public interface AttachmentService {

    /**
     * 上传接口
     * @description 上传接口
     * @author whl
     * @date 2018-01-11 10:17
     * @version 1.0.0
     * @param urlfile 文件流
     * @param backUrl 图片上传完成后  需要执行的类的方法 该方法默认参数都是Attachment  返回类型都是Result
     * @param module 上传文件夹名字
     * @param extendFile 允许扩展名数组
     * @param type 0图片 1视频
     * @return Result 0失败 1成功
     */
    public Result mobileUploadAttachment(MultipartFile urlfile, Object[] backUrl, String module, String[] extendFile, String type);


    /**
     * 保存附件
     * @description 保存附件
     * @author whl
     * @date 2018-01-11 10:20
     * @version 1.0.0
     * @param attachment 附件实体
     * @return Result 0失败 1成功
     */
    public Result saveAttachment(Attachment attachment);

    /**
     * 根据附件id获取系统附件
     * @description 接口说明 根据附件id获取系统附件
     * @author 王鑫涛
     * @date 16:04 2018/1/18
     * @version 版本号
     * @param id 系统附件id
     * @return
     */
    Attachment findOne(Long id);

}
