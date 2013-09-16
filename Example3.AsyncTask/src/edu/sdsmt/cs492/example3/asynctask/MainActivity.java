package edu.sdsmt.cs492.example3.asynctask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{

	private Button _buttonExecute;
	private TextView _textViewCounter;
	
	private int counter = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_buttonExecute = (Button) findViewById(R.id.buttonExecute);
		_buttonExecute.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				handleButtonExecuteOnClick();
			}
		});
		
		_textViewCounter = (TextView) findViewById(R.id.textViewCounter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void handleButtonExecuteOnClick()
	{
		
		try
		{
			
			counter++;
			
			Log.d(getString(R.string.app_name), "Counter: " + String.valueOf(counter));
			
			Thread.sleep(1000);
			
			_textViewCounter.setText(String.valueOf(counter));
			
		}
		catch (InterruptedException e)
		{
			Log.d(getString(R.string.app_name), e.getMessage());
		}
	}

}
