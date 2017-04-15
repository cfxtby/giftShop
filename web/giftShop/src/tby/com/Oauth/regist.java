package tby.com.Oauth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tby.com.encode.encodeOfRSA;
import tby.com.sql.sqlOfUser;

public class regist extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		OutputStream out=resp.getOutputStream();
		String userid=req.getParameter("userid");
		String passwd=req.getParameter("password");
		try {
			System.out.println(passwd);
			passwd=encodeOfRSA.decryptByPriKey(passwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write("ERROR".getBytes("utf-8"));
			out.flush();
			out.close();
		}
		String valify=req.getParameter("valicode");
		System.out.println(valify);
		String client_id=req.getParameter("client_id");
		access_token at=new access_token();
		at.setClient_id(client_id);
		at.setUsername(userid);
		at.setToken_type("password");
		at=sqlOfUser.InsertUser(at, passwd);
		String result;
		if(at==null){
			result="ERROR";
		}
		else{
			result=at.toString();
		}
		out.write(result.getBytes("utf-8"));
		out.flush();
		out.close();
		return;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		InputStream in=req.getInputStream();
		OutputStream out=resp.getOutputStream();
		String userid=req.getParameter("userid");
		String passwd=req.getParameter("passwd");
		try {
			passwd=encodeOfRSA.decryptByPriKey(passwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write("ERROR".getBytes("utf-8"));
			out.flush();
			out.close();
		}
		String valify=req.getParameter("valicode");
		String client_id=req.getParameter("client_id");
		access_token at=new access_token();
		at.setClient_id(client_id);
		at.setUsername(userid);
		at.setToken_type("password");
		at=sqlOfUser.InsertUser(at, passwd);
		String result;
		if(at==null){
			result="ERROR";
		}
		else{
			result=at.toString();
		}
		out.write(result.getBytes("utf-8"));
		out.flush();
		out.close();
		return;
	}
	
}
