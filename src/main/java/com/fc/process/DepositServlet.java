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


@WebServlet(name="depositServlet",urlPatterns = "/deposit")
public class DepositServlet  extends HttpServlet{
	public void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		System.out.println("come to deposite page..");
		
		String AccNo=request.getParameter("accno");
		String UserName =request.getParameter("username");
		String Password=request.getParameter("pwd");
		String depositAmount=request.getParameter("damount");
		
		System.out.println("getting details from deposit page...");
		
		ServletContext context=getServletContext();
		
		try {
			Connection connect=DbConnectionUtil.getmysqlConnection(context);
			
			String sqlQuery="select * from userdetails where Account_Number=? && Confirm_Password=? ";
			PreparedStatement pstatement=connect.prepareStatement(sqlQuery);
			System.out.println("first reading successfully...");
			
			pstatement.setString(1, AccNo);
			pstatement.setString(2, Password);
			
			
			ResultSet res= pstatement.executeQuery();
			int count=0;
			String pwd=null;
			
			double balance=0;
			System.out.println("affter execute successfully...");
			
			if(res.next()) {
				
				pwd=res.getString(7);
				balance=res.getDouble(8);	
				System.out.println(pwd);
				System.out.println(balance);
				System.out.println(depositAmount);
				System.out.println("getting previus balance ");
			}
			
				if(pwd!=null && Password.equals(pwd)) {
					System.out.println("enter the update query...");
					String depositQuery="update userdetails set Balance_Amount =? where Account_Number=? ";
					PreparedStatement pstatement2=connect.prepareStatement(depositQuery);
					double deposit=Double.parseDouble(depositAmount);
					double currentBalance=Math.abs(deposit+ balance);
					
				
					pstatement2.setDouble(1, currentBalance);
					pstatement2.setString(2, AccNo);
					
					System.out.println("before execute update code..");
					System.out.println("previous amount :"+balance);
					System.out.println("deposit amount :"+ deposit);
					System.out.println("currentBalance :" + currentBalance);
					
					System.out.println("Account No :" + AccNo);
					
					int afterDeposit= pstatement2.executeUpdate();
					System.out.println("after execute query...");
					
					if(afterDeposit==1) {
						System.out.println("deposit successfully");
					}
					else {
						System.out.println("not dposit");
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
