package com.fc.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fc.dbUtil.DbConnectionUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="forgetPassword",urlPatterns = "/forget")
public class ForgetPassServlet extends HttpServlet{
	public void service(HttpServletRequest request,HttpServletResponse response) {
		
		String UserName=request.getParameter("name");
		String Mobile=request.getParameter("mobile");
		String AccNumber=request.getParameter("accno");
		String Password=request.getParameter("password");
		String ConfirmPassword=request.getParameter("cpassword");
		

		ServletContext context=getServletContext();
		
		try {
			Connection connect=DbConnectionUtil.getmysqlConnection(context);
			
			String sqlQuery="update userdetails set Password=?,Confirm_Password=? where Account_Number=? ";
			PreparedStatement pstatement=connect.prepareStatement(sqlQuery);
			
		
			pstatement.setString(1,Password);
			pstatement.setString(2,ConfirmPassword );
			pstatement.setInt(3, Integer.parseInt(AccNumber));
			
			
			int res= pstatement.executeUpdate();
			
			if(res==1) {
				System.out.println("user Password Updated  Successfully....");
				System.out.println("User Details =>" + " AccNumber : "+AccNumber+" Mobile : "+Mobile+" password : "+Password+" confirm password : "+ConfirmPassword);
			}else {
				System.out.println("user Password Not Updates....");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
