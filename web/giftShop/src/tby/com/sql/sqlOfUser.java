package tby.com.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import tby.com.Oauth.access_token;

//用来查询和处理用户情况的静态对象
public class sqlOfUser {
	public static access_token checkoutUser(access_token at,String password ){
		//access_token at=new access_token();
		String userid=at.getUsername();
		String sql="select ID,password from user where ID= ?";
		String s[]={userid};
		ResultSet rs=sqlCreat.doSqlgetRs(sql, s);
		try {
			if(rs.next()){
				String newP=rs.getString("password");
				if(newP.compareTo(password)==0){
					at.getNewAccessToken();
					//sqlOfToken.insertAccessToken(at);
					return at;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static access_token InsertUser(access_token at,String password ){
		//access_token at=new access_token();
		String userid=at.getUsername();
		String sql="select ID,password from user where ID= ?";
		String s[]={userid};
		ResultSet rs=sqlCreat.doSqlgetRs(sql, s);
		try {
			if(!rs.next()){
				sql="insert into user (ID,password,name) value(?,?,?)";
				String s1[]={userid,password,null};
				sqlCreat.doSql(sql, s1);
				at.getNewAccessToken();
				return at;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
