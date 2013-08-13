package com.comp380.summer13.group1.tabshare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class Itemize extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itemize);
		double subtotal = 0.00;
		for(int i=0; i<AddPayees.bgList.size(); i++) {
			for(int j=0; j<AddPayees.bgList.get(i).getSize(); j++) {
				subtotal += AddPayees.bgList.get(i).getPayee(j).getTotal();
			}
		}
		TextView subtotalDisplay = (TextView) findViewById(R.id.subtotal_display);
		subtotalDisplay.setText("Subtotal is $"+subtotal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.itemize, menu);
		return true;
	}

}
