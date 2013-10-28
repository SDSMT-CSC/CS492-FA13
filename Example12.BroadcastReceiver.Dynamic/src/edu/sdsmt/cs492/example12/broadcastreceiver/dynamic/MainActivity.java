package edu.sdsmt.cs492.example12.broadcastreceiver.dynamic;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{
	private static final String CUSTOM_INTENT_ACTION = "edu.sdsmt.cs492.intent.show_toast";
	
	private LocalBroadcastManager _broadcastManager;
	private IntentFilter _intentFilter;
	private Receiver _receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		// Get an instance to the LocalBroadcastManager since this is 
		// a self-contained receiver inside the app.  Do not need the
		// extra overhead of using the Context.sendBroadcast() method.
		_broadcastManager = LocalBroadcastManager.getInstance(this);
		
		// Instance the broadcast receiver.
		_receiver = new Receiver();
		
		// Create intent filter to be broadcasted.
		_intentFilter = new IntentFilter(CUSTOM_INTENT_ACTION);
		
		// Now register the receive along with the specific intent filter
		// defined.
		_broadcastManager.registerReceiver(_receiver, _intentFilter);

		// Assign button click listener.
		Button buttonSend = (Button) findViewById(R.id.buttonRegister);
		
		buttonSend.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Broadcast the specific intent using the specified
				// action string.
				Intent intent = new Intent(CUSTOM_INTENT_ACTION);
				
				// Send the broadcast local to app/process.
				_broadcastManager.sendBroadcast(intent);
			}
		});
		
		
	}

	@Override
	protected void onDestroy()
	{
		// Unregister the receiver when the activity is destroyed
		// since it was registered in onCreate().
		_broadcastManager.unregisterReceiver(_receiver);
		
		super.onDestroy();
	}
	
	
}
