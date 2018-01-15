package com.zc.common.core.net.ip;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-4-10 下午03:29:02
 * 
 */
public class IpUtils {
	private IpUtils() {
	}
	/**
	 * 获取本地IP
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static List<String> getLocalIp() throws SocketException {
		List<String> list = new ArrayList<String>();
		Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
		while (networkInterface.hasMoreElements()) {
			NetworkInterface interfaces = networkInterface.nextElement();
			Enumeration<InetAddress> inetAddress = interfaces.getInetAddresses();
			while (inetAddress.hasMoreElements()) {
				InetAddress address = inetAddress.nextElement();
				String str = null;
				if (address instanceof Inet4Address) {
					str = "ipv4";
				} else if (address instanceof Inet6Address) {
					str = "ipv6";
				} else {
					str = "{?}";
				}
				list.add(str + "," + address.getHostAddress());
			}
		}
		return list;
	}

	/**
	 * 获取远程发送信息的ip
	 * 
	 * @param host
	 * @return
	 * @throws UnknownHostException
	 */
	public static List<String> getRemoteIp(String host) throws UnknownHostException {
		List<String> list = new ArrayList<String>();
		InetAddress[] addresses = InetAddress.getAllByName(host);
		for (InetAddress inetAddress : addresses) {
			list.add(inetAddress.getHostAddress());
		}
		return list;
	}

	/**
	 * 获取真实IP: 先取
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/**
	 * IP转成数字类型
	 * 
	 * @param strIP
	 * @return
	 */
	public static long ipToLong(String strIP) {
		long[] ip = new long[4];
		int position1 = strIP.indexOf(".");
		int position2 = strIP.indexOf(".", position1 + 1);
		int position3 = strIP.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIP.substring(0, position1));
		ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIP.substring(position3 + 1));
		// ip1*256*256*256+ip2*256*256+ip3*256+ip4
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * 是否是本地IP
	 * 
	 * @param strIp
	 * @return
	 */
	public static boolean isLocal(String strIp) {
		if ("127.0.0.1".equals(strIp))
			return true;
		long l = ipToLong(strIp);
		if (l >= 3232235520L)
			return l <= 3232301055L;
		return (l >= 167772160L) && (l <= 184549375L);
	}

}
