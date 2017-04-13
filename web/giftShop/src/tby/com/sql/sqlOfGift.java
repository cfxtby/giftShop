package tby.com.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tby.com.Oauth.access_token;
import tby.com.Oauth.orderForm;


//查询礼品的数据库查询对象，可以主要使用静态方法
public final class sqlOfGift {
	/*
	 * get the gift list of welcome page 
	 * */
	public static String getWelGift(){
		String sql="SELECT * from welGift";
		ResultSet rs=sqlCreat.doSqlgetRs(sql, null);
		ResultSetMetaData metaData;
		try {
			metaData = rs.getMetaData();
			int columnCount= metaData.getColumnCount();  
			ArrayList<String> giftList=new ArrayList<String>();
			JSONObject jsonObj=new JSONObject();
			JSONArray array=new JSONArray();
			while(rs.next()){
			     for(int i = 1; i <= columnCount;i++)  
			      {  
			       String columnName = metaData.getColumnLabel(i);
			       //System.out.println(rs.getLong(columnName));
			       String value =rs.getObject(columnName)+"";  
			       jsonObj.put(columnName, value);  
			      }  
			      array.put(jsonObj);  
			      jsonObj=new JSONObject();
		      }
			return array.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	
	/*
	 * get the information of gifts
	 */
	public static String getGiftInfo(String id){
		String sql="select * from goodinfo where accountGood = ?";
		
		String[] get={id};
		ResultSet rs=sqlCreat.doSqlgetRs(sql, get);
		ResultSetMetaData metaData;
		try {
			metaData = rs.getMetaData();
			int columnCount= metaData.getColumnCount();  
			ArrayList<String> giftList=new ArrayList<String>();
			JSONObject jsonObj=new JSONObject();
			JSONArray array=new JSONArray();
			while(rs.next()){
			     for(int i = 1; i <= columnCount;i++)  
			      {  
			       String columnName = metaData.getColumnLabel(i);
			       //System.out.println(rs.getLong(columnName));
			       String value =rs.getObject(columnName)+"";  
			       jsonObj.put(columnName, value);  
			      }  
			      array.put(jsonObj);  
			      jsonObj=new JSONObject();
		      }
			return array.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	
	/*
	 * get the list of the shopping car
	 * 
	 */
	public static String getGiftsOfcars(String userid){
		String sql="select * from shopingcar where user = ?";
		
		String[] get={userid};
		ResultSet rs=sqlCreat.doSqlgetRs(sql, get);
		ResultSetMetaData metaData;
		try {
			metaData = rs.getMetaData();
			int columnCount= metaData.getColumnCount();  
			ArrayList<String> giftList=new ArrayList<String>();
			JSONObject jsonObj=new JSONObject();
			JSONArray array=new JSONArray();
			while(rs.next()){
				String sql1="select photoAddr1 from goodinfo where accountGood = ?";
				String[] o={rs.getString("accountGood")}; 
				ResultSet rs1=sqlCreat.doSqlgetRs(sql1, o);
				
				for(int i = 1; i <= columnCount;i++)  
			      {  
			       String columnName = metaData.getColumnLabel(i);
			       //System.out.println(rs.getLong(columnName));
			       
			       String value =rs.getObject(columnName)+"";  
			       jsonObj.put(columnName, value);  
			      }  
				  jsonObj.put("pic", rs1.getString("photoAddr1"));
			      array.put(jsonObj);  
			      jsonObj=new JSONObject();
		      }
			return array.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	/*
	 * get the answer of the query
	 * 
	 */
	public static String query(String query){
		String result;
		String sql="select * from goodinfo where name LIKE ?";
		String []q=query.split("\\s");
		String s=".*[";
		for (int i=0;i<q.length-1;i++){
			s=s+q[i]+"|";
		}
		s=s+q[q.length]+"].*";
		String []order={s};
		ResultSet rs=sqlCreat.doSqlgetRs(sql, order);
		
		try {
			JSONArray ja=new JSONArray();
			while(rs.next()){
				JSONObject js=new JSONObject();
				js.put("name",rs.getString("name"));
				js.put("goodid",rs.getString("accountGood"));
				js.put("numleft",rs.getInt("numLeft"));
				js.put("price",rs.getDouble("price"));
				js.put("dec",rs.getString("description"));
				JSONArray array=new JSONArray();
				int i;
				for(i=0;i<4;i++){
					String addr;
					if((addr=rs.getString("photoAddr"+(i+1)))!=null){
						array.put(addr);
					}
					else break;
				}
				js.put("picnum", i);
				js.put("photoAddr", array);
				ja.put(js);
			}
			return ja.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean insertOrder(orderForm of) throws SQLException{

		String sql="insert into orderform(userid,goodid,num,price) values(?,?,?,?)";
		String s[]=new String[5];
		s[0]=of.getUserid();
		s[1]=of.getGoodid();
		s[2]=of.getNum();
		s[3]=of.getPrice();
		sqlCreat.doSql(sql, s);
		return true;
	}

}
