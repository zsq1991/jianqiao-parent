package com.common.util.oss;

import com.google.common.collect.Maps;
import com.zc.common.core.result.Result;
import com.zc.common.core.result.ResultUtils;
import com.zc.common.core.utils.UniqueUtils;
import com.zc.common.core.webmvc.springmvc.SpringMVCUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * @author system
 */
public class UpLoadUtils {
	
	private static final Log logger = LogFactory.getLog(UpLoadUtils.class);
	 
	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	
	public static String BASE_PATH ;
	/**
	 * 图片上传路径
 	 */
	public static String IMAGE_UPLOAD_PATH;
	
	public UpLoadUtils(){
		UpLoadUtils.BASE_PATH =request.getSession().getServletContext().getRealPath(File.separator);
		UpLoadUtils.IMAGE_UPLOAD_PATH =UpLoadUtils.BASE_PATH+"/upload/";
	}
	
	public static String getSerial(Date date, int index) {
		long msel = date.getTime();
		SimpleDateFormat fm = new SimpleDateFormat("MMddyyyyHHmmssSS");
		msel += index;
		date.setTime(msel);
		String serials = fm.format(date);
		return serials;
	}

	/**
	 * 检查是否是图片格式
	 * @param imgStr
	 * @return
	 */
	public static boolean checkIsImage(String imgStr) {
		boolean flag = false;
		if (imgStr != null) {
			String gif=".gif";
			String jpg=".jpg";
			String jpeg=".jpeg";
			String png=".png";
			if (gif.equalsIgnoreCase(imgStr)
					|| jpg.equalsIgnoreCase(imgStr)
					|| jpeg.equalsIgnoreCase(imgStr)
					|| png.equalsIgnoreCase(imgStr)) {
				flag = true;
			}
		}
		return flag;
	}

	public static Date strToDate(String str) throws ParseException {
		return new SimpleDateFormat("MM/dd/yyyy").parse(str);
	}

	public static Result saveFile(Map<String, String> imageMap, String module) {// 内部调用上传
		logger.info("=========================================saveFile方法========================");

		// imageUploadPath 图片上传路径
		Result appResult = new Result();
		//默认是失败
		appResult.setCode(0);
		// 保存文件拓展名
		String extName = ".jpg";
		String android="Android";
		String ios="IOS";
		if(android.equals(module)){
			extName=".apk";
		}else if(ios.equals(module)){
			extName=".ipa";
		}
		// 保存新的文件名
		String newFileName = "";
		// 保存当前时间
		String nowTimeStr = "";
		SimpleDateFormat sDateFormat;
		Random r = new Random();
		//实际路径
		String realPath = BASE_PATH;
		// 上传目录
		String savePath = IMAGE_UPLOAD_PATH + module + "/";
		Set<Entry<String, String>> set = imageMap.entrySet();
		Map<String, String> resultMap = Maps.newHashMap();
		for (Entry<String, String> entry : set) {
			String imageName = entry.getKey();
			String imageStr = entry.getValue();
			byte[] uploadImage = decode(imageStr);
			FileOutputStream fos = null;
			File fileupload = null;
			try {
				fileupload = new File("temp.te");
				if (!fileupload.exists()) {
					fileupload.createNewFile();
				}
				logger.info(savePath + "_______________size:"
						+ uploadImage.length + "====================="
						+ realPath);
				fos = new FileOutputStream(fileupload);
				fos.write(uploadImage);
				fos.flush();
				fos.close();
				// 获取文件大小
				long fileSize = fileupload.length();
				// 1m=1024*1024
				if (fileSize > 1024 * 1024*30) {
					appResult.setMsg(imageName + "上传的文件不能大于30M");
				} else {
					// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
					// 获取随机数
					int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000;
					// 时间格式化的格式
					sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					// 当前时间
					nowTimeStr = sDateFormat.format(new Date());
					// 获取拓展名
					// 文件重命名后的名字
					newFileName = nowTimeStr + rannum + extName;
					String filePath = savePath + newFileName;
					filePath = filePath.replace("\\", "/");
					String msgUrl = "upload/" + module + "/" + newFileName;
					// 检查上传的是否是图片
					// if (UpLoadUtils.checkIsImage(extName)) {
					FileUtils.copyFile(fileupload, new File(filePath));
					//alyUpload(module,newFileName,realPath+img_url,InitParamPc.getInitParam());
					appResult.setMsg(imageName + "，上传成功");
					resultMap.put(imageName, msgUrl);
					appResult.setCode(1);
					// } else {
					// appResult.setError_info(imageName
					// + "上传的文件类型错误，请选择jpg,jpeg,png和gif格式的图片!");
					// }

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				appResult.setMsg(imageName + "上传失败，出错啦!"
						+ e.getMessage());
			} finally {

				try {
					if (fos != null) {
						fos.close();
					}
					fileupload.delete();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
		appResult.setContent(resultMap);
		return appResult;
	}

	public static String encode(byte[] byteArray) {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		return base64Encoder.encode(byteArray);
	}

	public static byte[] decode(String base64EncodedString) {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		try {
			return base64Decoder.decodeBuffer(base64EncodedString);
		} catch (IOException e) {
			return null;
		}
	}

	public static String getImageBase64(String image) {
		String base64str = "";
		base64str = encode(getBytes(image));
		return base64str;
	}

	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return buffer;
	}
	/**
	 * springMvc上传
	 * @param urlfile
	 * @param
	 * @return
	 */
	public static Result springUploadFile(MultipartFile urlfile, String module) {
		String fileName = null;
		try {
			if (urlfile.isEmpty()) {
				return ResultUtils.returnError("上传文件失败");
			} else {
				String basePath = SpringMVCUtils.getRequest().getSession().getServletContext().getRealPath("/upload/"+module);
				File f = new File(basePath);
				f.mkdirs();
				String path = null;
				fileName = urlfile.getOriginalFilename();
				boolean isFile= StringUtils.endsWithAny(StringUtils.lowerCase(fileName), new String[] { ".png", ".jpg",".jpeg",".bmp",".gif"});
				String sysFileName = UniqueUtils.getOrder() + "."+ StringUtils.substringAfter(fileName, ".");
				if (isFile) {
					path = basePath + "/" + sysFileName;
				} else {
					return ResultUtils.returnError("文件格式不正确,上传文件失败");
				}
				urlfile.transferTo(new File(path));
				//alyUpload(module,sysFileName,path,InitParamPc.getInitParam());
				return ResultUtils.returnSuccess("上传成功", "upload/"+module+"/"+sysFileName);
			}
			} catch (Exception e) {
				//e.printStackTrace();
				return ResultUtils.returnError("上传失败");
			}
	}
	
	/***
	 * 上传到图片服务器
	 * @param module  上传的文件夹名 
	 * @param sysFileName 图片名
	 * @param path 路劲
	 */
	public static void alyUpload(String module,String sysFileName,String path)
	{
		 String isOpen="true";
		 String accessId="e6wt7YMUZEru6TOD";
		 String accessKey="QLhDKpV0Rez9fsH6oIOXbmks4sd7WE";
		 String bucketName="yst-images";
		// 是否开启sso********把图片保存到云服务器上
		String str="true";
		if (str.equals(isOpen))
		{
	       OSSFileUpLoad.createOss(bucketName,"upload/"+module+"/" + sysFileName, path, accessId, accessKey);
		}
	}
	
}