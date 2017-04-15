package tby.com.gift;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tby.com.encode.encodeOfRSA;
import tby.com.sql.sqlCreat;
import tby.com.sql.sqlOfGift;

public class welcome extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		encodeOfRSA.setPubKeyFile(getServletContext().getRealPath(encodeOfRSA.getPubKeyFile()));
		encodeOfRSA.setPriKeyFile(getServletContext().getRealPath(encodeOfRSA.getPriKeyFile()));
		sqlCreat.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String s=sqlOfGift.getWelGift();
		resp.setCharacterEncoding("UTF-8");
		System.out.println(s);
		resp.getOutputStream().write(s.getBytes("utf-8"));
		resp.getOutputStream().flush();
		//super.doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		doGet(req, resp);
	}
	
}
