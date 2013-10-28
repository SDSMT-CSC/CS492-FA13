package edu.sdsmt.cs492.example13.broadcastreceiver.ordered;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver1 extends BroadcastReceiver
{
	public Receiver1()
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
			setResultData("Receiver1; ");
		}
		else
		{
			setResultData(temp + "Receiver1; ");
		}
	}
}
