package com.comp380.summer13.group1.tabshare;

public class BillItem {
	private String itemName;
	private int quantity;
	private double price;

	public BillItem() {
		this.itemName = null;
		this.quantity = 0;
		this.price = 0.00;
	}
	
	public BillItem(String itemName, int quantity, double price) {
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
	}

	public void setName(String s) {
		itemName = s;
	}

	public String getName() {
		return itemName;
	}

	public void setQuantity(int i) {
		quantity = i;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setPrice(double d) {
		price = d;
	}

	public double getPrice() {
		return price;
	}

}
