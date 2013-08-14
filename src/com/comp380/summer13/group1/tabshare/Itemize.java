package com.comp380.summer13.group1.tabshare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class Itemize extends Activity {
	public static double subtotal;
	public static double total;
	public static double tip;
	public static double finalTotal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itemize);
		subtotal = 0.00;
		for(int i=0; i<AddPayees.bgList.size(); i++) {
			for(int j=0; j<AddPayees.bgList.get(i).getSize(); j++) {
				subtotal += AddPayees.bgList.get(i).getPayee(j).getTotal();
			}
			subtotal = Math.floor(subtotal*100)/100;
		}
		TextView subtotalDisplay = (TextView) findViewById(R.id.subtotal_display);
		subtotalDisplay.setText("Subtotal is $"+subtotal);
		((TextView) findViewById(R.id.final_total_display)).setText("Please enter final total from the bill");
		
		Button calculate = (Button) findViewById(R.id.calculate_btn);
		calculate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String totalString = ((EditText) findViewById(R.id.total_input_itemize)).getText().toString();
				if (totalString.length()<1 || totalString==null) {
					AlertDialog.Builder noTotal = new AlertDialog.Builder(Itemize.this);
					noTotal.setTitle("No Final Total");
					noTotal.setMessage("You must enter the final total from the bill.");
					noTotal.setPositiveButton("Got it!", null);
					noTotal.show();
					return;
				}
				total = Double.parseDouble(totalString);
				CheckBox tipBox = (CheckBox) findViewById(R.id.tip_checkbox_itemize);
				if(!tipBox.isChecked()) tip=0;
				applyTip();
			}
		});
		
		Button finalize = (Button) findViewById(R.id.continue_to_results_button);
		finalize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Itemize.this, Results.class);
				startActivity(intent);
			}
		});
		
		
	}	
	
	public void toggleTipRadios(View view) {
		CheckBox tipBox = (CheckBox) findViewById(R.id.tip_checkbox_itemize);
		LinearLayout finalList = (LinearLayout) findViewById(R.id.tip_radios);
		if(tipBox.isChecked()) {
			finalList.setVisibility(0);
			tip=.15;
			((RadioButton) findViewById(R.id.percent5_btn_final)).setChecked(false);
			((RadioButton) findViewById(R.id.percent10_btn_final)).setChecked(false);
			((RadioButton) findViewById(R.id.percent15_btn_final)).setChecked(true);
			((RadioButton) findViewById(R.id.round_up_btn_final)).setChecked(false);
			applyTip();
		}
		else {
			finalList.setVisibility(-1);
			tip=0;
			applyTip();
		}
	}
	
	public void tipRadio(View view) {
		RadioButton five = (RadioButton) findViewById(R.id.percent5_btn_final);
		RadioButton ten = (RadioButton) findViewById(R.id.percent10_btn_final);
		RadioButton fifteen = (RadioButton) findViewById(R.id.percent15_btn_final);
		RadioButton round = (RadioButton) findViewById(R.id.round_up_btn_final);
		if(view.getId()!=R.id.percent5_btn_final) five.setChecked(false);
		else tip = .05;
		if(view.getId()!=R.id.percent10_btn_final) ten.setChecked(false);
		else tip = .10;
		if(view.getId()!=R.id.percent15_btn_final) fifteen.setChecked(false);
		else tip = .15;
		if(view.getId()!=R.id.round_up_btn_final) round.setChecked(false);
		else tip = -1;
		applyTip();
	}
	
	public void applyTip() {
		String totalString = ((EditText) findViewById(R.id.total_input_itemize)).getText().toString();
		if (totalString.length()<1 || totalString==null) {
			AlertDialog.Builder noTotal = new AlertDialog.Builder(Itemize.this);
			noTotal.setTitle("No Final Total");
			noTotal.setMessage("You must enter the final total from the bill.");
			noTotal.setPositiveButton("Got it!", null);
			noTotal.show();
			return;
		}
		total = Double.parseDouble(totalString);
		finalTotal = total;
		double finalTip = 0.00;
		if(tip==-1) {
			finalTotal = (double)Math.round(finalTotal);
			finalTip = Math.ceil((finalTotal-total)*100)/100;
		}
		else {
			finalTip = Math.ceil(total*tip*100)/100;
			finalTotal = (double)Math.round((finalTip+total)*100)/100;
		}
		if (tip != -1) ((TextView) findViewById(R.id.final_total_display)).setText("Tip is $"+finalTip+" at "+tip*100+" percent.\nTotal is $"+finalTotal); 
		else ((TextView) findViewById(R.id.final_total_display)).setText("Tip is $"+finalTip+" when rounded.\nTotal is $"+finalTotal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.itemize, menu);
		return true;
	}

}
