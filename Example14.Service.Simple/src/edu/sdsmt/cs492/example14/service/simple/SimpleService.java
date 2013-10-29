package edu.sdsmt.cs492.example14.service.simple;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SimpleService extends Service
{

	private static final String TAG = "Example14";
	
	private Thread _workerThread;
	private boolean _suspendThread = false;

	public SimpleService()
	{
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

		Log.d(TAG, "onCreate() called");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(TAG, "onStartCommand called " + startId);
	
		final int currentStartID = startId;
		
		if (_workerThread != null && _workerThread.isAlive() == true)
		{
			Log.v(TAG, "Service thread already running");
			return 0;
		}
	
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{	
				Log.i(TAG, "Service starting " + currentStartID);
				
				for (int i = 1; i <= 3; i++)
				{
					long endTime = System.currentTimeMillis() + 10 * 1000;

					while (System.currentTimeMillis() < endTime)
					{
						synchronized (this)
						{
							try
							{
								wait(endTime - System.currentTimeMillis());
							}
							catch (Exception e)
							{
							}
						}
					}
					
					Log.i(TAG, "Service running " + currentStartID + " " + i * 10 + " sec");
					
					if (_suspendThread == true)
					{
						Log.i(TAG, "Stopping worker thread");
						break;
					}
				}
				stopSelf(currentStartID);
			}
		};
	
		_workerThread = new Thread(runnable);
		_workerThread.setName(TAG);
		_workerThread.start();
	
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onDestroy()
	{
		Log.d(TAG, "onDestroy() called");
		
		if (_workerThread != null & _workerThread.isAlive() == true)
		{
			_suspendThread = true;
			Log.i(TAG, "Suspending worker thread");
		}

		super.onDestroy();
	}
}
