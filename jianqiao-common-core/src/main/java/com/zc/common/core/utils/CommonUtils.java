package com.zc.common.core.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * @description 加密工具类
 * @author system
 * @date 2018-01-23 13:53
 * @version 1.0.0
 */
public class CommonUtils {
    private final static Base64 BASE64 = new Base64();

    /**
     * 3des解码
     *
     * @param value 待解密字符串
     * @param key   原始密钥字符串
     * @return 解密后字符串
     * @throws Exception
     */
    public static String decrypt3DES(String value, String key) throws Exception {
        byte[] b = decryptMode(getKeyBytes(key), (byte[]) BASE64.decode(value));
        return new String(b);
    }

    /**
     * 3des加密
     *
     * @param value 待加密字符串
     * @param key   原始密钥字符串
     * @return 加密后字符串
     * @throws Exception
     */
    public static String encrypt3DES(String value, String key) throws Exception {
        String str = byte2Base64(encryptMode(getKeyBytes(key), value.getBytes()));
        return str;
    }

    /**
     * 计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位
     *
     * @param strKey 原始密钥
     * @return 加密后密钥
     * @throws Exception
     */
    private static byte[] getKeyBytes(String strKey) throws Exception {
        if (null == strKey || strKey.length() < 1) {
            throw new Exception("key is null or empty!");
        }
        //长度
        int length = 24;
        MessageDigest alg = MessageDigest.getInstance("MD5");
        alg.update(strKey.getBytes());
        byte[] bKey = alg.digest();
        int start = bKey.length;
        byte[] bKey24 = new byte[length];
        for (int i = 0; i < start; i++) {
            bKey24[i] = bKey[i];
        }
        //为了与.net16位key兼容
        for (int i = start; i < length; i++) {
            bKey24[i] = bKey[i - start];
        }
        return bKey24;
    }

    /**
     * 定义 加密算法,可用 DES,DESede,Blowfish
     */
    private static final String ALGORITHM = "DESede";

    /**
     * 加密模式
     *
     * @param keybyte e为加密密钥，长度为24字节
     * @param src     为被加密的数据缓冲区（源）
     * @return
     */
    private static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 解密模式
     *
     * @param keybyte 为加密密钥，长度为24字节
     * @param src     为加密后的缓冲区
     * @return
     */
    private static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 转换成base64编码
     *
     * @param b
     * @return
     */
    public static String byte2Base64(byte[] b) {
        return new String(BASE64.encode(b));
    }

    /**
     * 转换成十六进制字符串
     *
     * @param
     * @return
     */
    @SuppressWarnings("unused")
    /*	private static String byte2hex(byte[] b) {
	      String hs = "";

	        String stmp = "";  
	        for (int n = 0; n < b.length; n++) {  
	            stmp = (Integer.toHexString(b[n] & 0XFF));
	            if (stmp.length() == 1) {

                   hs = hs + "0" + stmp;
                } else {

                    hs = hs + stmp;
                }
	            if (n < b.length - 1) {
                    hs = hs + ":";
                }
	        }  
	        return hs.toUpperCase();
	    }  */


    /**
     *
     */
    public final static String getMD5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
