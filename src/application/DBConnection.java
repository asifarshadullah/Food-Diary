package application;

import java.sql.*;

public class DBConnection {
	private static Connection con;
	
	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return con;
	}
	
}
