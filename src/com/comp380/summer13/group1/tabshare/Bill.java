package com.comp380.summer13.group1.tabshare;

import java.util.ArrayList;

public class Bill {

	int size;
	double total;
	private ArrayList<BillGroup> group;

	public Bill() {
		total = 0.00;
		group = new ArrayList<BillGroup>();
	}

	public void addGroup(String first, String last) {
		group.add(new BillGroup(first, last));
	}

	public BillGroup getGroup(int groupIndex){
		return group.get(groupIndex);
	}
	public void removeGroup(int groupIndex) {
		group.remove(groupIndex);
	}

	public void addPayeeToGroup(int groupIndex, String first, String last){
		group.get(groupIndex).addPayee(first, last);
	}
	
	public BillPayee getBillPayee(int groupIndex, int index) {
		return group.get(groupIndex).getPayee(index);
	}

	public void removePayee(int groupIndex, int index) {
		group.get(groupIndex).removePayee(index);
	}

	public void addItem(int groupIndex, int payeeIndex, String itemName,
			int quantity, double price) {
		group.get(groupIndex).getPayee(payeeIndex)
				.addItem(itemName, quantity, price);
	}

	public BillItem getItem(int groupIndex, int payeeIndex, int itemIndex) {
		return group.get(groupIndex).getPayee(payeeIndex).getItem(itemIndex);
	}

	public void removeItem(int groupIndex, int payeeIndex, int itemIndex) {
		group.get(groupIndex).getPayee(payeeIndex).removeItem(itemIndex);
	}

	public double getPayeeTotal(int groupIndex, int payeeIndex) {
		return group.get(groupIndex).getPayee(payeeIndex).getTotal();
	}
	
	public double getGroupTotal(int groupIndex){
		return group.get(groupIndex).groupTotal();
	}

	public double getBillTotal() {
		double total = 0;
		for (int i = 0; i < group.size(); i++) {
			total += group.get(i).groupTotal();
		}
		return total;
	}
}
