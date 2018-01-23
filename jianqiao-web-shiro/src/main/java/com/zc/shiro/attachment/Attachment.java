package com.zc.shiro.attachment;

import com.common.util.oss.OSSClientUtil;
import com.google.common.collect.Maps;
import com.zc.common.core.shiro.Result;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * @项目：tianyi-api-platform
 *
 * @描述：图片上传
 *
 * @作者： Mr.Shu  ggg
 *
 * @创建时间：2017年3月20日
 *
 * @Copyright @2017 by Mr.Shu
 */
@Controller
@RequestMapping("web/attachment")
public class Attachment {
    @Autowired
    OSSClientUtil ossClientUtil;
    /**
     * 上传最大值5M
     */
    private static final int MAX_POST_SIZE = 5 * 1024 * 1024;

    public static synchronized String getOrder() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(6);
    }
    @RequestMapping("/attachmenturl")
    public String getAttachmentUrl(){
        return "addAtachments/addattachments";
    }

    /**
     * 上传图片
     *
     * @param urlfile
     * @param module
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result springUploadFile(MultipartFile urlfile, String module) {

        String fileName = null;

        //判断包名，如果为空返回
        if (module == null || "".equals(module)) {
            module="adflash";
            //return new Result(false, "1", "请先自定义module");
        }

        try {
            if (urlfile.isEmpty()) {
                return new Result(false, "1", "图片不存在");
            } else {
                //如果上传图片大于5M不予上传
                if (urlfile.getSize() > MAX_POST_SIZE) {
                    return new Result(false, "1", "上传图片不能大于5M");
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String date = sdf.format(new Date());
                String url = module + "/" + date;
                String path = null;
                fileName = urlfile.getOriginalFilename();
                boolean isFile = StringUtils.endsWithAny(StringUtils.lowerCase(fileName), new String[]{".png", ".jpg", ".jpeg", ".bmp", ".gif"});
                String sysFileName = getOrder() + "." + fileName.substring((fileName.lastIndexOf(".") + 1));
                if (isFile) {
                    path = url + "/" + sysFileName;
                    Object address = ossClientUtil.putObject(path, urlfile);
                    System.out.print(address);
                    return new Result(true, address, 0);
                } else {
                    return new Result(false, "1", "图片格式不正确");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "1", "上传失败");
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/uploadAdattchmentImage", method = RequestMethod.POST)
    public Result uploadAdattchmentImage(MultipartFile urlfile) {
        String module = "suppler";
        return this.springUploadFile(urlfile, module);

    }


    /**
     * 商品详情富文本上传图片
     *
     * @param imgFile
     * @param module
     * @return
     * @author  yyg
     */
    @ResponseBody
    @RequestMapping(value = "/uploadProductImage", method = RequestMethod.POST)
    public Map<String,Object> springUploadProductFile2(MultipartFile imgFile, String module) {
        String fileName = null;
        if (module == null || "".equals(module)) {
            module = "default";
        }
        try {
            if (imgFile.isEmpty()) {
                return getError("文件不存在");
            } else {
                if (imgFile.getSize() > MAX_POST_SIZE) {
                    return getError("文件超出最大限制");
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String date = sdf.format(new Date());
                String url = module + "/" + date;
                String path = null;
                fileName = imgFile.getOriginalFilename();
                boolean isFile = StringUtils.endsWithAny(StringUtils.lowerCase(fileName), new String[]{".png", ".jpg", ".jpeg", ".bmp", ".gif"});
                String sysFileName = getOrder() + "." + fileName.substring((fileName.lastIndexOf(".") + 1));
                if (isFile) {
                    path = url + "/" + sysFileName;
                    Object address = ossClientUtil.putObject(path, imgFile);
                    System.out.print(address);
                    Map<String, Object> succMap = Maps.newHashMap();
                    succMap.put("error", 0);
                    succMap.put("url",address);
                    return succMap;
                } else {
                    return getError("上传失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getError("上传失败");
        }
    }
    private Map<String, Object> getError(String errorMsg) {
        Map<String, Object> errorMap = Maps.newHashMap();
        errorMap.put("error", 1);
        errorMap.put("message", errorMsg);
        return errorMap;
    }
}
