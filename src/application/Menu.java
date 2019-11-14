package application;

public class Menu {
	private String item;
	private int price;
	
	public Menu() {
		this.item=null;
		this.price=0;
	}
	public Menu(String item, int price) {
		super();
		this.item = item;
		this.price = price;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	

}
