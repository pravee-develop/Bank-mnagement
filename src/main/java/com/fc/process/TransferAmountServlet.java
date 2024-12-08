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

@WebServlet(name="TransferAmount",urlPatterns = "/transferamt")
public class TransferAmountServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		System.out.println("come to Transfer Amount page..");
		
		String My_AccNo=request.getParameter("accno");
		String Target_AccNo=request.getParameter("taccno");
		String UserName =request.getParameter("username");
		String Password=request.getParameter("pwd");
		String Transfer_Amount=request.getParameter("tamount");
		
		System.out.println("getting details from Transfer Amount page...");
		
		ServletContext context=getServletContext();
		
		try {
			Connection connect=DbConnectionUtil.getmysqlConnection(context);
			System.out.println("read my account details....");
			
			String sqlQuery="select * from userdetails where Account_Number=? && Confirm_Password=? ";
			PreparedStatement pstatement=connect.prepareStatement(sqlQuery);
			System.out.println("myAccount  reading successfully...");
			
			pstatement.setString(1, My_AccNo);
			pstatement.setString(2, Password);
			
			System.out.println("my account number :" + My_AccNo);
			System.out.println("my account pass :" + Password);
			
			
			ResultSet readmy_Acc= pstatement.executeQuery();
			
			String pwd=null;
			
			double My_balance=0;
			System.out.println("affter execute myAccount successfully...");
			
			if(readmy_Acc.next()) {
				
				pwd=readmy_Acc.getString(7);
				My_balance=readmy_Acc.getDouble(8);	
				System.out.println(pwd);
				System.out.println(My_balance);
				System.out.println("Transfer amount :"+Transfer_Amount);
				System.out.println("getting myAccount  previus balance ");
				System.out.println("my account reading completed....");
			}
			
				if(pwd!=null && Password.equals(pwd)) {
					System.out.println("enter the myAccount transfer process...");
					String widrawQuery="update userdetails set Balance_Amount =? where Account_Number=? ";
					PreparedStatement pstatement2=connect.prepareStatement(widrawQuery);
					double widraw=Double.parseDouble(Transfer_Amount);
					double currentBalance=Math.abs(My_balance-widraw);
					
				
					pstatement2.setDouble(1, currentBalance);
					pstatement2.setString(2, My_AccNo);
					
					System.out.println("previous amount :"+My_balance);
					System.out.println("Transfer  amount :"+ widraw);
					System.out.println("currentBalance :" + currentBalance);
					
					System.out.println("Account No :" + My_AccNo);
					
					int afterwidraw= pstatement2.executeUpdate();
					System.out.println("after execute pstatement2 query...");
					
					if(afterwidraw==1) {
						System.out.println("widraw successfully");
					}
					else {
						System.out.println("not widraw successfully");
					}
					
					System.out.println("....read target account details...");
					
					String sqlQuery2="select * from userdetails where Account_Number=? ";
					PreparedStatement pstatement3=connect.prepareStatement(sqlQuery2);
					System.out.println("Target Account  reading successfully...");
					
					pstatement3.setString(1, Target_AccNo);
					
					
					System.out.println("Target Account number :" + Target_AccNo);
					System.out.println("deposit amount :" + Transfer_Amount);
					
					ResultSet readTarget_Acc= pstatement3.executeQuery();
					
					double TargetAcc_balance=0;
					String Target_pwd=null;
					if(readTarget_Acc.next()) {
						
						Target_pwd=readTarget_Acc.getString(7);
						TargetAcc_balance=readTarget_Acc.getDouble(8);	
						System.out.println("target Account Pass :"+ Target_pwd);
						System.out.println("target account Balance :"+TargetAcc_balance);
						System.out.println("Transfer amount :"+Transfer_Amount);
						System.out.println("getting Target Account  previus balance ");
						System.out.println("Target account reading completed....");
					}
					String depositQuery="update userdetails set Balance_Amount =? where Account_Number=? ";
					PreparedStatement pstatement4=connect.prepareStatement(depositQuery);
					double deposit=Double.parseDouble(Transfer_Amount);
					double Target_currentBalance=Math.abs(deposit+TargetAcc_balance);
					
				
					pstatement4.setDouble(1, Target_currentBalance);
					pstatement4.setString(2, Target_AccNo);
					
					System.out.println("previous amount :"+TargetAcc_balance);
					System.out.println("Transfer  amount :"+ deposit);
					System.out.println("currentBalance :" + Target_currentBalance);
					
					System.out.println("Account No :" + Target_AccNo);
					
					int afterTransfer= pstatement4.executeUpdate();
					System.out.println("after execute query...");
					
					if(afterTransfer==1) {
						System.out.println("Amount Transfer successfully");
					}
					else {
						System.out.println("not Amount Transfer successfully");
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
