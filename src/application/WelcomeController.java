package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class WelcomeController {
	
	@FXML
	public void LogIn() {
		
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/application/LogIn.fxml"));
				Scene scene = new Scene(root,600,600);
				Main.window.setScene(scene);
				Main.window.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			
		}
	
	@FXML
	public void SignIn() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/SignIn.fxml"));
			Scene scene = new Scene(root,600,600);
			Main.window.setScene(scene);
			Main.window.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
}
	
