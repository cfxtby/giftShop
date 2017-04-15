package com.example.tby.myapplication.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//AES加解密，使用静态方法
public class encodeOfAES {
    /** 
     * 对输入流进行AES加密
     * @return 
     */  
	public static int encode(InputStream in,OutputStream out,String password){
        Cipher cipher = initAESCipher(password,Cipher.ENCRYPT_MODE);  
        //以加密流写入文件  
        CipherInputStream cipherInputStream = new CipherInputStream(in, cipher);  
        byte[] cache = new byte[1024];  
        int nRead = 0;  
        
        try {
        	while ((nRead = cipherInputStream.read(cache)) != -1) {  
			out.write(cache, 0, nRead);
            out.flush();  
            }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}  
		return 1;
	}
	
    /** 
     * 对输入流进行AES解密
     * @return 
     */  
	public static int decode(InputStream in,OutputStream out,String password){
        Cipher cipher = initAESCipher(password,Cipher.DECRYPT_MODE);  
        //以加密流写入文件  
        CipherOutputStream cipherOutputStream = new CipherOutputStream(out, cipher);  
        byte[] cache = new byte[1024];  
        int nRead = 0;  
        
        try {
        	while ((nRead = in.read(cache)) != -1) {  
        		cipherOutputStream.write(cache, 0, nRead);
        		cipherOutputStream.flush();  
            }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}  
		return 1;
	}
	
	
	
	/** 
     * 初始化 AES Cipher 
     * @param sKey 
     * @param cipherMode 
     * @return 
     */  
    private static Cipher initAESCipher(String sKey, int cipherMode) {  
        //创建Key gen  
        KeyGenerator keyGenerator = null;  
        Cipher cipher = null;  
        try {  
            keyGenerator = KeyGenerator.getInstance("AES");  
            keyGenerator.init(128, new SecureRandom(sKey.getBytes()));  
            SecretKey secretKey = keyGenerator.generateKey();  
            byte[] codeFormat = secretKey.getEncoded();  
            SecretKeySpec key = new SecretKeySpec(codeFormat, "AES");  
            cipher = Cipher.getInstance("AES");  
            //初始化  
            cipher.init(cipherMode, key);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
        }  
        return cipher;  
    }  	
	
}
