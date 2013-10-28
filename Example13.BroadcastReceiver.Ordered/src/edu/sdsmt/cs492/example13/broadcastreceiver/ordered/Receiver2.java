package edu.sdsmt.cs492.example13.broadcastreceiver.ordered;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver2 extends BroadcastReceiver
{
	public Receiver2()
	{
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (isOrderedBroadcast() == false)
		{
			// Probably should do something here.
		}
		
		
		String temp = getResultData();
		if (temp == null)
		{
			setResultData("Receiver2; ");
		}
		else
		{
			setResultData(temp + "Receiver2; ");
		}
	}
}
