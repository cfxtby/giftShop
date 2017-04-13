package tby.com.gift;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.jdbc.odbc.OdbcDef;
import tby.com.Oauth.access_token;
import tby.com.Oauth.orderForm;
import tby.com.Oauth.verify;

public class dealOrder extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		access_token at=myOauth(req);
		orderForm of=new orderForm();
		of.setGoodid(req.getParameter("goodid"));
		of.setUserid(req.getParameter("userid"));
		of.setNum(req.getParameter("num"));
		of.setPrice(req.getParameter("price"));
		
	}
	
	private access_token myOauth(HttpServletRequest req) {
		String acc=req.getParameter("access_token");
		String userid=req.getParameter("userid");
		access_token at=verify.verifyAccessToken(acc,userid);
		return at;
	}
}
