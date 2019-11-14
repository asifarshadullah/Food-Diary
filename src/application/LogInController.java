package application;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class LogInController {
	@FXML
	private TextField email;
	
	@FXML
	private TextField pass;
	
	@FXML
	private Label logStatus;
	
	
	
	/**
	 * This method is used for logging in
	 * 
	 * @throws SQLException This throws SQL Exception
	 */
	@FXML
	public void logIn() throws SQLException {
		boolean flag = false;
		Connection tempCon = DBConnection.getConnection();
		String sql = "{ ? =call login_verify(?,?)}";
		CallableStatement stmt = tempCon.prepareCall(sql);
		stmt.setString(2, email.getText());
		stmt.setString(3, pass.getText());
		stmt.registerOutParameter(1, Types.INTEGER);
		
		stmt.execute();
		int temp = stmt.getInt(1);
		System.out.println(temp);
		if(temp==0) {
			
			logStatus.setText("Login Failed");
		}
		else
		{
			Main.cusID = temp;
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("/application/searchByLocation.fxml"));
			
			try {
				Loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Parent root = Loader.getRoot();
			Scene scene = new Scene(root,600,600);
			Main.window.setScene(scene);
			Main.window.show();
		}
		
		tempCon.close();
		
	}
	
	/**
	 * This method gets back to the welcome screen
	 * @throws SQLException Throws SQL Exception
	 */
	@FXML
	public void getBack() throws SQLException {
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
