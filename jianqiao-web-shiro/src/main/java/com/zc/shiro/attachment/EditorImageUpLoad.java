package com.zc.shiro.attachment;

import com.common.util.oss.OSSClientUtil;
import com.google.common.collect.Maps;
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
 * @description 富文本上传图片
 * @author system
 * @date 2018-01-25 17:28
 * @version 1.0.0
 */
@Controller
public class EditorImageUpLoad {

    @Autowired
    OSSClientUtil ossClientUtil;
    /**
     * 上传最大值5M
     */
    private static final int MAX_POST_SIZE = 5 * 1024 * 1024;

    public static synchronized String getOrder() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(6);
    }


    /**
     * 商品详情富文本上传图片
     *
     * @param imgFile
     * @param module
     * @return
     * @author yyg
     */
    @ResponseBody
    @RequestMapping(value = "/uploadProductImage", method = RequestMethod.POST)
    public Map<String, Object> springUploadProductFile(MultipartFile imgFile, String module) {
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
                    succMap.put("url", address);
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
