package application;

import java.sql.*;

public class Customer {
	private int id;
	
	private String name;
	private String email;
	private String pass;
	private String mbl;
	
	public Customer() {
		id=0;
		name=null;
		email = null;
		pass = null;
		mbl = null;
	}
	
	public Customer(String name, String email, String pass, String mbl) throws SQLException {
		this.id=0;
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.mbl = mbl;
	}
	/*
	public int getCurrentId() throws SQLException {
		Connection tempCon = DBConnection.getConnection();
		String sql = "select * from storeId";
		PreparedStatement st  = tempCon.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		int id=10;
		while(rs.next()) {
			id=rs.getInt(1);
			System.out.println(rs.getInt(1));
		}
		sql = "delete from storeId";
		st=tempCon.prepareStatement(sql);
		int i =st.executeUpdate();
		System.out.println("*****"+ i);
		sql = "insert into storeId values(?)";
		st=tempCon.prepareStatement(sql);
		System.out.println(id);
		id++;
		System.out.println(id);
		st.setInt(1, id);
		i = st.executeUpdate();
		System.out.println("&&&&&"+ i);
		tempCon.close();
		--id;
		return id;
	}
	*/
	public Customer(Customer ob) {
		id = ob.getId();
		name = ob.getName();
		email = ob.getEmail();
		pass = ob.getPass();
		mbl = ob.getMbl();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getMbl() {
		return mbl;
	}
	public void setMbl(String mbl) {
		this.mbl = mbl;
	}
	
	

}
