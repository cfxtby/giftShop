package com.example.tby.myapplication.utils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public class encodeOfRSA {
	private static byte[] pub_key;
	// 数字签名，密钥算法
	private static final String RSA_KEY_ALGORITHM = "RSA";

	// 数字签名签名/验证算法
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	// RSA密钥长度，RSA算法的默认密钥长度是1024密钥长度必须是64的倍数，在512到65536位之间
	private static final int KEY_SIZE = 1024;

	private static void initKey(String pub) throws Exception {
		pub_key=Base64Utils.decode(pub);
		System.out.println(pub_key.length);
	}
	/**
	 * 用公钥加密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPubKey(byte[] data,String pub) throws Exception {
		if (pub_key == null)
			initKey(pub);
		// 取得公钥

		////.println(new String(pub_key));
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	/**
	 * 用公钥加密
	 *
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPubKey(String data,String pub) throws Exception {
		// 公钥加密
		byte[] enSign = encryptByPubKey(data.getBytes(),pub);
		return Base64Utils.encode(enSign);
	}


	public static byte[] decryptByPubKey(byte[] data,String pub) throws Exception {
		if (pub_key == null)
			initKey(pub);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
		//Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	/**
	 * 用公钥解密
	 *
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPubKey(String data,String pub) throws Exception {
		// 公匙解密
		byte[] design = decryptByPubKey(Base64Utils.decode(data),pub);
		return new String(design);
	}
}

