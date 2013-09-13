package edu.sdsmt.cs492.example1.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import edu.sdsmt.cs492.example1.helloworld.common.HelloWorldApp;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Log onCreate() was called.
		Log.d(HelloWorldApp.TAG, "MainActivity onCreate() called");
		
		// Retrieve string resource value.
		String helloWorldStringValue = this.getResources().getString(R.string.hello_world);
		Log.d(HelloWorldApp.TAG, helloWorldStringValue);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
