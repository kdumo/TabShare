package com.comp380.summer13.group1.tabshare;

public class BillItem {
	private String itemName;
	private double quantity;
	private double price;

	public BillItem() {
		this.itemName = null;
		this.quantity = 0;
		this.price = 0.00;
	}
	
	public BillItem(double quantity, double price) {
		this.itemName = null;
		this.quantity = quantity;
		this.price = price;
	}
	
	public BillItem(String itemName, double quantity, double price) {
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

	public void setQuantity(double i) {
		quantity = i;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setPrice(double d) {
		price = d;
	}

	public double getPrice() {
		return price;
	}

	public double getSubtotal() {
		double subtotal = quantity*price;
		subtotal = (double)Math.ceil(subtotal * 10000) / 10000;
		subtotal = (double)Math.round(subtotal*100)/100;
		return subtotal;
	}

}
