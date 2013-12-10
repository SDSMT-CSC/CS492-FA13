package com.parse.starter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Buttons extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buttons);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buttons, menu);
		return true;
	}
	
	public void startBasic(View v) {
		Intent intent = new Intent(this , ParseStarterProjectActivity.class);
		
		startActivity(intent);
	}
	

}
