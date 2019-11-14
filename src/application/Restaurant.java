package application;

public class Restaurant {
	private int id;
	
	private String name;
	private String location;
	private String menuTable;
	
	public Restaurant() {
		id=0;
		name= "ask";
		location = null;
		menuTable = null;
	}
	
	/**
	 * This constructor initializes the Restaurant class
	 * @param id  Restaurant Id of a restaurant
	 * @param name   Name of a restaurant
	 * @param email  Email of a restaurant
	 * @param location  Location of restaurant
	 * @param mbl  Mobile no of a restaurant
	 * @param menuTable  Menu Database of restaurant
	 */
	public Restaurant(int id,String name,String email,String location,String menuTable) {
		this.id= id;
		this.name=name;
		this.location=location;
		this.menuTable= menuTable;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMenuTable() {
		return menuTable;
	}
	public void setMenuTable(String menuTable) {
		this.menuTable = menuTable;
	}

}
