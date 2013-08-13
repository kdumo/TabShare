package com.comp380.summer13.group1.tabshare;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AddItems extends Activity {
	BillItem biObj = new BillItem();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_items);
		final ListView items = (ListView) findViewById(R.id.item_list);
		final ArrayList<String> list = new ArrayList<String>();
		for (int i=0; i<AddPayees.bgObj.getSize(); i++) {
			for (int j=0; j<AddPayees.bgObj.getPayee(i).getNumItem(); j++) {
				String name = AddPayees.bgObj.getPayee(i).getItem(j).getName();
				if(name == null) name = "Unnamed Items";
				String addString = AddPayees.bgObj.getPayee(i).getItem(j).getQuantity()+" x "+name+" at $";
				addString += AddPayees.bgObj.getPayee(i).getItem(j).getPrice()+" each ";
				list.add(AddPayees.bgObj.getPayee(i).getItem(j).getName());
			}
		}
		final ArrayAdapter adpter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
		items.setAdapter(adpter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_items, menu);
		
		return true;
		
	}

}
