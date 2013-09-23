package edu.sdsmt.cs492.example3.asynctask;

import android.os.AsyncTask;
import android.util.Log;

public class WebRequestAsync extends AsyncTask<Integer, String, String>
{
	private IWebRequestListener _listener;
	private final String _complete = "Done";
	private final String _tag;
	
	private int _counter;
	
	public WebRequestAsync(String logTag, IWebRequestListener listener)
	{
		_listener = listener;
		_tag = logTag;
	}

	// Executes on the UI Thread.
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		
		// Call appropriate listener method defined on interface.
		_listener.onWebRequestStart();
	}

	// Executes on a background thread.
	@Override
	protected String doInBackground(Integer... parameters)
	{
		_counter = parameters[0];
		int endCounter = _counter + 4;
		
		try
		{
			// Displays started message for 5 seconds.
			Thread.sleep(5000);
			
			while (_counter <= endCounter)
			{
				// Tells OS to schedule a call to onProgressUpdate() method.
				publishProgress(String.valueOf(_counter * 5) + " s");
				
				Log.d(_tag, "Async Counter: " + String.valueOf(_counter));
				
				// Simulate a web call.
				Thread.sleep(5000);
				_counter++;
			}
		}
		catch (InterruptedException e)
		{
			Log.e(_tag, e.getMessage());
		}
		
		return _complete;
	}

	// Executes on the UI Thread.
	@Override
	protected void onProgressUpdate(String... values)
	{
		super.onProgressUpdate(values);
		
		// Call appropriate listener method defined on interface.
		_listener.onWebRequestProgressUpdate(values[0]);
	}

	// Executes on the UI Thread.
	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
		
		// Call appropriate listener method defined on interface.
		_listener.onWebRequestComplete(result);
	}

}
