package com.fc.bank;

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


@WebServlet(name="registerbankservlet",urlPatterns = "/register")
public class RegisterBankServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String Name=request.getParameter("name");
		String AccNumber=request.getParameter("accno");
		String Age=request.getParameter("age");
		String Mobile=request.getParameter("mobile");
		String Gender=request.getParameter("gender");
		String Password=request.getParameter("password");
		String ConfirmPassword=request.getParameter("cpassword");
		String Initial_amount=request.getParameter("bamount");
		
		
		
		ServletContext context=getServletContext();
		
		try {
			Connection connect=DbConnectionUtil.getmysqlConnection(context);
			
			System.out.println(".......first check ther user already exixts or not........");
			
			String sqlQuery="select * from userdetails where Account_Number=? && Confirm_Password=?";
			PreparedStatement pstatement=connect.prepareStatement(sqlQuery);
			
			pstatement.setString(1,AccNumber );
			pstatement.setString(2, Password);
			
			ResultSet res= pstatement.executeQuery();
			
			int count=0;
			String pwd=null;
			String Accno=null;
			
			while(res.next()) {
				++count;
				Accno=res.getString(2);
				System.out.println( "already have account "+Accno);
			}
			if(count==1) {
				if(Accno!=null && Accno.equals(AccNumber)) {
					System.out.println("........The user already exixts.......");
					RequestDispatcher dispatcher=request.getRequestDispatcher("error.html");
					dispatcher.forward(request, response);
					
					
				}
				
			}else {

				String registerQuery="insert into userdetails values(?,?,?,?,?,?,?,?)";
				PreparedStatement pstatement2=connect.prepareStatement(registerQuery);
				
				
				System.out.println("..........reached on register page.....");
				
				pstatement2.setString(1,Name);
				pstatement2.setString(2, AccNumber);
				pstatement2.setInt(3, Integer.parseInt(Age));
				pstatement2.setString(4, Gender);
				pstatement2.setString(5, Mobile);
				pstatement2.setString(6, Password);
				pstatement2.setString(7, ConfirmPassword);
				pstatement2.setDouble(8, Double.parseDouble(Initial_amount));
				
				
				int res2= pstatement2.executeUpdate();
				
				if(res2==1) {
					System.out.println("user Added Successfully....");
					System.out.println("User Details =>" + " Name :"+Name+" AccNumber :"+AccNumber+" Age : "+ Age+" Gender : "+" Mobile :"+Mobile+" password :"+Password+" confirm_password :"+ConfirmPassword+" Initial_Amount :"+Initial_amount);
				}else {
					System.out.println("user Not Added....");
				}
			}
				
			
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
	}

}
