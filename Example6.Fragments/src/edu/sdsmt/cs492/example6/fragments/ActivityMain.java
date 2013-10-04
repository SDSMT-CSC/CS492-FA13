package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMain extends Activity
{
	private Button _buttonStaticFragmentLayout;
	private Button _buttonDynamicFragmentLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_buttonStaticFragmentLayout = (Button) findViewById(R.id.buttonStaticFragmentLayout);
		_buttonStaticFragmentLayout.setOnClickListener(new onStaticFragmentLayoutClick());
		
		_buttonDynamicFragmentLayout = (Button) findViewById(R.id.buttonDynamicFragmentLayout);
		_buttonDynamicFragmentLayout.setOnClickListener(new onDynamicFragmentLayoutClick());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public class onStaticFragmentLayoutClick implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ActivityMain.this, ActivityStaticFragmentLayout.class);
			startActivity(intent);
			
		}
		
	}

	public class onDynamicFragmentLayoutClick implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ActivityMain.this, ActivityDynamicFragmentLayout.class);
			startActivity(intent);
			
		}
		
	}
}
