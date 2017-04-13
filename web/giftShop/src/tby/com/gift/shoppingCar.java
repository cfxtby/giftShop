package tby.com.gift;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tby.com.Oauth.access_token;
import tby.com.Oauth.verify;
import tby.com.sql.sqlOfGift;

public class shoppingCar extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		OutputStream out=resp.getOutputStream();
		access_token at=myOauth(req);
		String s="ERROR";
		if(at!=null){
			s=sqlOfGift.getGiftsOfcars(at.getUsername());
		}
		out.write(s.getBytes());
		out.flush();
		out.close();return;
	}
	
	private access_token myOauth(HttpServletRequest req) {
		String acc=req.getParameter("access_token");
		String userid=req.getParameter("userid");
		access_token at=verify.verifyAccessToken(acc,userid);
		return at;
	}
	
}
