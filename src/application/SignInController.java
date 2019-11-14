package application;

import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignInController {
	@FXML
	private TextField name;
	
	@FXML
	private TextField email;
	
	@FXML
	private TextField pass;
	
	@FXML
	private TextField mbl;
	
	/**
	 * Insert the user data information
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	public void insertData() throws ClassNotFoundException, SQLException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/searchByLocation.fxml"));
			Scene scene = new Scene(root,600,600);
			Main.window.setScene(scene);
			Main.window.show();
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		String sql = "insert into diaryCustomer values(?,?,?,?,?)";
		// DBConnection.getConnection();
		Connection tempCon =DBConnection.getConnection();
		PreparedStatement st = tempCon.prepareStatement(sql);
		Customer cus = new Customer(name.getText(),email.getText(),pass.getText(),mbl.getText());
		
		st.setNull(1,java.sql.Types.INTEGER);
		st.setString(2,cus.getName());
		st.setString(3, cus.getEmail());
		st.setString(4, cus.getPass());
		st.setString(5, cus.getMbl());
		
		int i=st.executeUpdate();
		System.out.println(i);
		sql = "{ ? = call login_verify(?,?)}";
		CallableStatement stmt = tempCon.prepareCall(sql);
		stmt.setString(2, email.getText());
		stmt.setString(3, pass.getText());
		stmt.registerOutParameter(1, Types.INTEGER);
		
		stmt.execute();
		
		Main.cusID = stmt.getInt(1);
		
		tempCon.close();
	
	}
	
	/**
	 * Get back to the welcome screen
	 */
	@FXML
	public void getback() {
		try {
			Main.cusID = 0;
			Parent root = FXMLLoader.load(getClass().getResource("/application/Welcome.fxml"));
			Scene scene = new Scene(root,600,600);
			Main.window.setScene(scene);
			Main.window.show();
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	
}
