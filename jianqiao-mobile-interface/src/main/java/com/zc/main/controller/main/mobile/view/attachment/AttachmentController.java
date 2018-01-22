package com.zc.main.controller.main.mobile.view.attachment;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.common.util.uploaddilename.UploadFileName;
import com.zc.common.core.result.Result;
import com.zc.main.service.attachment.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author
 * @package : com.zc.main.controller.main.mobile.view.attachment
 * @progect : jianqiao-parent
 * @Description : 上传图片
 * @Created by : 三只小金
 * @Creation Date ：2018年01月18日17:21
 */
@RequestMapping("mobile/view/attachment")
@Controller
public class AttachmentController {

    private static Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    @DubboConsumer(version = "1.0.0",timeout = 30000)
    private AttachmentService attachmentService;

    /**
     * APP 上传接口
     * @author huangxin
     * @data 2018/1/18 17:38
     * @Description: APP 上传接口
     * @Version: 3.2.0
     * @param urlfile 文件流
     * @param type 0图片 1视频
     * @return
     */
    @RequestMapping("upload")
    @ResponseBody
    public Result mobileUploadSaleCircleAttachment(
            @RequestParam("urlfile") MultipartFile urlfile,
            @RequestParam(value="type",defaultValue="0",required=false)String type
    ){

        String[] extendFile=new String[] { ".png", ".jpg", ".jpeg", ".bmp", ".gif" ,".rm",".avi",".mp4",".3gp",".wma",".rmvb",".flash",".mid"};
        String module= UploadFileName.MEMBER_HEADIMG.getName();
        return attachmentService.mobileUploadAttachment(urlfile,new Object[]{attachmentService,"saveAttachment"},module,extendFile,type);
    }
}
