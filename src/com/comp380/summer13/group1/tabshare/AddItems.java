package com.comp380.summer13.group1.tabshare;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AddItems extends Activity {
	BillItem biObj = new BillItem();
	int payeeListIndex = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_items);
		final ListView items = (ListView) findViewById(R.id.item_list);
		final ArrayList<String> list = new ArrayList<String>();
		String payeeName = AddPayees.bgObj.getPayee(0).getName();
		setTitle("Add items for "+payeeName);
		for (int i=0; i<AddPayees.bgList.size(); i++) {
			if (AddPayees.bgList.get(i).getPayee(0).getName().equals(payeeName)) payeeListIndex = i;
		}
		for (int i=0; i<AddPayees.bgList.get(payeeListIndex).getSize(); i++) {
			Log.v(null,AddPayees.bgList.get(payeeListIndex).getPayee(i).getName()+" has "+AddPayees.bgList.get(payeeListIndex).getPayee(i).getNumItem()+" items");
			if(i==0) {
				for (int j=0; j<AddPayees.bgList.get(payeeListIndex).getPayee(i).getNumItem(); j++) {
					String name = AddPayees.bgList.get(payeeListIndex).getPayee(i).getItem(j).getName();
					if(name == null) name = "Unnamed Items";
					String addString = AddPayees.bgList.get(payeeListIndex).getPayee(i).getItem(j).getQuantity()+" "+name+" at $";
					addString += AddPayees.bgList.get(payeeListIndex).getPayee(i).getItem(j).getPrice()+" each =$" +AddPayees.bgList.get(payeeListIndex).getPayee(i).getItem(j).getSubtotal();
					list.add(addString);
				}
			}
			else {
				double subtotal = 0.00;
				String groupMemberName = AddPayees.bgList.get(payeeListIndex).getPayee(i).getName();
				for (int j=0; j<AddPayees.bgList.get(payeeListIndex).getPayee(i).getNumItem(); j++) {
					double quantity = AddPayees.bgList.get(payeeListIndex).getPayee(i).getItem(j).getQuantity();
					double price = AddPayees.bgList.get(payeeListIndex).getPayee(i).getItem(j).getPrice();
					subtotal += (quantity*price);
				}
				String addString = groupMemberName+"'s subtotal is $"+subtotal;
				list.add(addString);
			}
		}
		final ArrayAdapter adpter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
		items.setAdapter(adpter);
		
		
		
		findViewById(R.id.price_item_input).requestFocus();
		Button addItemsButton = (Button) findViewById(R.id.add_new_item_button);
		addItemsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String quant = ((EditText) findViewById(R.id.quantity_item_input)).getText().toString();
				String pri = ((EditText) findViewById(R.id.price_item_input)).getText().toString();
				Log.v(null, "Quant is "+quant);
				if (quant.length()<1||quant==null) quant = "1.0";
				if (pri.length()<1||pri==null) {
					AlertDialog.Builder noPrice = new AlertDialog.Builder(AddItems.this);
					noPrice.setTitle("No Price");
					noPrice.setMessage("You must have a price for the item!\nQuantity is 1 if unmodified");
					noPrice.setPositiveButton("Got it!", null);
					noPrice.show();
					return;
				}
				double quantity = Double.parseDouble(quant);
				double price = Double.parseDouble(((EditText) findViewById(R.id.price_item_input)).getText().toString());
				String name = ((EditText) findViewById(R.id.name_item_input)).getText().toString();
				if (name.length()<1) name = "Items";
				if(quantity<1) quantity = 1.0;
				double subtotal = quantity*price;
				subtotal = (double)Math.ceil(subtotal * 10000) / 10000;
				subtotal = (double)Math.round(subtotal*100)/100;
				list.add(quantity +" "+ name + " at $" + price + " each = $" +subtotal);
				((EditText) findViewById(R.id.quantity_item_input)).setText(null);
				((EditText) findViewById(R.id.price_item_input)).setText(null);
				((EditText) findViewById(R.id.name_item_input)).setText(null);
				AddPayees.bgList.get(payeeListIndex).getPayee(0).addItem(new BillItem(name, quantity, price));
				items.setAdapter(adpter);
				findViewById(R.id.price_item_input).requestFocus();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_items, menu);
		
		return true;
		
	}

}
