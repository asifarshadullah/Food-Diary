package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ThanksPageController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void searchAgain()
	{
		try {
			Connection tempCon = DBConnection.getConnection();
			Main.resID = 0;
			String sql = "delete from tempRestaurant";
			PreparedStatement st = tempCon.prepareStatement(sql);
			st.executeUpdate();
			Main.bill.clear();
			sql = "delete from currentRes";
			st =tempCon.prepareStatement(sql);
			st.executeUpdate();
			Parent root = FXMLLoader.load(getClass().getResource("/application/searchByLocation.fxml"));
			Scene scene = new Scene(root,600,600);
			Main.window.setScene(scene);
			Main.window.show();
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void LogOut() {
		try {
			Connection tempCon = DBConnection.getConnection();
			Main.cusID = 0;
			Main.resID = 0;
			String sql = "delete from tempRestaurant";
			PreparedStatement st = tempCon.prepareStatement(sql);
			st.executeUpdate();
			sql = "delete from currentRes";
			st =tempCon.prepareStatement(sql);
			st.executeUpdate();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Welcome.fxml"));
			Scene scene = new Scene(root,600,600);
			Main.window.setScene(scene);
			Main.window.show();
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}

}
