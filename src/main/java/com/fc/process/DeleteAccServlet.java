package com.fc.process;

import java.io.IOException;
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

@WebServlet(name="deleteAccountServlet",urlPatterns = "/deleteacc")
public class DeleteAccServlet extends HttpServlet{
	public void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		System.out.println("come to Account Delete page..");
		
		String AccNo=request.getParameter("accno");
		String UserName =request.getParameter("username");
		String Password=request.getParameter("pwd");
		
		System.out.println("getting details from delete page...");
		
		ServletContext context=getServletContext();
		
		try {
			Connection connect=DbConnectionUtil.getmysqlConnection(context);
			
			String sqlQuery="select * from userdetails where Account_Number=? && Confirm_Password=?";
			PreparedStatement pstatement=connect.prepareStatement(sqlQuery);
			System.out.println("first reading my account successfully...");
			
			pstatement.setString(1, AccNo);
			pstatement.setString(2, Password);
			
			ResultSet res= pstatement.executeQuery();
			int count=0;
			String pwd=null;
			
			
			System.out.println("....after executeUpdate  successfully...");
			
			if(res.next()) {
				
				pwd=res.getString(7);
				System.out.println("password getting successfully");	
				
			}
			
				if(pwd!=null && Password.equals(pwd)) {
					System.out.println("...enter the update query...");
					String deleteQuery="delete from userdetails where Account_Number=?";
					PreparedStatement pstatement2=connect.prepareStatement(deleteQuery);
					
					pstatement2.setString(1, AccNo);
					
					System.out.println("delete Account No :" + AccNo);
					
					int afterDeleteAcc= pstatement2.executeUpdate();
					System.out.println("after execute delete query...");
					
					if(afterDeleteAcc==1) {
						System.out.println("delete account successfully");
					}
					else {
						System.out.println("not delete account ");
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
