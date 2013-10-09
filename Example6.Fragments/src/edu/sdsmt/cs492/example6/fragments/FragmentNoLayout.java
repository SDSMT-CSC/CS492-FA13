package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

public class FragmentNoLayout extends Fragment
{
	private IOnCounterUpdateListener _listener;
	
	private boolean _runProgress = false;
	private boolean _stopProgress = false;
	private int _currentProgress = 0;

	/**
	 * Define listener interface that the hosting Activity needs 
	 * to implement.
	 */
	public interface IOnCounterUpdateListener
	{
		public void onCounterUpdate(int currentProgress);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Retain this instance of the Fragment while the 
		// Activity transitions through its lifecycle.
		setRetainInstance(true);
		
		_thread.setName("FRAGMENT_WORKER");
		_thread.start();
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		try
		{
			// Assign listener reference from hosting activity.
			_listener = (IOnCounterUpdateListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString());
		}
	}

	@Override
	public void onPause()
	{
		// Fragment has been detached from the host Activity, 
		// so make sure the Thread does not continue to 
		// make listener callbacks.
		if (getActivity().isChangingConfigurations() == true)
		{
			suspendCounter(false);
		}
		else
		{
			// Cancel thread.
			suspendCounter(true);
		}
		
		super.onPause();
	}
	
	final Thread _thread = new Thread()
	{

		@Override
		public void run()
		{
			int maxProgress = 500;
			
			while (true)
			{
				synchronized (this)
				{
					
					// AsyncTask only run when Activity is shown.
					while (_runProgress == true && _currentProgress <= maxProgress)
					{
						publishProgress(_currentProgress);
						_currentProgress++;
						
						// Delay
						try
						{
							wait(2000);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
				
				// Keep thread alive.
				synchronized (this)
				{
					try
					{
						wait(500);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				
				Log.d("ISLog", "running");
			}
		}
		
	};
	
	public void startThread()
	{
		// Tell the thread to start counting.
		synchronized (_thread)
		{
			_runProgress = true;
			_thread.notify();
		}
	}
	
	private void publishProgress(final int currentProgress)
	{
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				_listener.onCounterUpdate(currentProgress);
			}
		});
	}

	private void suspendCounter(boolean cancel)
	{
		synchronized (_thread)
		{
			_runProgress = false;
			_stopProgress = cancel;
			_thread.notify();
		}
	}
}
