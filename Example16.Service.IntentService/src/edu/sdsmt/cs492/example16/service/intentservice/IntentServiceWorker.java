package edu.sdsmt.cs492.example16.service.intentservice;

import java.util.Calendar;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;

public class IntentServiceWorker extends IntentService
{
	private static final String ACTION_MAIN = "edu.sdsmt.cs492.example16.intentservice.action.MAIN";
	private static final String EXTRA_PARAM = "edu.sdsmt.cs492.example16.intentservice.extra.PARAM";
	
	public IntentServiceWorker()
	{
		// Required by IntentService base class.
		super("IntentServiceWorker");
		
		// Redeliver Intent if onHandleIntent dies before returning 
		// to the system.
		setIntentRedelivery(true);
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		// Handle the action that was started by IntentService.
		if (intent != null)
		{
			final String action = intent.getAction();
			
			if (ACTION_MAIN.equals(action))
			{
				final String param = intent.getStringExtra(EXTRA_PARAM);
				handleAction(param);
			}
		}
	}

	public static void startAction(Context context, String param)
	{
		// NOTE: If there is a call in progress, next call is queued up as a 
		// function of the IntentService class.
		Intent intent = new Intent(context, IntentServiceWorker.class);
		intent.setAction(ACTION_MAIN);
		intent.putExtra(EXTRA_PARAM, param);
		context.startService(intent);
	}

	private void handleAction(String param)
	{
		// Simulate work, 5 seconds.
		SystemClock.sleep(5 * 1000);
		
		// Create and broadcast intent which will update the main thread.
		Intent intent = new Intent("edu.sdsmt.cs492.example16.receiver.action.MAIN");
		intent.putExtra("KEY", param + Calendar.getInstance().get(Calendar.SECOND) + " sec");
		sendBroadcast(intent);
	}
}
