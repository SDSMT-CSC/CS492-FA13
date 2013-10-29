package edu.sdsmt.cs492.example15.service.bind;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BindService extends Service
{
	private static final String TAG = "Example15";
	
	private final IBinder _binder = new BindServiceBinder();
	private ArrayList<String> _list = new ArrayList<String>();
	
	public BindService()
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
		
		// Populate a list of names at random to allow the MainActivity bound
		// to this service to get the latest list.
		
		Random random = new Random();
		
		if (random.nextBoolean() == true)
		{
			_list.add("Billy Bob");
		}
		
		if (random.nextBoolean() == true)
		{
			_list.add("Frank");
		}
		
		if (random.nextBoolean() == true)
		{
			_list.add("Hank");
		}
		
		if (random.nextBoolean() == true)
		{
			_list.add("Wilford");
		}
		
		if (_list.size() >= 20)
		{
			// Remove first entry.
			_list.remove(0);
		}
		
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		Log.d(TAG, "onBind() called");
		
		return _binder;
	}
	
	@Override
	public void onDestroy()
	{
		Log.d(TAG, "onDestroy() called");
		
		// Cancel the Alarm Manager interval for this service.
		// If you don't cancel, the Alarm Manager will trigger and
		// create the service again.
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, new Intent(this, BindService.class), 0);
		alarmManager.cancel(pendingIntent);
		
		super.onDestroy();
	}
	
	// http://developer.android.com/guide/components/bound-services.html#Binder
	public class BindServiceBinder extends Binder
	{
		BindService getService()
		{
			return BindService.this;
		}
	}
	
	public List<String> getNameList()
	{
		Log.d(TAG, "getNameList() called");
		return _list;
	}
}
