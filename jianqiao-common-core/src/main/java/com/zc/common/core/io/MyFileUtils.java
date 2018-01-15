package com.zc.common.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.zeroturnaround.zip.ZipUtil;


/**
 * 这个类需要zt-zip.jar包
 * 
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-3-26 上午11:29:08
 * 
 */
public class MyFileUtils {
	private MyFileUtils() {
		throw new AssertionError();
	}

	/**
	 * 文件进行压缩成zip的
	 * 
	 * @param sourceFile
	 * @param zipFile
	 * @throws IOException
	 */
	public static void packFileZip(File sourceFile, File zipFile) {
		ZipUtil.pack(sourceFile, zipFile);
	}

	/**
	 * 解压缩文件必须是zip的
	 * 
	 * @param sourceZip
	 * @param parseFile
	 * @throws IOException
	 */
	public static void unpackFileZip(File sourceZip, File parseFile) {
		ZipUtil.unpack(sourceZip, parseFile);
	}

	/**
	 * 获取系统的项目路径
	 * 
	 * @return
	 */
	public static String getSystemFileName() {
		return System.getProperty("user.dir");
	}

	/**
	 * 拷贝文件路径，不拷贝文件
	 * 
	 * @param srcDir
	 * @param targetDir
	 */
	public static void copyDirNoFile(final String srcDir, final String targetDir) {
		String srcDirs = getNormalFileName(srcDir);
		String targetDirs = getNormalFileName(targetDir);
		List<File> list = new ArrayList<File>();
		CollectionUtils.addAll(list, new File(srcDirs).listFiles());
		try {
			copyDirnofile(list, targetDirs, srcDirs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取标准的文件目录名称
	 * 
	 * @param srcDir
	 * @return
	 */
	public static String getNormalFileName(final String srcDir) {
		String srcDirs = "";
		if (StringUtils.isNotBlank(FilenameUtils.getBaseName(srcDir))) {
			srcDirs = srcDir + File.separator;
		} else {
			srcDirs = srcDir;
		}
		return srcDirs;
	}

	private static void copyDirnofile(List<File> list, String target, String srcdir)
			throws IOException {
		for (File file : list) {
			File file2 = new File(target
					+ StringUtils.substringAfter(file.getCanonicalPath(), srcdir));
			if (file.isDirectory()) {
				file2.mkdirs();
				List<File> lisst = new ArrayList<File>();
				CollectionUtils.addAll(lisst, file.listFiles());
				copyDirnofile(lisst, target, srcdir);
			}
		}
	}

	/**
	 * 将输入流写入到输出流
	 * 
	 * @param outputStream
	 * @param inputStream
	 */
	public static void write(final OutputStream outputStream, final InputStream inputStream) {
		try {
			IOUtils.copy(inputStream, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}
}
