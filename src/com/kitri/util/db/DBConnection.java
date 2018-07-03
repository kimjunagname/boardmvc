package com.kitri.util.db;

import java.sql.*;

public class DBConnection {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection makeConnection() throws SQLException {
		Connection conn = null;
		//conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.18.43:1521:xe", "kitri", "kitri");
		conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "speedtago", "speedtago");
		return conn;
	}
	
}
