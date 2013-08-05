package com.comp380.summer13.group1.tabshare;

import java.util.ArrayList;

public class BillPayee {

	private String firstName;
	private String lastName;
	private ArrayList<BillItem> items;
	private String phoneNumber;
	private String email;

	public BillPayee(String first, String last) {
		phoneNumber = null;
		firstName = first;
		lastName = last;
		items = new ArrayList<BillItem>();
		email = null;
	}

	public BillPayee(String first, String last, String phone) {
		phoneNumber = phone;
		firstName = first;
		lastName = last;
		items = new ArrayList<BillItem>();
		email = null;
	}

	public BillPayee(String first, String last, String phone,
			String email) {
		phoneNumber = phone;
		firstName = first;
		lastName = last;
		items = new ArrayList<BillItem>();
		this.email = email;
	}

	public void setFirstName(String s) {
		firstName = s;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String s) {
		lastName = s;
	}

	public String getLastName() {
		return lastName;
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

	public void setEmail(String s) {
		email = s;
	}

	public String getEmail() {
		return email;
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
