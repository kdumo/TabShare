package com.comp380.summer13.group1.tabshare;

import java.util.ArrayList;

public class BillGroup {
	private ArrayList<BillPayee> billPayee = new ArrayList<BillPayee>();

	public BillGroup() {
		billPayee = new ArrayList<BillPayee>();
	}

	public BillGroup(String name, String number) {
		billPayee.add(new BillPayee(name, number));
	}

	public void addPayee(String name, String number) {
		billPayee.add(new BillPayee(name, number));
	}
	
	public void addPayee(BillPayee bp) {
		billPayee.add(bp);
	}

	public BillPayee getPayee(int index) {
		return billPayee.get(index);
	}

	public void removePayee(int index) {
		billPayee.remove(index);
	}

	public int getSize() {
		return billPayee.size();
	}

	public double groupTotal() {
		double total = 0;
		for (int i = 0; i < getSize(); i++) {
			total += billPayee.get(i).getTotal();
		}
		return total;
	}

}
