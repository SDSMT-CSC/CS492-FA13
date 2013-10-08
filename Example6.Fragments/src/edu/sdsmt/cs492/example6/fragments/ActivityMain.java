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
	private Button _buttonStaticLayout;
	private Button _buttonProgrammaticLayout;
	private Button _buttonDynamicLayout;
	private Button _buttonDynamicConfigChangeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		_buttonStaticLayout = (Button) findViewById(R.id.buttonDualPaneStaticLayout);
		_buttonStaticLayout.setOnClickListener(new onStaticFragmentLayoutClick());
		
		_buttonProgrammaticLayout = (Button) findViewById(R.id.buttonDualPaneProgrammaticLayout);
		_buttonProgrammaticLayout.setOnClickListener(new onDynamicFragmentLayoutClick());
		
		_buttonDynamicLayout = (Button) findViewById(R.id.buttonDynamicLayout);
		_buttonDynamicLayout.setOnClickListener(new onRuntimeFragmentLayoutClick());
		
		_buttonDynamicConfigChangeLayout = (Button) findViewById(R.id.buttonDynamicConfigChangeLayout);
		_buttonDynamicConfigChangeLayout.setOnClickListener(new onFragmentBackStackClick());
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
			Intent intent = new Intent(ActivityMain.this, ActivityDualPaneStaticLayout.class);
			startActivity(intent);
		}
	}

	public class onDynamicFragmentLayoutClick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ActivityMain.this, ActivityDualPaneProgrammaticLayout.class);
			startActivity(intent);
			
		}
	}
	
	public class onRuntimeFragmentLayoutClick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ActivityMain.this, ActivityDynamicLayout.class);
			startActivity(intent);
			
		}
	}
	
	public class onFragmentBackStackClick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ActivityMain.this, ActivityConfigChangeDynamicLayout.class);
			startActivity(intent);
			
		}
	}
}
