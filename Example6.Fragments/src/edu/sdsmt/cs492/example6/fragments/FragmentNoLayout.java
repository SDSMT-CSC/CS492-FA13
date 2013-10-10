package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class FragmentNoLayout extends Fragment
{
	public static final int PROGRESS_MAX = 50;
	
	private static final String THREAD_NAME = "FRAGMENT_WORKER";
	
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
		
		// Assign thread name and start it.
		_thread.setName(THREAD_NAME);
		_thread.start();
		
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
		// Only on non-config change, stop the thread.
		if (getActivity().isChangingConfigurations() == false)
		{
			synchronized (_thread)
			{
				_runProgress = false;
				_stopProgress = true;
				_thread.notify();
			}
		}

		super.onPause();
	}
	
	@Override
	public void onDestroy()
	{
		synchronized (_thread)
		{
			_runProgress = false;
			_stopProgress = true;
			_thread.notify();
		}
		
		super.onDestroy();
	}
	
	final Thread _thread = new Thread()
	{

		@Override
		public void run()
		{
			while (true)
			{
				synchronized (this)
				{
					// AsyncTask only run when Activity is shown.
					while (_runProgress == true && _currentProgress <= PROGRESS_MAX)
					{
						publishProgress(_currentProgress);
						_currentProgress++;
						
						if (_stopProgress == true)
						{
							return;
						}
						
						// Delay
						try
						{
							wait(1000);
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
				
				if (_stopProgress == true)
				{
					return;
				}
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
}
