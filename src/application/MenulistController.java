package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MenulistController implements Initializable {
	@FXML private TableView<Menu> menuTable;
	@FXML private TableColumn<Menu,String> itemMenu;
	@FXML private TableColumn<Menu,Integer> priceMenu;
	
	@FXML private TableView<Menu> billTable;
	@FXML private TableColumn<Menu,String> itemBill;
	@FXML private TableColumn<Menu,Integer> priceBill;
	
	@FXML private Label rate;
	@FXML private TextField giveRate;
	
	private String menuData;
	private int tempResId;
	private Rating obRate=new Rating();
	//private Restaurant menu;
	
	private ObservableList<Menu> menu = FXCollections.observableArrayList();
	
	/**
	 * Loads menu of a restaurant
	 * @param item
	 * @param price
	 */
	public void loadList(String item,int price) {
		Menu temp = new Menu(item,price);
		menu.add(temp);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			getMenuData();
			loadMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		itemMenu.setCellValueFactory(new PropertyValueFactory<Menu,String>("item"));
		priceMenu.setCellValueFactory(new PropertyValueFactory<Menu,Integer>("price"));
		menuTable.setItems(menu);
		menuTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		itemBill.setCellValueFactory(new PropertyValueFactory<Menu,String>("item"));
		priceBill.setCellValueFactory(new PropertyValueFactory<Menu,Integer>("price"));
		billTable.setItems(Main.bill);
		billTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		/*try {
			//getRateTable();
			//getRateData();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			//showRating();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}
	
	/**
	 * Adds items to the Receipt
	 */
	public void addToCart() {
		Main.bill.addAll(menuTable.getSelectionModel().getSelectedItems());
		
	}
	
	/*public void setRestaurant(int id, String name, String loc, String menu )
	{
		currRes.setId(id);
		currRes.setName(name);
		currRes.setLocation(loc);
		currRes.setMenuTable(menu);
	}*/
	
	public void submitBill() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Receipt.fxml"));
			Scene scene = new Scene(root,600,600);
			Main.window.setScene(scene);
			Main.window.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeFromList() {
		ObservableList<Menu> temp,temp1;
		temp=billTable.getSelectionModel().getSelectedItems();
		temp1=billTable.getItems();
		temp.forEach(Main.bill::remove);
		billTable.setItems(Main.bill);
	}
	
	/**
	 * Loads Menu from database
	 * @throws SQLException
	 */
	public void loadMenu() throws SQLException {
		Connection con = DBConnection.getConnection();
		String sql = "select * from "+ menuData;
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			loadList(rs.getString(2),rs.getInt(3));
		}
		
	}
	
	/**
	 * Get menu data table
	 * @throws SQLException
	 */
	public void getMenuData() throws SQLException {
		Connection con = DBConnection.getConnection();
		String sql = "select * from currentRes";
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			menuData = rs.getString(4);
		}
		con.close();
	}
	
	public void goToPrevious() {
		try {
			Connection con = DBConnection.getConnection();
			String sql = "delete from currentRes";
			PreparedStatement st = con.prepareStatement(sql);
			st.executeUpdate();
			Parent root = FXMLLoader.load(getClass().getResource("/application/SearchByName.fxml"));
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
	
	/**
	 * Get Restaurant id
	 * @throws SQLException
	 */
	public void getRateTable() throws SQLException {
		Connection con = DBConnection.getConnection();
		String sql = "select resId from tempRestaurant";
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			tempResId=rs.getInt(1);
		}
		con.close();
	}
	
	public void getRateData() throws SQLException {
		Connection con = DBConnection.getConnection();
		String sql = "select * from rating where resId=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, tempResId);
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			obRate.setTotalRate(rs.getLong(2));
			obRate.setCnt(rs.getInt(3));
		}
		System.out.println("*************"+obRate.getTotalRate()+" "+obRate.getCnt());
		con.close();
		
	}
	
	/**
	 * Show rating of a restaurant
	 * @throws SQLException
	 */
	public void showRating() throws SQLException {
		double temp =(double) (obRate.getTotalRate()/obRate.getCnt());
		String s = Double.toString(temp);
		System.out.println(s);
		rate.setText(s);
		
	}
	
	/**
	 * Update rating of a restaurant
	 * @throws SQLException
	 */
	public void updateRating() throws SQLException {
		long temp1 = Long.parseLong(giveRate.getText());
		long temp = obRate.getTotalRate() + temp1;
		obRate.setTotalRate(temp);
		System.out.println("11111111"+temp);
		obRate.setCnt(obRate.getCnt()+1);
		System.out.println("222222222"+obRate.getCnt());
		Connection con = DBConnection.getConnection();
		String sql = "update rating set totalRate=?,cnt=? where resId=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, temp);
		st.setInt(2, obRate.getCnt());
		st.setInt(3, tempResId);
		st.executeUpdate();
		
		showRating();
	}

}
