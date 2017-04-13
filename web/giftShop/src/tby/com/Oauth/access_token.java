package tby.com.Oauth;
//包含着access_token的所有应用对象

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import tby.com.sql.sqlOfToken;


public class access_token {
	
	private String access_token;
	private String token_type;
	private String expires;
	private String refresh_token;
	private String username;
	private String client_id;
	private long createdtime;
	private long modifiedtime;
			
	public long getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(long createdtime) {
		this.createdtime = createdtime;
	}
	public long getModifiedtime() {
		return modifiedtime;
	}
	public void setModifiedtime(long modifiedtime) {
		this.modifiedtime = modifiedtime;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getExpires() {
		return expires;
	}
	public void setExpires(String expires) {
		this.expires = expires;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	
	public void getNewAccessToken() {
		expires=15*24*3600*1000+"";
		getNewRefreshToken();
		createdtime=new Date().getTime();
		modifiedtime=createdtime+15*24*3600*1000;//十五天后过期
		access_token=hashMd5.getMD5(token_type+username+client_id+refresh_token);
		//sqlOfToken.updateAccessToken(this);
		}
	
	public void getNewRefreshToken() {
		String random = UUID.randomUUID().toString();//获得随机数作为access_token
		refresh_token=hashMd5.getMD5(random+token_type+username+client_id);
		}

	
	
	public String toString(){
		JSONObject js=new JSONObject();
		try {
			js.put("username", username);
			js.put("token_type", token_type);
			js.put("access_token",access_token);
			js.put("refresh_token", refresh_token);
			js.put("modifiedtime",modifiedtime);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return js.toString();
	}
	
}
