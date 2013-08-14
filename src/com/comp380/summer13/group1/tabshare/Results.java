package com.comp380.summer13.group1.tabshare;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Results extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		
		final ListView results = (ListView) findViewById(R.id.results_list);
		final ArrayList<String> list = new ArrayList<String>();
		final ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
		for(int i=0; i<AddPayees.bgList.size(); i++) {
			double subtotal = AddPayees.bgList.get(i).groupTotal();
			double percentResponsible = subtotal/Itemize.subtotal;
			double amountResponsible = Math.ceil((percentResponsible*Itemize.finalTotal)*100)/100;
			list.add(AddPayees.bgList.get(i).getPayee(0).getName()+" owes $"+amountResponsible+" ("+(double)Math.round(percentResponsible*1000)/10+"% of the bill)");
		}
		results.setAdapter(adapter);
		results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
				final String payeeName = AddPayees.bgList.get(position).getPayee(0).getName();
				AlertDialog.Builder builder = new AlertDialog.Builder(Results.this);

				builder.setTitle(payeeName+" Options");
				
				builder.setItems(new CharSequence[] {"Send Text Receipt", "View Group Breakdown"} , 
				    new DialogInterface.OnClickListener() 
				    {
				        public void onClick(DialogInterface dialog, int which) {
				        	switch(which) {
				        	case 0: 
				        		AlertDialog.Builder viewNumber = new AlertDialog.Builder(Results.this);
								viewNumber.setTitle(payeeName+"'s number");
								final String number = AddPayees.bgList.get(position).getPayee(0).getPhone();
								if (number.length()<4) viewNumber.setMessage("Click continue to enter number");
								else viewNumber.setMessage(number);
								viewNumber.setNegativeButton("Cancel",null);
								viewNumber.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichButton) {
										AlertDialog.Builder editNumber = new AlertDialog.Builder(Results.this);
										final EditText newNumber = new EditText(Results.this);
										newNumber.requestFocusFromTouch();
						        		newNumber.setInputType(3);
						        		editNumber.setView(newNumber);
						        		if (number.length()>4) newNumber.setText(number);
						        		editNumber.setMessage("Verify / Enter number:");
										editNumber.setTitle("Verify number for "+payeeName);
										editNumber.setPositiveButton("Send", new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int whichButton) {
												AddPayees.bgList.get(position).getPayee(0).setPhone(newNumber.getText().toString());
												double subtotal = AddPayees.bgList.get(position).groupTotal();
												double percentResponsible = subtotal/Itemize.subtotal;
												double amountResponsible = Math.ceil((percentResponsible*Itemize.finalTotal)*100)/100;
												String textString="You paid $"+amountResponsible;
												if(AddPayees.bgList.get(position).getSize()!=1) {
													double[] group = new double[AddPayees.bgList.get(position).getSize()];
													for (int i=0; i<AddPayees.bgList.get(position).getSize(); i++) {
														double percent = AddPayees.bgList.get(position).getPayee(i).getTotal()/subtotal;
														double payeeTotal = Math.ceil(percent*amountResponsible*100)/100;
														group[i] = payeeTotal;
													}
													group[0] = amountResponsible;
													for(int i=1; i<group.length; i++) {
														group[0]-= group[i];
														group[0] = Math.floor(group[0]*100)/100;
													}
													for(int i=0; i<group.length; i++) {
														textString+=", $"+group[i]+" for "+AddPayees.bgList.get(position).getPayee(i).getName();
													}
												}
												SmsManager smsManager = SmsManager.getDefault();
												textString+=" using TabShare!";
								        		smsManager.sendTextMessage(AddPayees.bgList.get(position).getPayee(0).getPhone(), null, textString, null, null);
											}
										});
										editNumber.setNegativeButton("Cancel",null);
										editNumber.show();
										newNumber.requestFocus();
										newNumber.requestFocusFromTouch();
									}
								});							
								viewNumber.show();
								break;
				        	case 1:
				        		AlertDialog.Builder showGroup = new AlertDialog.Builder(Results.this);
								showGroup.setTitle(payeeName+"'s group breakdown");
								double[] group = new double[AddPayees.bgList.get(position).getSize()];
								double groupTotal = AddPayees.bgList.get(position).groupTotal();
								double percentResponsible = groupTotal/Itemize.subtotal;
								double amountResponsible = Math.ceil((percentResponsible*Itemize.finalTotal)*100)/100;
								for (int i=0; i<AddPayees.bgList.get(position).getSize(); i++) {
									double percent = AddPayees.bgList.get(position).getPayee(i).getTotal()/groupTotal;
									double payeeTotal = Math.ceil(percent*amountResponsible*100)/100;
									group[i] = payeeTotal;
								}
								group[0] = amountResponsible;
								for(int i=1; i<group.length; i++) {
									group[0]-= group[i];
									group[0] = Math.floor(group[0]*100)/100;
								}
								CharSequence[] memberSummary = new CharSequence[AddPayees.bgList.get(position).getSize()];
								for (int i=0; i<AddPayees.bgList.get(position).getSize(); i++) {
									memberSummary[i] = AddPayees.bgList.get(position).getPayee(i).getName()+" - $"+group[i];
								}
								showGroup.setItems(memberSummary,null);
								showGroup.setPositiveButton("Ok",null);
								showGroup.show();
				        	}
				        }
				    });
				builder.setPositiveButton("Ok", null);
				builder.show();
			}
		});
		String total = Double.toString(Itemize.finalTotal);
		TextView display = (TextView) findViewById(R.id.final_results_summary);
		display.setText("Total is $"+total+"\nRounding may yield a slightly higher combined total");
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}

}
	
