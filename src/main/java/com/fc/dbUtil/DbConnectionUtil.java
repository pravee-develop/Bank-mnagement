package com.fc.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.ServletContext;

public class DbConnectionUtil {
	public static Connection getmysqlConnection(ServletContext context) throws ClassNotFoundException, SQLException {
		String driverClassname=context.getInitParameter("driver-class");
		String url=context.getInitParameter("url");
		String hostName=context.getInitParameter("host");
		String port=context.getInitParameter("port");
		String databasename=context.getInitParameter("database-name");
		String userName=context.getInitParameter("user-name");
		String password=context.getInitParameter("password");
		
		
		Class.forName(driverClassname);
		String fullurl= url + "://" + hostName + ":" + port + "/" + databasename;
		Connection connect=DriverManager.getConnection(fullurl,userName,password);
		System.out.println("fullurl :"+fullurl);
		System.out.println("jdbc util Class executed successfully");
		
		
		return connect;
		
	}

}
