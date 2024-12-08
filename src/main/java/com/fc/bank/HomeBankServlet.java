package com.fc.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fc.dbUtil.DbConnectionUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name="bankhomePage", urlPatterns = "/home")
public class HomeBankServlet  extends HttpServlet{
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		String UserName=request.getParameter("username");
		String Password=request.getParameter("pwd");
		
		ServletContext context=getServletContext();
		
		try {
			Connection connect=DbConnectionUtil.getmysqlConnection(context);
			
			String sqlQuery="select * from userdetails where Name=? && Confirm_Password=?";
			PreparedStatement pstatement=connect.prepareStatement(sqlQuery);
			
			pstatement.setString(1,UserName );
			pstatement.setString(2, Password);
			
			ResultSet res= pstatement.executeQuery();
			
			int count=0;
			String pwd=null;
			
			
			while(res.next()) {
				++count;
				pwd=res.getString(7);
				String userInfo= "userName :"+res.getString(1)+" password :"+res.getString(7);
				System.out.println("login Successfully...");
				System.out.println(userInfo);
				
			}
			if(count==1) {
				if(pwd!=null && Password.equals(pwd)) {
					RequestDispatcher dispatcher=request.getRequestDispatcher("index.html");
					dispatcher.forward(request, response);
				}
				
			}else {
				RequestDispatcher dispatcher=request.getRequestDispatcher("error.html");
				dispatcher.forward(request, response);
			}
				
			
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
