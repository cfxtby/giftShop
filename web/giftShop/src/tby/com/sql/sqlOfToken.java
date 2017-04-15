package tby.com.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import tby.com.Oauth.access_token;

//用来处理查询access_token的静态对象
public class sqlOfToken {
	public static access_token getAccessToken(String userid){
		access_token at=new access_token();
		String sql="select * from accesstoken where userID = ?";
		String s[]={userid};
		ResultSet rs=sqlCreat.doSqlgetRs(sql,s);
		try {
			if(rs.next()){
			at.setAccess_token(rs.getString("access_token"));
			at.setRefresh_token(rs.getString("refresh_token"));
			at.setUsername(userid);
			at.setClient_id(rs.getString("client_id"));
			at.setModifiedtime(rs.getLong("refresh_date"));
			at.setToken_type("password");
			at.setAESkey(rs.getString("AESkey"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return at;
	}
	public static boolean insertAccessToken(access_token at) throws SQLException{
		String sql1="select * from accesstoken where userID = ?";
		String order[]={at.getUsername()};
		ResultSet rs1=sqlCreat.doSqlgetRs(sql1, order);
		if(rs1.next()){
			return updateAccessToken(at);
		}
		
		String sql="insert into accesstoken values(?,?,?,?,?,?)";
		String s[]=new String[6];
		s[0]=at.getUsername();
		s[1]=at.getAccess_token();
		s[2]=at.getRefresh_token();
		s[3]=at.getClient_id();
		s[4]=at.getModifiedtime()+"";
		s[5]=at.getAESkey();
		sqlCreat.doSql(sql, s);
		return true;
	}
	
	public static boolean updateAccessToken(access_token at){
		
		String sql="UPDATE accesstoken SET access_token=?,refresh_token=?,"
				+ "client_id=?,refresh_date=? WHERE userID=?";
		String s[]=new String[5];
		s[4]=at.getUsername();
		s[0]=at.getAccess_token();
		s[1]=at.getRefresh_token();
		s[2]=at.getClient_id();
		s[3]=at.getModifiedtime()+"";
		sqlCreat.doSql(sql, s);
		return true;
	}
	
	public static String getKey(String access_token){
		String sql="select AESkey from accesstoken where access_token=? ";
		String s[]={access_token};
		ResultSet rs=sqlCreat.doSqlgetRs(sql, s);
		try {
			if(rs.next()){
				return rs.getString("AESkey");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
