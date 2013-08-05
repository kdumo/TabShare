package com.comp380.summer13.group1.tabshare;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddPayees extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_payees);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_payees, menu);
		return true;
	}
	private static final int CONTACT_PICKER_RESULT = 1001;
	private static final String DEBUG_TAG = null;  
	public void doLaunchContactPicker(View view) {  
	    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,  
	            Contacts.CONTENT_URI);  
	    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);  
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
	                // let's just get the first mobile 
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
		                    Log.v(DEBUG_TAG, "Got mobile: " + mobile);  
		                } else {  
		                    Log.w(DEBUG_TAG, "No results");  
		                }
	                }
	            } catch (Exception e) {  
	                Log.e(DEBUG_TAG, "Failed to get mobile data", e);  
	            } finally {  
	                if (cursor != null) {  
	                    cursor.close();  
	                }  
	                EditText nameEntry = (EditText) findViewById(R.id.name_input);
	                EditText numberEntry = (EditText) findViewById(R.id.number_input);
	                nameEntry.setText(dispName);
	                numberEntry.setText(mobile);
	                if (mobile.length() == 0) {  
	                    Toast.makeText(this, "No phone number found.",  
	                            Toast.LENGTH_LONG).show();  
	                }  
	            }  
	            break;  
	        }  
	    } else {  
	        Log.w(DEBUG_TAG, "Warning: activity result not ok");  
	    }  
	}  
	
	

}
