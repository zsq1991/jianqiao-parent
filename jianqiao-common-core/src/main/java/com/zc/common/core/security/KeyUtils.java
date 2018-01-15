package com.zc.common.core.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.io.IOUtils;

/**
 * 关于密钥解析的方法
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-24 上午10:59:42
 * 
 */
public class KeyUtils {
	/**
	 * 解析.p12文件即PKCS12类型的证书获取私钥
	 * 
	 * @param keypassword私钥的密码
	 * @param alias 私钥的别名
	 * @param inputStream 密钥输入流
	 * @return
	 */
	public static PrivateKey getPrivateKey(final String keypassword, final String alias,
			final InputStream inputStream) {
		try {
			KeyStore keyStore = KeyStore.getInstance(LicenceType.PKCS12.getName());
			char[] newpass = keypassword.toCharArray();
			keyStore.load(inputStream, newpass);
			return (PrivateKey) keyStore.getKey(alias, newpass);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return null;
	}

	/**
	 * 对公钥或者私钥加密
	 * 
	 * @param signRSA
	 * @param key
	 * @return
	 */
	public static byte[] encode(byte[] signRSA, Key key) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(signRSA);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对公钥或者私钥解密
	 * 
	 * @param signRSA
	 * @param key
	 * @return
	 */
	public static byte[] decode(byte[] signRSA, Key key) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(signRSA);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过.cer的文件解析公钥
	 * 
	 * @param inputStream
	 * @return
	 */
	public static PublicKey getPublicKey(final InputStream inputStream) {
		try {
			CertificateFactory certificateFactory = CertificateFactory
					.getInstance(LicenceType.X_509.getName());
			Certificate certificate = certificateFactory.generateCertificate(inputStream);
			X509Certificate t = (X509Certificate) certificate;
			// 公钥
			return t.getPublicKey();

		} catch (CertificateException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return null;
	}

}
