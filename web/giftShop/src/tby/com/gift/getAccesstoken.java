package tby.com.gift;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tby.com.Oauth.access_token;
import tby.com.Oauth.verify;
import tby.com.sql.sqlOfToken;
import tby.com.sql.sqlOfUser;

public class getAccesstoken extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		
		OutputStream out=resp.getOutputStream();
		String grant_type = req.getParameter("grant_type");	//查看当前的授权方式
		if(grant_type.compareTo("password")==0){
			//verify.
			access_token at=new access_token();
			at.setClient_id(req.getParameter("client_id"));
			at.setUsername(req.getParameter("userid"));
			at.setToken_type("password");
			at=sqlOfUser.checkoutUser(at, req.getParameter("password"));
			try {
				sqlOfToken.insertAccessToken(at);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(at!=null && at.toString()!=null){
			out.write(at.toString().getBytes());
			out.flush();
			out.close();
			}
			else{
				out.write("ERROR".getBytes());
				out.flush();
				out.close();
			}
			return;
		}
		if(grant_type.compareTo("refresh_access")==0){
			String RefreshToken=req.getParameter("refresh_token");
			String userid=req.getParameter("userid");
			access_token at=verify.verifyRefreshToken(RefreshToken, userid);
			if(at!=null){
				at.getAccess_token();
				sqlOfToken.updateAccessToken(at);
				out.write(at.toString().getBytes());
				out.flush();
				out.close();
				return;
			}
			out.write("ERROR".getBytes());
			out.flush();
			out.close();
			
		}
		
		out.write("ERROR".getBytes());
		out.flush();
		out.close();
		return ;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);

		
	}
	
}
