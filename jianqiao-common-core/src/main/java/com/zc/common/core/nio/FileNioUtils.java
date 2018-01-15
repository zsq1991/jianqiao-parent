package com.zc.common.core.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-3-22 下午07:43:34
 * 
 */
public class FileNioUtils {
	/**
	 * 利用nio读取文件的内容的方法
	 * 
	 * @param fileName
	 * @param buf 字节缓冲区的大小
	 * @param buff 字节数组的大小
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static byte[] readFile(String fileName, int buf, int buff) throws IOException {
		File file = new File(fileName);
		ByteBuffer byteBuffer = ByteBuffer.allocate(buf);
		byte[] bs = new byte[buff];
		FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
		fileChannel.read(byteBuffer);
		byteBuffer.position();
		byteBuffer.rewind();
		byteBuffer.get(bs);
		byteBuffer.clear();
		return bs;
	}

	/**
	 * 利用nio写入文件的内容
	 * @param fileName
	 * @param buf
	 * @param bs
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void writeFile(String fileName, int buf, byte[] bs) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			file.mkdirs();
		}
		FileChannel fileChannel = new RandomAccessFile(file, "rws").getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buf);
		byteBuffer = ByteBuffer.wrap(bs);
		fileChannel.write(byteBuffer);
	}
}
