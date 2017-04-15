package tby.com.Oauth;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import tby.com.encode.AES;
import tby.com.encode.AesUtil;
import tby.com.sql.sqlOfToken;

public class decodeCode {
	JSONObject json;
	String acc;
	public boolean init(HttpServletRequest req){
		try {
			acc=req.getParameter("access_token");
			String key=sqlOfToken.getKey(acc);
			if(key==null)return false;
			String data;
			data=AesUtil.aesDecrypt(req.getParameter("data"), key);
			if(data==null)return false;
			json=new JSONObject(data);
			System.out.println(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public String getParameter(String key){
		try {
			if("access_token".compareTo(key)==0)
				return acc;
			return json.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
