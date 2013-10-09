package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

public class FragmentNoLayout extends Fragment
{
	private IOnCounterUpdateListener _listener;
	
	private boolean _runCounter = false;
	private boolean _stopCounter = false;
	private int _currentCounter = 0;
	private int _endCounter = 5;

	/**
	 * Define listener interface that the hosting Activity needs 
	 * to implement.
	 */
	public interface IOnCounterUpdateListener
	{
		public void onCounterUpdate(String progressText, int counter);
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
	public void onDetach()
	{
		// Fragment has been detached from the host Activity, 
		// so make sure the Thread does not continue to 
		// make listener callbacks.
		suspendCounter(false);
		
		super.onDetach();
	}

	@Override
	public void onDestroy()
	{
		suspendCounter(true);
		
		super.onDestroy();
	}
	
	final Thread _thread = new Thread()
	{

		private static final String STARTED = "Started";
		private static final String STOPPED = "Stopped";
		private static final String FINISHED = "Finished";
		
		@Override
		public void run()
		{
			while (true)
			{
				synchronized (this)
				{
					
					// AsyncTask only run when Activity is shown.
					while (_runCounter == true && _currentCounter <= _endCounter)
					{
						// Displays started message
						if (_currentCounter == 0)
						{
							// Set the string to Start.
							publishProgress(STARTED, _currentCounter);
						}
						
						if (_stopCounter == true)
						{
							publishProgress(STOPPED, _currentCounter);
							return;
						}
						else
						{
							publishProgress("", _currentCounter);
							_currentCounter++;
						}
						
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
					
					if (_runCounter == true && _currentCounter >= _endCounter)
					{
						publishProgress(FINISHED, _currentCounter);
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
				
				if (_stopCounter == true)
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
			_runCounter = true;
			_thread.notify();
		}
	}
	
	public void restartThread(int counter)
	{			
		synchronized (_thread)
		{
			_runCounter = true;
			_currentCounter = counter;
			_endCounter = 5 - counter;
			_thread.notify();
		}
	}
	
	private void publishProgress(final String progressText, final int counter)
	{
		getActivity().runOnUiThread(new Runnable()
		{
			
			@Override
			public void run()
			{
				_listener.onCounterUpdate(progressText, counter);
			}
		});
	}

	private void suspendCounter(boolean endCounterThread)
	{
		synchronized (_thread)
		{
			_runCounter = false;
			_stopCounter = endCounterThread;
			_thread.notify();
		}
	}
}
