package com.comp380.summer13.group1.tabshare;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class AddPayees extends Activity {
	ArrayList<BillGroup> bgList = new ArrayList<BillGroup>();
	BillGroup bgObj = new BillGroup();
	BillPayee bpObj = new BillPayee();
	BillItem biObj = new BillItem();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_payees);
		final ListView payees = (ListView) findViewById(R.id.payees_list);
		final ArrayList<String> list = new ArrayList<String>();
		final ArrayAdapter adpter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
		
		payees.setAdapter(adpter);
		
		payees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
				final String payeeName = list.get(position).toString();
				AlertDialog.Builder builder = new AlertDialog.Builder(AddPayees.this);

				builder.setTitle(payeeName+" Options");
				
				builder.setItems(new CharSequence[] {"Edit Name", "View/Edit Number", "Add Items", "Delete", "Is paying for...", "Show group"} , 
				    new DialogInterface.OnClickListener() 
				    {
				        public void onClick(DialogInterface dialog, int which) {
				        	switch(which) {
				        	case 0: 
				        		AlertDialog.Builder editName = new AlertDialog.Builder(AddPayees.this);
				        		final EditText newName = new EditText(AddPayees.this);
				        		newName.setInputType(8192);
				        		editName.setView(newName);
				        		editName.setMessage("Enter new name:");
								editName.setTitle("Edit name for "+payeeName);
								editName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichButton) {
										String oldName = list.get(position);
										for(int i=0; i<bgList.size(); i++) {
											for (int j=0; j<bgList.get(i).getSize(); j++) {
												if (bgList.get(i).getPayee(j).getName().equals(oldName)) {
													bgList.get(i).getPayee(j).setName(newName.getText().toString());
													list.set(position, bgList.get(i).getPayee(j).getName());
												}
											}
										}
										list.set(position, newName.getText().toString());
						        		payees.setAdapter(adpter);
									}
								});
								editName.setNegativeButton("Cancel",null);
								editName.show();
								newName.requestFocus();
				        		break;
				        	case 1:
				        		AlertDialog.Builder viewNumber = new AlertDialog.Builder(AddPayees.this);
								viewNumber.setTitle(payeeName+"'s number");
								for(int i=0; i<bgList.size(); i++) {
									if (bgList.get(i).getPayee(0).getName().equals(list.get(position))) {
										String number = bgList.get(i).getPayee(0).getPhone();
										if (number.length()<4) viewNumber.setMessage("No phone number");
										else viewNumber.setMessage(number);
									}
								}
								viewNumber.setNegativeButton("Ok",null);
								viewNumber.setPositiveButton("Edit Number", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichButton) {
										AlertDialog.Builder editNumber = new AlertDialog.Builder(AddPayees.this);
										final EditText newNumber = new EditText(AddPayees.this);
										newNumber.requestFocusFromTouch();
						        		newNumber.setInputType(3);
						        		editNumber.setView(newNumber);
						        		editNumber.setMessage("Enter new number:");
										editNumber.setTitle("Edit number for "+payeeName);
										editNumber.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int whichButton) {
												int found = findInList(list.get(position).toString());
												bgList.get(found).getPayee(0).setPhone(newNumber.getText().toString());
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
				        	case 2:
				        		SmsManager smsManager = SmsManager.getDefault();
				        		smsManager.sendTextMessage("+1-661-505-8458", null, "sms message", null, null);
				        		break;
				        	case 3:
				        		AlertDialog.Builder delete = new AlertDialog.Builder(AddPayees.this);
								delete.setTitle("Confirm");
								delete.setMessage("Do you want to remove "+ payeeName +" from the list?");
								delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichButton) {
										for(int i=0; i<bgList.size(); i++) {
											if (bgList.get(i).getPayee(0).getName().equals(list.get(position))) {
												bgList.remove(i);
												list.remove(position);
								        		payees.setAdapter(adpter);
											}
										}
									}
								});
								delete.setNegativeButton("No",null);
								delete.show();
				        		break;
				        	case 4: 
				        		int size = bgList.size();
				        		ArrayList<String> payeeList = new ArrayList<String>();
				        		for (int i=0; i<size; i++) {
				        			if(i!=position) payeeList.add(bgList.get(i).getPayee(0).getName());
				        		}
				        		CharSequence[] payeeArray = new String[payeeList.size()];
				        		payeeArray = payeeList.toArray(payeeArray);
				        		AlertDialog.Builder group = new AlertDialog.Builder(AddPayees.this);
								group.setTitle(payeeName+" is paying for...");
								group.setItems(payeeArray, new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										if(which>=list.indexOf(payeeName)) which++;
										String payeeMember = list.get(which);
										int leader = 0 , member = 0;
										
										for(int i=0; i<bgList.size(); i++) {
											if (bgList.get(i).getPayee(0).getName().equals(payeeMember)) {
												member = i;											
											}
											if (bgList.get(i).getPayee(0).getName().equals(payeeName)) {
												leader = i;
											}
										}
										if (leader!=member) {
											bgList.get(leader).addPayee(payeeMember, bgList.get(member).getPayee(0).getPhone());
											bgList.remove(member);
											list.remove(list.indexOf(payeeMember));
											payees.setAdapter(adpter);
										}
									}
								});
								group.setNegativeButton("Cancel",null);
								group.show();
				        		break;
				        	case 5:
				        		ArrayList<String> members = new ArrayList<String>();
				        		for(int i=0; i<bgList.size(); i++) {
				        			if(bgList.get(i).getPayee(0).getName().equals(payeeName)) {
				        				Log.v(null,bgList.get(i).getSize()+" is the size of bgList");
				        				for(int j=0; j<bgList.get(i).getSize(); j++) {
				        					members.add(bgList.get(i).getPayee(j).getName());
				        				}
				        			}
				        		}
				        		CharSequence[] groupMembers = new CharSequence[members.size()];
				        		for (int i=0; i<members.size();i++) {
				        			groupMembers[i]=members.get(i);
				        		}
				        		AlertDialog.Builder groupMemberDisp = new AlertDialog.Builder(AddPayees.this);
				        		groupMemberDisp.setTitle(payeeName+" is paying for...");
				        		groupMemberDisp.setItems(groupMembers,null);
				        		groupMemberDisp.setPositiveButton("Ok",null);
				        		groupMemberDisp.show();
				        	}
				        } 
				    }); 
				builder.setNegativeButton("Cancel", null);
				builder.show();
			}
		
		});
		
		Button clear = (Button) findViewById(R.id.clear_payee_button);
		clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(list.size()>0){
					AlertDialog.Builder alert = new AlertDialog.Builder(AddPayees.this);
	
					alert.setTitle("Confirm");
					alert.setMessage("Do you want to clear the list?");
	
					alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						EditText nameEntry = (EditText) findViewById(R.id.name_input);
						EditText numbEntry = (EditText) findViewById(R.id.number_input);
						list.clear();
						payees.setAdapter(adpter);
						nameEntry.setText(null);
						numbEntry.setText(null);
						bgList = new ArrayList<BillGroup>();
					 }
					});
	
					alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
					 public void onClick(DialogInterface dialog, int whichButton) {
					     // Canceled.
					}
					});
	
					 alert.show();
				
				}
			}
		});
		
		Button addPayee = (Button) findViewById(R.id.add_contact_button);
		addPayee.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText nameEntry = (EditText) findViewById(R.id.name_input);
				EditText numbEntry = (EditText) findViewById(R.id.number_input);
				String payeeName = nameEntry.getText().toString();
				String payeeNumb = numbEntry.getText().toString();
				if(payeeName.length()>0) {
					bgObj = new BillGroup(payeeName, payeeNumb);
					bgList.add(bgObj);
					list.add(bgObj.getPayee(0).getName());
					payees.setAdapter(adpter);
					nameEntry.setText(null);
					numbEntry.setText(null);
				}
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_payees, menu);
		return true;
		
	}
	private static final int CONTACT_PICKER_RESULT = 1001;  
	public void doLaunchContactPicker(View view) {  
	    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,  
	            Contacts.CONTENT_URI);  
	    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);  
	}  
	
	public int findInList(String s) {
		int intReturn = -1;
		for(int i=0; i<bgList.size(); i++) {
			if (bgList.get(i).getPayee(0).getName().equals(s)) {
				intReturn = i;
			}
		}
		return intReturn;
	}
	
	public int[] findInPayees (String s) {
		int[] intReturn = {-1,-1};
		for (int i=0; i<bgList.size(); i++) {
			for (int j=0; j<bgList.get(i).getSize(); j++) {
				if (bgList.get(i).getPayee(j).getName().equals(s)) {
					intReturn[0]=i;
					intReturn[1]=j;
				}
			}
		}
		return intReturn;
	}
	
	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	    if (resultCode == RESULT_OK) {  
	        switch (requestCode) {  
	        case CONTACT_PICKER_RESULT:
	        	ContentResolver cr = getContentResolver();
	            Cursor cursor = null;  
	            String mobile = "";
	            String dispName = "";
	            try {  
	                Uri result = data.getData();  
	                String id = result.getLastPathSegment();  
	                cursor = cr.query(Phone.CONTENT_URI,  
	                        null, Phone.CONTACT_ID + " = " + id, null,  
	                        null);
	                int nameIdx = cursor.getColumnIndex(Data.DISPLAY_NAME);
	                if(cursor.moveToFirst()) {
		                Log.v(null,"contact has "+cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
		                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))==1) {
		                	String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
		                	Log.v(null,"type "+cursor.getInt(cursor.getColumnIndex(Phone.TYPE)));
		                    if(cursor.getInt(cursor.getColumnIndex(Phone.TYPE))==Phone.TYPE_MOBILE)mobile = number;
		                }
		                while (cursor.moveToNext()) {
		                    String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
		                    int type = cursor.getInt(cursor.getColumnIndex(Phone.TYPE));
		                    Log.v(null, "for number "+number);
		                    switch(type) {
			                    case Phone.TYPE_MOBILE: mobile = number; break;
			                    case Phone.TYPE_MMS: mobile = number; break;
			                    default: Log.v(null, "number type "+type); break;
		                    }
		                }
		                if (mobile == "") {
		                	cursor.moveToFirst();
			                while (cursor.moveToNext()) {
			                    String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
			                    Log.v(null, "for number "+number);
			                    int type = cursor.getInt(cursor.getColumnIndex(Phone.TYPE));
			                    if (type==Phone.TYPE_MOBILE) mobile = number;
			                }
		                }
		                if (cursor.moveToFirst()) {   
		                    dispName = cursor.getString(nameIdx);
		                    Log.v(null, "Got mobile: " + mobile);  
		                } else {  
		                    Log.w(null, "No results");  
		                }
	                }
	            } catch (Exception e) {  
	                Log.e(null, "Failed to get mobile data", e);  
	            } finally {  
	                if (cursor != null) {  
	                    cursor.close();  
	                }  
	                EditText nameEntry = (EditText) findViewById(R.id.name_input);
	                EditText numberEntry = (EditText) findViewById(R.id.number_input);
	                nameEntry.setText(dispName);
	                numberEntry.setText(mobile);
	                if (mobile.length() == 0) {  
	                    Toast.makeText(this, "No mobile number found.",  
	                            Toast.LENGTH_LONG).show();  
	                }  
	            }  
	            break;  
	        }  
	    } else {  
	        Log.w(null, "Warning: activity result not ok");  
	    }  
	}  
	
	
}
