package edu.sdsmt.cs492.example16.service.intentservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity
{
	public static MainActivity _mainActivity;
	
	private Button _buttonExecute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_mainActivity = this;
		
		// Execute button handler.
		_buttonExecute = (Button) findViewById(R.id.buttonExecute);
		_buttonExecute.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// Static helper method in intent service to handle work.
				IntentServiceWorker.startAction(MainActivity.this, "Current: ");
			}
		});
	}
	
	public void statusToast(String param)
	{
		// Callback mechanism used by broadcast receiver to update
		// main thread.  Broadcast receiver could make this call
		// themselves, but why not keep all UI update code in one
		// place.
		Toast.makeText(this, "UPDATE | " + param, Toast.LENGTH_SHORT).show();
	}
}
