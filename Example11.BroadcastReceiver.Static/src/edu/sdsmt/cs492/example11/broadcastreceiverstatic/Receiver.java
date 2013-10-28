package edu.sdsmt.cs492.example11.broadcastreceiverstatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver
{
	public Receiver()
	{
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		// Verify it is the right "action".
		if (intent.getAction() == "edu.sdsmt.cs492.intent.show_toast")
		{
			Toast.makeText(context, "Broadcast Intent was received", Toast.LENGTH_LONG).show();
		}
	}
}
