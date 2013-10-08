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
	private Button _buttonNoLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		_buttonStaticLayout = (Button) findViewById(R.id.buttonDualPaneStaticLayout);
		_buttonStaticLayout.setOnClickListener(new OnDualPaneStaticLayoutClick());
		
		_buttonProgrammaticLayout = (Button) findViewById(R.id.buttonDualPaneProgrammaticLayout);
		_buttonProgrammaticLayout.setOnClickListener(new OnDualPaneProgrammaticLayoutClick());
		
		_buttonDynamicLayout = (Button) findViewById(R.id.buttonDynamicLayout);
		_buttonDynamicLayout.setOnClickListener(new OnDynamicLayoutClick());
		
		_buttonDynamicConfigChangeLayout = (Button) findViewById(R.id.buttonDynamicConfigChangeLayout);
		_buttonDynamicConfigChangeLayout.setOnClickListener(new OnDynamicConfigChangeLayoutClick());
		
		_buttonNoLayout = (Button) findViewById(R.id.buttonNoLayout);
		_buttonNoLayout.setOnClickListener(new OnNoLayoutClick());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public class OnDualPaneStaticLayoutClick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ActivityMain.this, ActivityDualPaneStaticLayout.class);
			startActivity(intent);
		}
	}

	public class OnDualPaneProgrammaticLayoutClick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ActivityMain.this, ActivityDualPaneProgrammaticLayout.class);
			startActivity(intent);
			
		}
	}
	
	public class OnDynamicLayoutClick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ActivityMain.this, ActivityDynamicLayout.class);
			startActivity(intent);
			
		}
	}
	
	public class OnDynamicConfigChangeLayoutClick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ActivityMain.this, ActivityConfigChangeDynamicLayout.class);
			startActivity(intent);
			
		}
	}
	
	public class OnNoLayoutClick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ActivityMain.this, ActivityNoLayout.class);
			startActivity(intent);
			
		}
	}
}
