package com.zc.common.core.encrypt;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	/**
	 * Used building output as Hex
	 */
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
			'B', 'C', 'D', 'E', 'F' };

	public static String getMD5ofStr(String s) {
		return StringUtils.lowerCase(md5(s));
	}

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			if (!StringUtils.isEmpty(str)) {
				messageDigest.update(str.getBytes("UTF-8"));
			}
		} catch (NoSuchAlgorithmException e) {
			//log.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			//log.error(e.getMessage());
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
		}
		return md5StrBuff.toString().toUpperCase();
	}

	public static String hash(String text, String key) {

		if (text == null) {
			throw new IllegalArgumentException("text can't be null");
		}
		if (key == null) {
			throw new IllegalArgumentException("key can't be null");
		}

		String s = md5(key);
		byte[] textData = text.getBytes();
		int len = textData.length;
		int n = (len + 15) / 16;
		byte[] tempData = new byte[n * 16];
		for (int i = len; i < n * 16; i++) {
			tempData[i] = 0;
		}
		System.arraycopy(textData, 0, tempData, 0, len);
		textData = tempData;
		String[] c = new String[n];
		for (int i = 0; i < n; i++) {
			c[i] = new String(textData, 16 * i, 16);
		}
		// end c
		String[] b = new String[n];
		String hash;

		String temp = s;
		String target = "";
		for (int i = 0; i < n; i++) {
			b[i] = md5(temp + c[i]);
			temp = b[i];
			target += b[i];
		}

		// 3.hash=MD5(b(1)+b(2)+...+b(n))
		hash = md5(target);
		return hash;
	}

	/**
	 * Converts an array of bytes into an array of characters representing the
	 * hexidecimal values of each byte in order. The returned array will be
	 * double the length of the passed array, as it takes two characters to
	 * represent any given byte.
	 *
	 * @param data a byte[] to convert to Hex characters
	 * @return A char[] containing hexidecimal characters
	 */
	private static char[] encodeHex(byte[] data) {

		int l = data.length;

		char[] out = new char[l << 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return out;
	}

	private static MessageDigest getMD5Digest() {
		try {
			MessageDigest md5MessageDigest = MessageDigest.getInstance("MD5");
			md5MessageDigest.reset();
			return md5MessageDigest;
		} catch (NoSuchAlgorithmException nsaex) {
			throw new RuntimeException("Could not access MD5 algorithm, fatal error");
		}
	}

	private static String md5(String content) {
		byte[] data = getMD5Digest().digest(content.getBytes());
		char[] chars = encodeHex(data);
		return new String(chars);
	}
}
