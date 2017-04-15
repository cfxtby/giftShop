package tby.com.gift;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tby.com.Oauth.access_token;
import tby.com.Oauth.decodeCode;
import tby.com.Oauth.orderForm;
import tby.com.Oauth.verify;
import tby.com.sql.sqlOfGift;

public class dealOrder extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		String result;
		decodeCode dc=new decodeCode();
		
		if(!dc.init(req)){
			result="ERROR";
		}
		else{
			orderForm of=new orderForm();
			of.setGoodid(dc.getParameter("goodid"));
			of.setUserid(dc.getParameter("userid"));
			of.setNum(dc.getParameter("num"));
			of.setPrice(dc.getParameter("price"));
			if(deal(of)){
				result="OK";
			}
			else{
				result="ERROR";
			}
			
		}
		resp.getOutputStream().write(result.getBytes());
		resp.getOutputStream().close();return;
	}
	
	private boolean deal(orderForm of){
		try {
			boolean flag=sqlOfGift.insertOrder(of);
			return flag;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	private access_token myOauth(HttpServletRequest req) {
		String acc=req.getParameter("access_token");
		String userid=req.getParameter("userid");
		System.out.println(acc+userid);
		access_token at=verify.verifyAccessToken(acc,userid);
		return at;
	}
}
