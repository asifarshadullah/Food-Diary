package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Rating {
	
	private long totalRate;
	private int cnt;
	
	public Rating() {
		totalRate=0;
		cnt=0;
	}
	
	private int resId;
	
	
	



	
	
	
	
	public long getTotalRate() {
		return totalRate;
	}










	public void setTotalRate(long totalRate) {
		this.totalRate = totalRate;
	}










	public int getCnt() {
		return cnt;
	}










	public void setCnt(int cnt) {
		this.cnt = cnt;
	}










	public int getResId() {
		return resId;
	}










	public void setResId(int resId) {
		this.resId = resId;
	}










	public void getRateData() throws SQLException {
		Connection con = DBConnection.getConnection();
		String sql ="select * from tempRestaurant";
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			resId=rs.getInt(1);
		}
		
		con.close();
	}

}
