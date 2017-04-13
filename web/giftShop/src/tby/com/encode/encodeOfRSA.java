package tby.com.encode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class encodeOfRSA {
	private static byte[] pub_key;//="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSUmOXyQmYYSnZacp0btvAZCOvCNPtzixAp7eJmzmAG4mgy/VgrY/s1BDLh9qTNHIRWXepUtwMrf1kYul/A45qE/2oxIbeeq4238YDWQ7ModOVXR9ytEHsT0jpCFvoYfYXYZnnoWRrLIBylQeXzqxbLDxxBxGCs4AjoRKh5S7nNQIDAQAB".getBytes();
	private static byte[] pri_key;//="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANJSY5fJCZhhKdlpynRu28BkI68I0+3OLECnt4mbOYAbiaDL9WCtj+zUEMuH2pM0chFZd6lS3Ayt/WRi6X8DjmoT/ajEht56rjbfxgNZDsyh05VdH3K0QexPSOkIW+hh9hdhmeehZGssgHKVB5fOrFssPHEHEYKzgCOhEqHlLuc1AgMBAAECgYEAqTB9zWx7u4juEWd45ZEIVgw4aGXBllt0Xc6NZrTn3JZKcH+iNNNqJCm0GQaAXkqiODKwgBWXzttoK4kmLHa/6D7rXouWN8PGYXj7DHUNzyOe3IgmzYanowp/A8gu99mJQJzyhZGQ+Uo9dZXAgUDin6HAVLaxF3yWD8/yTKWN4UECQQD8Q72r7qdAfzdLMMSQl50VxRmbdhQYbo3D9FmwUw6W1gy2jhJyPXMi0JZKdKaqhxMZIT3zy4jYqw8/0zF2xc5/AkEA1W+n24Ef3ucbPgyiOu+XGwW0DNpJ9F8D3ZkEKPBgjOMojM7oqlehRwgy52hU+HaL4Toq9ghL1SwxBQPxSWCYSwJAGQUO9tKAvCDh9w8rL7wZ1GLsG0Mm0xWD8f92NcrHE6a/NAv7QGFf3gAaJ+BR92/WMRPe9SMmu3ab2JS1vzX3OQJAdN70/T8RYo8N3cYxNzBmf4d59ee5wzQb+8WD/57QX5UraR8LS+s8Bpc4uHnqvTq8kZG2YI5eZ9YQ6XwlLVbVTQJAKOSXNT+XEPWaol1YdWZDvr2m/ChbX2uwz52s8577Tey96O4Z6S/YA7V6Fr7hZEzkNF+K0LNUd79EOB6m2eQq5w==".getBytes();//="afdsjlk".getBytes();

	// 数字签名，密钥算法
	private static final String RSA_KEY_ALGORITHM = "RSA";
	private static final String keyFile="keys/";
	private static final String priKeyFile=keyFile+"pri_key";
	private static final String pubKeyFile=keyFile+"pub_key";
	
	// 数字签名签名/验证算法
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	// RSA密钥长度，RSA算法的默认密钥长度是1024密钥长度必须是64的倍数，在512到65536位之间
	private static final int KEY_SIZE = 1024;
	public static void main1(String args[]) throws Exception{
		//RSAForSever r=new RSAForSever();
		encodeOfRSA.createKey();
	}
	private static void createKey() throws Exception {
		KeyPairGenerator keygen = KeyPairGenerator
				.getInstance(RSA_KEY_ALGORITHM);
		SecureRandom secrand = new SecureRandom();
		secrand.setSeed("initSeed".getBytes());// 初始化随机产生器
		keygen.initialize(KEY_SIZE, secrand); // 初始化密钥生成器
		KeyPair keys = keygen.genKeyPair();
		pub_key = keys.getPublic().getEncoded();
		////.println("公钥：" + Base64.encodeBase64String(pub_key));
		pri_key = keys.getPrivate().getEncoded();
		////.println("私钥：" + Base64.encodeBase64String(pri_key));
	    File file= new File(pubKeyFile);
		FileOutputStream fw = new FileOutputStream(file);
		////.println(pub_key.length);
		fw.write(pub_key);
		////.println(pri_key.length);
		//fw.write(pri_key);
		fw.close();
	    file= new File(priKeyFile);
		fw = new FileOutputStream(file);
		fw.write(pri_key);
		fw.close();
	}
	
	private static void initKey() throws Exception {
		int i=162,j=634;
		FileInputStream fi=new FileInputStream(new File(pubKeyFile));
		byte[] buf=new byte[i];
		byte[] buf1=new byte[j];
		fi.read(buf,0,i);
		fi.close();

		pub_key=buf;
		////.println("私钥：" + new String(buf));
		fi=new FileInputStream(new File(priKeyFile));
		fi.read(buf1,0,j);
		pri_key=buf1;
		fi.close();
		////.println("私钥：" + Base64.encodeBase64String(pri_key));
		////.println("私钥：" + new String(buf1));
	}
	/**
	 * 用公钥加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPubKey(byte[] data) throws Exception {
		if (pub_key == null || pri_key == null)
			initKey();
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
	public static String encryptByPubKey(String data) throws Exception {
		// 私匙加密
		byte[] enSign = encryptByPubKey(data.getBytes());
		return Base64.encodeBase64String(enSign);
	}
	/**
	 * 用私钥加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPriKey(byte[] data) throws Exception {
		if (pub_key == null || pri_key == null)
			initKey();
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	/**
	 * 用私钥加密
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPriKey(String data) throws Exception {		// 私匙加密
		byte[] enSign = encryptByPriKey(data.getBytes());
		return Base64.encodeBase64String(enSign);
	}
	/**
	 * 用公钥解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPubKey(byte[] data) throws Exception {
		if (pub_key == null || pri_key == null)
			initKey();
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	/**
	 * 用公钥解密
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPubKey(String data) throws Exception {
		// 公匙解密
		byte[] design = decryptByPubKey(Base64.decodeBase64(data));
		return new String(design);
	}
	/**
	 * 用私钥解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPriKey(byte[] data) throws Exception {
		if (pub_key == null || pri_key == null)
			initKey();
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	/**
	 * 用私钥解密
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPriKey(String data) throws Exception {
		// 公匙解密
		byte[] design = decryptByPriKey(Base64.decodeBase64(data));
		return new String(design);
	}
	
	
	
}

