package com.comp380.summer13.group1.tabshare;

import java.util.ArrayList;

public class BillPayee {

	private String name;
	private ArrayList<BillItem> items = new ArrayList<BillItem>();
	private String phoneNumber;
	
	public BillPayee() {
		phoneNumber = null;
		name = null;
		items = new ArrayList<BillItem>();
	}

	public BillPayee(String nm, String number) {
		phoneNumber = number;
		name = nm;
		items = new ArrayList<BillItem>();
	}

	public BillPayee(String nm) {
		phoneNumber = null;
		name = nm;
		items = new ArrayList<BillItem>();
	}

	public void setName(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}

	public void addItem(BillItem bi) {

		items.add(bi);
	}

	public void addItem(String itemName, int quantity, double price) {
		items.add(new BillItem(itemName, quantity, price));
	}

	public BillItem getItem(int i) {
		return items.get(i);
	}

	public void removeItem(int i) {
		items.remove(i);
	}

	public void setPhone(String s) {
		phoneNumber = s;
	}

	public String getPhone() {
		return phoneNumber;
	}

	public double getTotal() {
		double runningTotal = 0.00;
		for (int i = 0; i < items.size(); i++) {
			double price = items.get(i).getPrice();
			int quantity = items.get(i).getQuantity();
			runningTotal += (price * quantity);
		}
		return runningTotal;
	}

}
