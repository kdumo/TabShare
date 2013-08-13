package com.comp380.summer13.group1.tabshare;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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
		for (int i=0; i<AddPayees.bgList.size(); i++) {
			if (AddPayees.bgList.get(i).getPayee(0).getName().equals(payeeName)) payeeListIndex = i;
		}
		for (int i=0; i<AddPayees.bgList.get(payeeListIndex).getSize(); i++) {
			Log.v(null,AddPayees.bgList.get(payeeListIndex).getPayee(i).getName()+" has "+AddPayees.bgList.get(payeeListIndex).getPayee(i).getNumItem()+" items");
			for (int j=0; j<AddPayees.bgList.get(payeeListIndex).getPayee(i).getNumItem(); j++) {
				String name = AddPayees.bgList.get(payeeListIndex).getPayee(i).getItem(j).getName();
				if(name == null) name = "Unnamed Items";
				String addString = AddPayees.bgList.get(payeeListIndex).getPayee(i).getItem(j).getQuantity()+" "+name+" at $";
				addString += AddPayees.bgList.get(payeeListIndex).getPayee(i).getItem(j).getPrice()+" each =$" +AddPayees.bgList.get(payeeListIndex).getPayee(i).getItem(j).getSubtotal();
				list.add(addString);
			}
		}
		final ArrayAdapter adpter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
		items.setAdapter(adpter);
		
		Button addItemsButton = (Button) findViewById(R.id.add_new_item_button);
		addItemsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				double quantity = Integer.parseInt(((EditText) findViewById(R.id.quantity_item_input)).getText().toString());
				double price = Double.parseDouble(((EditText) findViewById(R.id.price_item_input)).getText().toString());
				String name = ((EditText) findViewById(R.id.name_item_input)).getText().toString();
				if (name.length()<1) name = "Items";
				double subtotal = quantity*price;
				subtotal = (double)Math.ceil(subtotal * 10000) / 10000;
				subtotal = (double)Math.round(subtotal*100)/100;
				list.add(quantity +" "+ name + " at $" + price + " each = $" +subtotal);
				((EditText) findViewById(R.id.quantity_item_input)).setText(null);
				((EditText) findViewById(R.id.price_item_input)).setText(null);
				((EditText) findViewById(R.id.name_item_input)).setText(null);
				AddPayees.bgList.get(payeeListIndex).getPayee(0).addItem(new BillItem(name, quantity, price));
				items.setAdapter(adpter);
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