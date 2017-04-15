package tby.com.gift;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tby.com.sql.sqlCreat;
import tby.com.sql.sqlOfGift;

public class infoOfGood extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		String i=req.getParameter("accountGood");
		String result;
		
		if (i.matches("\\d"))
			result=sqlOfGift.getGiftInfo(i);
		else 
			result="ERROR";
		System.out.println(result);
		System.out.println(i);
		resp.getOutputStream().write(result.getBytes("UTF-8"));
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
