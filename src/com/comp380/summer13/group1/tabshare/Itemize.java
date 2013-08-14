package com.comp380.summer13.group1.tabshare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class Itemize extends Activity {
	public static double subtotal;
	public static double total;
	public static int tipPerent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itemize);
		subtotal = 0.00;
		for(int i=0; i<AddPayees.bgList.size(); i++) {
			for(int j=0; j<AddPayees.bgList.get(i).getSize(); j++) {
				subtotal += AddPayees.bgList.get(i).getPayee(j).getTotal();
			}
		}
		TextView subtotalDisplay = (TextView) findViewById(R.id.subtotal_display);
		subtotalDisplay.setText("Subtotal is $"+subtotal);
		
		Button finalize = (Button) findViewById(R.id.calculate_btn);
		finalize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
			}
		});
	}	
	
	public void toggleTipRadios(View view) {
		CheckBox tipBox = (CheckBox) findViewById(R.id.tip_checkbox_itemize);
		LinearLayout finalList = (LinearLayout) findViewById(R.id.tip_radios);
		if(tipBox.isChecked()) {
			finalList.setVisibility(0);
		}
		else finalList.setVisibility(-1);
	}
	
	public void tipRadio(View view) {
		RadioButton five = (RadioButton) findViewById(R.id.percent5_btn_final);
		RadioButton ten = (RadioButton) findViewById(R.id.percent10_btn_final);
		RadioButton fifteen = (RadioButton) findViewById(R.id.percent15_btn_final);
		RadioButton round = (RadioButton) findViewById(R.id.round_up_btn_final);
		if(view.getId()!=R.id.percent5_btn_final) five.setChecked(false);
		if(view.getId()!=R.id.percent10_btn_final) ten.setChecked(false);
		if(view.getId()!=R.id.percent15_btn_final) fifteen.setChecked(false);
		if(view.getId()!=R.id.round_up_btn_final) round.setChecked(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.itemize, menu);
		return true;
	}

}
