package com.example.tby.myapplication.utils;


import java.math.BigInteger;  
import java.security.MessageDigest;  
import java.security.SecureRandom;  

import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
import javax.crypto.spec.SecretKeySpec;  



/** 
* 编码工具类 
* 1.将byte[]转为各种进制的字符串 
* 2.base 64 encode 
* 3.base 64 decode 
* 4.获取byte[]的md5值 
* 5.获取字符串md5值 
* 6.结合base64实现md5加密 
* 7.AES加密 
* 8.AES加密为base 64 code 
* 9.AES解密 
* 10.将base 64 code AES解密 
* @author uikoo9 
* @version 0.0.7.20140601 
*/  
public class AES {  
    
  public static void main(String[] args) throws Exception {  
      String content = "我爱你";  
      System.out.println("加密前：" + content);  

      String key = "123456";  
      System.out.println("加密密钥和解密密钥：" + key);  
        
      String encrypt = Base64Utils.encode((aesEncryptToBytes(content, key)));  
      System.out.println("加密后：" + encrypt);  
        
      String decrypt = new String(aesDecryptByBytes(Base64Utils.decode(encrypt), key));  
      System.out.println("解密后：" + decrypt);  
  }  
    
  /** 
   * 将byte[]转为各种进制的字符串 
   * @param bytes byte[] 
   * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
   * @return 转换后的字符串 
   */  
  public static String binary(byte[] bytes, int radix){  
      return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
  }  
    


    
  /** 
   * 获取byte[]的md5值 
   * @param bytes byte[] 
   * @return md5 
   * @throws Exception 
   */  
  public static byte[] md5(byte[] bytes) throws Exception {  
      MessageDigest md = MessageDigest.getInstance("MD5");  
      md.update(bytes);  
        
      return md.digest();  
  }  
    


    
  /** 
   * AES加密 
   * @param content 待加密的内容 
   * @param encryptKey 加密密钥 
   * @return 加密后的byte[] 
   * @throws Exception 
   */  
  public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
      KeyGenerator kgen = KeyGenerator.getInstance("AES");  
      kgen.init(128, new SecureRandom(encryptKey.getBytes()));  

      Cipher cipher = Cipher.getInstance("AES");  
      cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        
      return cipher.doFinal(content.getBytes("utf-8"));  
  }  
    

  /** 
   * AES解密 
   * @param encryptBytes 待解密的byte[] 
   * @param decryptKey 解密密钥 
   * @return 解密后的String 
   * @throws Exception 
   */  
  public static byte[] aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
      KeyGenerator kgen = KeyGenerator.getInstance("AES");  
      kgen.init(128, new SecureRandom(decryptKey.getBytes()));  
        
      Cipher cipher = Cipher.getInstance("AES");  
      cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
      byte[] decryptBytes = cipher.doFinal(encryptBytes);  
        
      return decryptBytes;  
  }  
    
  public static String decrypt(String input,String key){
	  try {
		String decrypt = new String(aesDecryptByBytes(Base64Utils.decode(input), key));
		return decrypt;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	  return null;
  }
    
  public static String encrypt(String input,String key){
	  try {
		  String encrypt = Base64Utils.encode((aesEncryptToBytes(input, key)));  
		return encrypt;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	  return null;
  }
}  