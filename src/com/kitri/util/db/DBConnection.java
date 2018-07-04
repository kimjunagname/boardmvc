package com.kitri.util.db;

import java.sql.*;

import javax.naming.*;
import javax.sql.DataSource;

public class DBConnection {

	public static Connection makeConnection() throws SQLException {
		//conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.18.43:1521:xe", "kitri", "kitri");
		DataSource datasource = null;
				
		try {
			Context initContext = new InitialContext();
			Context ctx = (Context) initContext.lookup("java:comp/env");
			//context 로 형변환(java:comp/env >> jdbc 풀 접근 
			datasource = (DataSource) ctx.lookup("jdbc/kitri");
			//datasource 경로 찾기	
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return datasource.getConnection();
	}
	
	
	
	//	static {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static Connection makeConnection() throws SQLException {
//		Connection conn = null;
//		//conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.18.43:1521:xe", "kitri", "kitri");
//		conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "speedtago", "speedtago");
//		return conn;
//	}
	
	
	
	
}
