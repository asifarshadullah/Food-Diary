package application;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.net.URL;
import java.sql.*;
import java.util.HashSet;
import java.util.ResourceBundle;

public class searchByLocationController implements Initializable {
	@FXML
	private ListView<String> listView;
	
	@FXML
	private Button btn;
	
	@FXML
	private Label locStatus;
	
	@FXML
	private TextField txt;
	private ObservableList<String> entries = FXCollections.observableArrayList();
	@FXML private TableView<Menu> billTable;
	@FXML private TableColumn<Menu,String> itemBill;
	@FXML private TableColumn<Menu,Integer> priceBill;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Connection con = DBConnection.getConnection();
			String sql = "select res_location from diaryRestaurant";
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			HashSet<String> set = new HashSet<>();
			while(rs.next()) {
				if(!set.contains(rs.getString(1))) {
					entries.add(rs.getString(1));
					set.add(rs.getString(1));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("**************");
		
		
		
		listView.setMaxHeight(180);
		listView.setItems(entries);
		System.out.println("##############");
		txt.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> arg0, Object oldVal, Object newVal) {
				handleSearchByKey2((String)oldVal,(String)newVal);
				System.out.println("!!!!!!!!!!!!!!");
			}
			
		});
		
		itemBill.setCellValueFactory(new PropertyValueFactory<Menu,String>("item"));
		priceBill.setCellValueFactory(new PropertyValueFactory<Menu,Integer>("price"));
		billTable.setItems(Main.bill);
		billTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	
	
	/**
	 * This method searches for restaurants in a specific location
	 * 
	 * @throws SQLException
	 */
	public void Search(ActionEvent event) throws SQLException {
		System.out.println(">>>>>>>>>>>>>>>>>");
		boolean flag=false;
		for(String entry:entries) {
			String temp=entry.toUpperCase();
			if(txt.getText().toUpperCase().equals(temp)) {
				System.out.println("666666666");
				if(flag==false) {
					Connection con = DBConnection.getConnection();
					String sql = "{ call insert_tempRes(?)";
					CallableStatement stmt = con.prepareCall(sql);
					stmt.setString(1, temp);
					stmt.execute();
					con.close();
					flag=true;
				}
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/application/SearchByName.fxml"));
					Scene scene = new Scene(root,600,600);
					Main.window.setScene(scene);
					Main.window.show();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				break;
			}
		}
		
		if(flag==false)
		{
			locStatus.setText("Invalid Location");
		}
	}
	
	/**
	 * Submits the generated bill
	 */
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
	
	/**
	 * Removes items from receipt list
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
	 * A user logs out and goes back to the welcome screen
	 */
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
	        if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
	            // Restore the lists original set of entries 
	            // and start from the beginning
	            listView.setItems( entries );
	        }
	         
	        // Break out all of the parts of the search text 
	        // by splitting on white space
	        String[] parts = newVal.toUpperCase().split(",");
	 
	        // Filter out the entries that don't contain the entered text
	        ObservableList<String> subentries = FXCollections.observableArrayList();
	        for ( Object entry: listView.getItems() ) {
	            boolean match = true;
	            String entryText = (String)entry;
	            for ( String part: parts ) {
	                // The entry needs to contain all portions of the
	                // search string *but* in any order
	                if ( ! entryText.toUpperCase().contains(part) ) {
	                    match = false;
	                    break;
	                }
	            }
	 
	            if ( match ) {
	            	System.out.println("---------------");
	                subentries.add(entryText);
	            }
	        }
	        listView.setItems(subentries);
	    }

	

}
