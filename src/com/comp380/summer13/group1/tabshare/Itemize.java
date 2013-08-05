package com.comp380.summer13.group1.tabshare;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Itemize extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itemize);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.itemize, menu);
		return true;
	}

}
