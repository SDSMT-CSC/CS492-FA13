package edu.sdsmt.cs492.example15.service.bind;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartServiceReceiver extends BroadcastReceiver
{
	// Restart service every 10 seconds.
	private static final long INTERVAL = 1000 * 10;

	public StartServiceReceiver()
	{
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		// Get a reference to the Alarm Service system service.
		AlarmManager alarmService = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		// Build intent used to identify the BindService class.
		Intent intentService = new Intent(context, BindService.class);
		
		// Allows Alarm Service to act on this apps behalf and call
		// the BindService class every 10 seconds.
		PendingIntent pending = PendingIntent.getService(context, 
				                                         0, 
				                                         intentService,
				                                         PendingIntent.FLAG_CANCEL_CURRENT);
		
		// Get instance of calendar and add 10 seconds before 
		// alarm is started.
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 10);

		// InexactRepeating allows Android to optimize the energy consumption
		
		alarmService.setInexactRepeating(AlarmManager.RTC_WAKEUP,		// Trigger alarm according to clock.
				                         calendar.getTimeInMillis(), 	// Trigger 10 seconds from now.
				                         INTERVAL, 						// Actual trigger interval.
				                         pending);						// Used to identify what Service.
	}
}
