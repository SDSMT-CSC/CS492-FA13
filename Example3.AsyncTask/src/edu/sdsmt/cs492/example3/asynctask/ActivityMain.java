package edu.sdsmt.cs492.example3.asynctask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ActivityMain extends Activity
{

	private static final String TAG = "Example3.Async";
	
	private CheckBox _checkBoxUseAsync;
	private Button _buttonSimulateWebCall;
	private Button _buttonUpdateView;
	private TextView _textViewWebCounter;
	private TextView _textViewCounter;
	
	private int _webCounter = 1;
	private int _viewCounter = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_checkBoxUseAsync = (CheckBox) findViewById(R.id.checkBoxUseAsync);
		_checkBoxUseAsync.setChecked(false);
		
		_buttonSimulateWebCall = (Button) findViewById(R.id.buttonExecute);
		_buttonSimulateWebCall.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				_viewCounter = 1;
				_textViewCounter.setText(String.valueOf(_viewCounter));
				
				_webCounter = 1;
				_textViewWebCounter.setText(String.valueOf(_webCounter));
				
				_buttonSimulateWebCall.setEnabled(false);
				handleSimulateWebButtonClick(_checkBoxUseAsync.isChecked());
			}
		});
		
		_buttonUpdateView = (Button) findViewById(R.id.buttonUpdateView);
		_buttonUpdateView.setOnClickListener(new OnClickListener ()
		{

			@Override
			public void onClick(View v)
			{	
				handleUpdateViewButtonClick();
			}
		});
		
		_textViewWebCounter = (TextView) findViewById(R.id.textViewWebCounter);
		_textViewCounter = (TextView) findViewById(R.id.textViewCounter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Inner class used to implement the request|response listener interface
	// from the AsyncTask.
	public class HandleWebRequestAsyncListener implements IWebRequestListener
	{

		@Override
		public void onWebRequestStart()
		{
			handleWebResponse("Started");
		}

		@Override
		public void onWebRequestProgressUpdate(String progress)
		{
			handleWebResponse(progress);
		}

		@Override
		public void onWebRequestComplete(String result)
		{
			handleWebResponse(result);
		}
		
	}

	private void handleUpdateViewButtonClick()
	{
		_viewCounter++;
		_textViewCounter.setText(String.valueOf(_viewCounter));
	}
	
	private void handleSimulateWebButtonClick(boolean useAsync)
	{
		
		if (useAsync == false)
		{
			try
			{
				while (_webCounter <= 2)
				{
					handleWebResponse(String.valueOf(_webCounter * 10) + " s");
					
					Log.d(TAG, "No Async Counter: " + String.valueOf(_webCounter));
					
					Thread.sleep(10000);
					_webCounter++;
				}
				
				handleWebResponse("Done");
			}
			catch (InterruptedException e)
			{
				Log.e(TAG, e.getMessage());
			}
		}
		else
		{
			WebRequestAsync webRequestAsync = new WebRequestAsync(TAG, new HandleWebRequestAsyncListener());
			webRequestAsync.execute(_webCounter);
		}
	}
	
	private void handleWebResponse(String response)
	{
		_textViewWebCounter.setText(response);
		
		if (response.contains("Done") == true)
		{
			_buttonSimulateWebCall.setEnabled(true);
		}
	}
}
