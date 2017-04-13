package tby.com.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


//数据库连接初始化
public final class sqlCreat {
	private static Connection con;
	
    //准备声明sql语句
    private static java.sql.PreparedStatement pstmt=null;
     
    //结果集
    private static ResultSet rs=null;
	
	public static void init() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/giftsql";
	    String username = "root";
	    String password = "123";
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        con = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        con=null;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        con=null;
	    }
	}
	
	
    /*
     * 执行sql语句
     * */
    public static void doSql(String sql,Object[] object) {
        // TODO Auto-generated method stub
        //判断sql语句是否存在
        if(sql!=null){
            //判断object数组是否存在
            if(object==null){
                //如果不存在，创建一个，防止出现空指针异常
                object=new Object[0];
            }
 
            try {
                 
                //声明一条准备的sql语句
                pstmt=con.prepareStatement(sql);
                 
                //为Object对象一一赋值
                for(int i=0;i<object.length;i++){
                     
                    pstmt.setObject(i+1, object[i]);
                     
                }
                //执行声明的sql语句
                pstmt.execute();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
             
        }else{
             
            System.out.println("sql语句并不存在！");
             
        }
     
    }
	
    
    public static ResultSet doSqlgetRs(String sql,Object[] object){
    	//ResultSet rs;
    	try {
    	    doSql(sql, object);
			rs=pstmt.getResultSet();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    
    
    
}
