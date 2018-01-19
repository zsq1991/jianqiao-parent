package com.zc.service.impl.attachment;

import com.alibaba.dubbo.config.annotation.Service;
import com.common.util.oss.UpLoadUtils;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.common.core.utils.DoubleUtils;
import com.zc.common.core.utils.UniqueUtils;
import com.zc.common.core.webmvc.springmvc.SpringMVCUtils;
import com.zc.main.entity.attachment.Attachment;
import com.zc.main.service.attachment.AttachmentService;
import com.zc.mybatis.dao.AttachmentMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author
 * @package : com.zc.service.impl.attachment
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : 三只小金
 * @Creation Date ：2018年01月18日17:23
 */
@Component
@Transactional(readOnly = true)
@Service(version = "1.0.0",interfaceClass=AttachmentService.class)
public class AttachmentServiceImpl  implements AttachmentService {

    private  static Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Autowired
    private AttachmentMapper attachmentMapper;

    /**
     * 上传接口
     * @description 上传接口
     * @author whl
     * @date 2018-01-11 10:21
     * @version 1.0.0
     * @param urlfile 文件流
     * @param backUrl 图片上传完成后  需要执行的类的方法 该方法默认参数都是Attachment  返回类型都是Result
     * @param module 上传文件夹名字
     * @param extendFile 允许扩展名数组
     * @param type 0图片 1视频
     * @return Result 0失败 1成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result mobileUploadAttachment(MultipartFile urlfile, Object[] backUrl, String module, String[] extendFile, String type) {
        String fileName = null;
        Attachment attachment=null;
        try {
            if (urlfile.isEmpty()) {
                return ResultUtils.returnError("上传文件失败");
            } else {

                String basePath = SpringMVCUtils.getRequest().getRealPath("/upload/" + module);
                File file = new File(basePath);
                if(!file.exists()){
                    file.mkdirs();
                }
                String path = null;
                fileName = urlfile.getOriginalFilename();

                boolean isFile = StringUtils.endsWithAny(StringUtils.lowerCase(fileName),extendFile );
                String sysFileName = UniqueUtils.getOrder() + "." + StringUtils.substringAfter(fileName, ".");

                //文件格式
                if (isFile) {
                    path = basePath + "/" + sysFileName;
                } else {
                    return ResultUtils.returnError("文件格式不正确,上传文件失败");
                }

                urlfile.transferTo(new File(path));

                //文件大小
                Double size= DoubleUtils.div(urlfile.getSize(),1024000.0, 2);//单位  M

                if(type.equals("0")){
                    //上传图片
                    if(size>2){

                        return ResultUtils.returnError("请上传2M以内的图片");
                    }
                    attachment = new Attachment();
                    attachment.setName(fileName);
                    attachment.setAddress("upload/" + module +"/"+ sysFileName);
                    //记录文件大小 (单位  M)
                    attachment.setMemory(size);

                    //设置图片的尺寸
                    BufferedImage bufferedImage=null;
                    try {
                        bufferedImage = ImageIO.read(new File(path));
                        int width = bufferedImage.getWidth();
                        int height = bufferedImage.getHeight();
                        attachment.setSizeInfo(width+","+height);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //上传到图片服务器
                    UpLoadUtils.alyUpload(module, sysFileName, path);

                } else if(type.equals("1")){
                    //上传视频
                    if(size>200){

                        return ResultUtils.returnError("请上传200M以内的视频");
                    }
                    attachment = new Attachment();
                    attachment.setName(fileName);
                    attachment.setAddress("upload/" + module +"/"+ sysFileName);
                    //记录文件大小 (单位  M)
                    attachment.setMemory(size);
                    //上传到图片服务器
                    UpLoadUtils.alyUpload(module, sysFileName, path);

                }
                //反射  保存实体
                Object obj=backUrl[0];
                Class<? extends Object> clazz=obj.getClass();
                Method method=clazz.getDeclaredMethod(backUrl[1].toString(), attachment.getClass());

                Object returnObj=method.invoke(obj,attachment);

                if(returnObj==null){
                    logger.error("上传图片回调方法返回数据为空");
                    return ResultUtils.returnError("上传失败");
                }
                return (Result) returnObj;

            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据

            logger.error("图片上传发生异常:"+e.getMessage());

            return ResultUtils.returnError("上传失败");
        }
    }

    /**
     * 上传图片保存数据
     * @description 上传图片保存数据
     * @author whl
     * @date 2018-01-11 10:21
     * @version 1.0.0
     * @param attachment 图片实体类
     * @return Result 0失败 1成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveAttachment(Attachment attachment) {
        //保存图片信息
         attachmentMapper.insert(attachment);
        return ResultUtils.returnSuccess("上传成功", attachment);
    }
}
