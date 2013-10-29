package edu.sdsmt.cs492.example14.service.simple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{

	private Intent _intentService;
	private Button _buttonStartService;
	private Button _buttonStopService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_intentService = new Intent(this, SimpleService.class);
		
		_buttonStartService = (Button) findViewById(R.id.buttonStartService);
		_buttonStartService.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				startService(_intentService);
			}
		});
		
		_buttonStopService = (Button) findViewById(R.id.buttonStopService);
		_buttonStopService.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				stopService(_intentService);
			}
		});
		
	}
}
