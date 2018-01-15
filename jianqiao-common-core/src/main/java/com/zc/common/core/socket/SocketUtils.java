package com.zc.common.core.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.zc.common.core.io.MyFileUtils;
import com.zc.common.core.io.MyIOUtils;


/**
 * socket工具类
 * 
 * @author zhangkaoqin
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-6-25 下午4:37:16
 * 
 */
public class SocketUtils {
	private SocketUtils() {
	}

	/**
	 * 获取服务器端的socket对象可以写在线程中对客服端发来的socket进行监听
	 * 
	 * @param aport
	 * @return
	 * @throws IOException
	 */
	public static ServerSocket serverTCPSocket(final int aport) throws IOException {
		ServerSocket serverSocket = new ServerSocket(aport);
		return serverSocket;
	}

	/**
	 * 客户端发送socket的socket方法获取
	 * 
	 * @param dstName
	 * @param dstPort
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static Socket clientTCPSocket(final String dstName, final int dstPort)
			throws UnknownHostException, IOException {
		return new Socket(dstName, dstPort);
	}

	/**
	 * 发送socket信息
	 * 
	 * @param socket
	 * @param inputStream
	 * @throws IOException
	 */
	public static void sendByTcp(final Socket socket, final InputStream inputStream)
			throws IOException {
		MyFileUtils.write(socket.getOutputStream(), inputStream);
	}

	/**
	 * 发送socket信息
	 * 
	 * @param socket
	 * @param content
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendByTcp(final Socket socket, final String content, final String ecoding)
			throws UnsupportedEncodingException, IOException {
		sendByTcp(socket, MyIOUtils.stringToStream(content, ecoding));
	}

	/**
	 * 接收socket tcp消息
	 * 
	 * @param inputStream
	 * @param encoding
	 * @return
	 */
	public static List<String> receiveTcp(final InputStream inputStream,final String encoding) {
		return MyIOUtils.readLines(inputStream, encoding);
	}

	/**
	 * 接收端或者服务器端接收的UDP包，放在线程当中运行
	 * 
	 * @param port
	 * @param buf
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static DatagramPacket serviceUdp(final int port, final int buf) throws IOException {
		DatagramSocket datagramSocket = new DatagramSocket(port);
		byte[] buff = new byte[buf];
		DatagramPacket datagramPacket = new DatagramPacket(buff, 0, buff.length);
		datagramSocket.receive(datagramPacket);
		return datagramPacket;
	}

	/**
	 * 获取数据包中的数据
	 * 
	 * @param datagramPacket
	 * @return
	 */
	public static byte[] receiveUdp(final DatagramPacket datagramPacket) {
		return datagramPacket.getData();
	}

	/**
	 * 客户端发送UDP的包
	 * 
	 * @param port
	 * @param content
	 * @param encoding
	 * @param host
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void clientUdp(final int port, final String content, final String encoding,
			final String host) throws IOException {
		DatagramSocket datagramSocket = new DatagramSocket(port);
		InetAddress inetAddress = InetAddress.getByName(host);
		byte[] bs = content.getBytes(encoding);
		DatagramPacket datagramPacket = new DatagramPacket(bs, bs.length, inetAddress, port);
		datagramSocket.send(datagramPacket);
	}
}
