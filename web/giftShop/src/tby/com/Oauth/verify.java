package tby.com.Oauth;

import tby.com.sql.sqlOfToken;

public class verify {
	public static access_token verifyRefreshToken(String RefreshToken,String userid){
		boolean flag;
		access_token at=sqlOfToken.getAccessToken(userid);
		if(RefreshToken.compareTo(at.getRefresh_token())==0){
			return at;
		}
		else{
			return null;
		}
		//return flag;
	}
	
	public static access_token verifyAccessToken(String RefreshToken,String userid){
		boolean flag;
		access_token at=sqlOfToken.getAccessToken(userid);
		if (at==null)return null;
		if(RefreshToken.compareTo(at.getAccess_token())==0&&at.getModifiedtime()>=System.currentTimeMillis()){
			at.setAccess_token(null);
			return at;
		}
		else{
			return null;
		}
		//return flag;
	}
	

}
