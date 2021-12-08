package uapbazar.Store;

import java.io.Serializable;

public abstract class Product implements Cloneable,Serializable{
	private String name, id;
	private Category category;
	private int quantity;
	private double price;
	private boolean onSale;
	private int salePercent;
	
	public Product(String name, String id, Category category, int quantity, double price) {
		super();
		this.name = name;
		this.id = id;
		this.category = category;
		this.quantity = quantity;
		this.price = price;
		
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isOnSale() {
		return onSale;
	}

	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void updateQuantity(int quantity) {
		this.quantity += quantity;
	}

	public int getSalePercent() {
		return salePercent;
	}

	public void setSalePercent(int salePercent) {
		this.salePercent = salePercent;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	

	public double totalPrice(int amt) {
		return amt*price;
	}

	public double salePrice(int amt) {
		if(this.isOnSale()) {
			return (double)amt*price*((100-salePercent)/100.0);
			
		}
		else {
			
			return totalPrice(amt);
		}
	}
	public abstract void putOnSale(Object criterion, int percentage) throws ExpirationException;

	// TO show the summary or customer view
	@Override
	public String toString() {
		if (!onSale)
			//return  String.format("%s(%s)-%.1f tk-----Amount: %d", name,id,price,quantity);
			return  String.format("Name: %s\nID: %s\nPrice: %.1ftk\nAmount: %d",name,id,price,quantity);

		else
			//return  String.format("%s(%s)-On Sale-%.2f tk(Original Price:%.1f)----Amount: %d", name,id,salePrice(1), price,quantity);
			return  String.format("Name: %s\nID:%s\nOn Sale: %.2ftk\nOriginal Price: %.1ftk\nAmount: %d",name,id,salePrice(1),price,quantity);
	}
	
	// This is for showing the details
	public String details() {
		return "Name=" + name + "\tId= " + id + "\tCategory= " + category + "\tQuantity= " + quantity + "\tprice= "
				+ price+"tk" + " \tOnSale= " + onSale + "\tSalePercent =" + salePercent;
	}
	
	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
	}
	
}




