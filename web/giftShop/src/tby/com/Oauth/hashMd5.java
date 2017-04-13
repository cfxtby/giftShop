package tby.com.Oauth;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;




public class hashMd5 {
	
	public static void main(String args[]){
		System.out.println(getMD5("helloworld"));
	}
	
	public static String getMD5(String data){
		String result=null;
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(data.getBytes());  
			byte[] b=md.digest();   
			BASE64Encoder encoder = new BASE64Encoder(); 
			result=encoder.encode(b);
			BASE64Decoder de=new BASE64Decoder();
			byte b1[]=de.decodeBuffer(result);
			System.out.println(Arrays.equals(b,b1));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
}
