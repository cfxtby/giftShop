package tby.com.gift;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tby.com.sql.sqlOfGift;

public class query extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		OutputStream out=resp.getOutputStream();
		String query=req.getParameter("query");
		if (!query.matches("\\w\\s")){
			out.write("ERROR".getBytes());
			out.flush();
			out.close();
			return ;
		}
		String result=sqlOfGift.query(query);
		out.write(result.getBytes());
		out.flush();out.close();
		return ;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		
	
	}
	
}
