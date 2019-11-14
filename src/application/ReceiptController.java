package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReceiptController implements Initializable{
	@FXML private TableView<Menu> billTable;
	@FXML private TableColumn<Menu,String> itemBill;
	@FXML private TableColumn<Menu,Integer> priceBill;
	
	@FXML private Label finalBill;
	
	@FXML private TextField creditNo;
	@FXML private TextField mblNo;
	@FXML private TextField address;
	
	
	private int total;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		itemBill.setCellValueFactory(new PropertyValueFactory<Menu,String>("item"));
		priceBill.setCellValueFactory(new PropertyValueFactory<Menu,Integer>("price"));
		billTable.setItems(Main.bill);
		setFinalBill();
		
	}
	
	/**
	 * Generates the bill for the customer
	 */
	public void setFinalBill() {
		total =0;
		for(Menu temp:billTable.getItems()) {
			total+=priceBill.getCellObservableValue(temp).getValue();
		}
		
		finalBill.setText(Integer.toString((int) total));
	}
	
	public void submitBill() throws SQLException {
		Connection con = DBConnection.getConnection();
		String sql = "insert into transactionRecord values(?,?,?,?,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setNull(1, Types.INTEGER);
		st.setInt(2, Main.cusID);
		st.setString(3, creditNo.getText());
		st.setString(4, mblNo.getText());
		st.setString(5, address.getText());
		st.setInt(6, Main.resID);
		st.setInt(7, total);
		
		st.executeUpdate();
		
		
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/ThanksPage.fxml"));
			Scene scene = new Scene(root,600,600);
			Main.window.setScene(scene);
			Main.window.show();
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void goToPrevious() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Menulist.fxml"));
			Scene scene = new Scene(root,600,600);
			Main.window.setScene(scene);
			Main.window.show();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void LogOut() {
		try {
			Connection tempCon = DBConnection.getConnection();
			Main.cusID = 0;
			Main.resID = 0;
			Main.bill.clear();
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
