package tby.com.Oauth;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.*;

import sun.net.www.URLConnection;

public class sms {

 
	public static void main(String args[]){
		String url="https://webapi.sms.mob.com/sms/verify";
		System.out.println(sendPost(url, ""));
	}

	
	  public static String sendPost(String url, String param) {  
	        PrintWriter out = null;  
	        BufferedReader in = null;  
	        String result = "";  
	        	
	            // 创建URL对象
	            URL myURL;
				try {
					myURL = new URL(url);
					 // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
		            HttpsURLConnection httpsConn = (HttpsURLConnection) myURL
		                    .openConnection();
		            // 取得该连接的输入流，以读取响应内容
		            httpsConn.setDoOutput(true);
		            httpsConn.setDoInput(true);
		            OutputStream o=httpsConn.getOutputStream();
		            InputStream in1 = httpsConn.getInputStream();
		            String ars="appkey="+"1c98c87073338&"+
		            		"phone="+"1825027549&"+"zone="+"+86&"+
		            		"code+"+"验证码";
		            o.write(ars.getBytes());
		            o.flush();
		            // 读取服务器的响应内容并显示
		            byte b[]=new byte[512];
		            int n=in1.read(b);
		            while (n>0) {
		                result+=new String(b,0,n);
		            }
		            return result;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           
	        	return null;

	  }
}
