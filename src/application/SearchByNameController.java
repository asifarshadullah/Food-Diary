package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchByNameController implements Initializable {
	
	@FXML
	private TextField txt;
	
	@FXML
	private ListView<String> list;
	
	@FXML private TableView<Menu> billTable;
	@FXML private TableColumn<Menu,String> itemBill;
	@FXML private TableColumn<Menu,Integer> priceBill;
	
	private ObservableList<String> entries = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			loadList();
			list.setMaxHeight(180);
			list.setItems(entries);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		txt.textProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> arg0, Object oldVal, Object newVal) {
				handleSearchByKey2((String)oldVal,(String)newVal);
				
			}
			
		});
		
		itemBill.setCellValueFactory(new PropertyValueFactory<Menu,String>("item"));
		priceBill.setCellValueFactory(new PropertyValueFactory<Menu,Integer>("price"));
		billTable.setItems(Main.bill);
		billTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	
	/**
	 * Searches for a restaurant by name
	 * @throws SQLException
	 */
	public void Search() throws SQLException {
		
		for(String entry:entries) {
			String temp=entry.toUpperCase();
			if(txt.getText().toUpperCase().equals(temp)) {
				Connection con = DBConnection.getConnection();
				String sql = "select * from tempRestaurant";
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					if(rs.getString(2).toUpperCase().equals(temp)) {
						String sql1 = "insert into currentRes values(?,?,?,?)";
						PreparedStatement stmt = con.prepareStatement(sql1);
						stmt.setInt(1, rs.getInt(1));
						stmt.setString(2, rs.getString(2));
						stmt.setString(3, rs.getString(3));
						stmt.setString(4, rs.getString(4));
						
						Main.resID =rs.getInt(1);
						
						stmt.executeUpdate();
						
						System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
						
						try {
							Parent root = FXMLLoader.load(getClass().getResource("/application/Menulist.fxml"));
							Scene scene = new Scene(root,600,600);
							Main.window.setScene(scene);
							Main.window.show();
						}catch(Exception e1) {
							e1.printStackTrace();
						}
						break;
						
					}
				}
			}
		}
	}
	
	
	
	public void loadList() throws SQLException {
		Connection con =DBConnection.getConnection();
		String sql = "select * from tempRestaurant";
		PreparedStatement st = con.prepareStatement(sql);
		System.out.println("||||||||||||||||");
		HashSet<String> set = new HashSet();
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			if(!set.contains(rs.getString(2)))
			{
				entries.add(rs.getString(2));
				set.add(rs.getString(2));
			}
		}
	}
	
	/**
	 * Removes items from receipt
	 */
	public void removeFromList() {
		ObservableList<Menu> temp,temp1;
		System.out.println("************1");
		temp=billTable.getSelectionModel().getSelectedItems();
		System.out.println("************2");
		temp1=billTable.getItems();
		System.out.println("************4");
		temp.forEach(Main.bill::remove);
		System.out.println("************3");
		billTable.setItems(Main.bill);
		System.out.println("************5");
	}
	
	/**
	 * Goes to the previous page
	 */
	public void goToPrevious() {
		try {
			Connection con = DBConnection.getConnection();
			String sql = "delete from tempRestaurant";
			PreparedStatement st = con.prepareStatement(sql);
			st.executeUpdate();
			Parent root = FXMLLoader.load(getClass().getResource("/application/SearchByLocation.fxml"));
			Scene scene = new Scene(root,600,600);
			Main.window.setScene(scene);
			Main.window.show();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
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
	
	@FXML
	public void LogOut() {
		try {
			Connection tempCon = DBConnection.getConnection();
			Main.cusID = 0;
			Main.bill.clear();
			String sql = "delete from tempRestaurant";
			PreparedStatement st = tempCon.prepareStatement(sql);
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
	 * Searches dynamically for available locations
	 * @param oldVal Previous value of the textfield
	 * @param newVal Current value of the textfield
	 */
	public void handleSearchByKey2(String oldVal, String newVal) {
        // If the number of characters in the text box is less than last time
        // it must be because the user pressed delete
	 System.out.println("________________");
        if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
            // Restore the lists original set of entries 
            // and start from the beginning
            list.setItems( entries );
        }
         
        // Break out all of the parts of the search text 
        // by splitting on white space
        String[] parts = newVal.toUpperCase().split(",");
 
        // Filter out the entries that don't contain the entered text
        ObservableList<String> subentries = FXCollections.observableArrayList();
        for ( Object entry: list.getItems() ) {
            boolean match = true;
            String entryText = (String)entry;
            for ( String part: parts ) {
                // The entry needs to contain all portions of the
                // search string *but* in any order
                if ( ! entryText.toUpperCase().contains(part) ) {
                    match = false;
                    System.out.println("@@@@@@@@@@@@@");
                    break;
                }
            }
 
            if ( match ) {
            	System.out.println("---------------");
                subentries.add(entryText);
            }
        }
        list.setItems(subentries);
    }
	
	
}
