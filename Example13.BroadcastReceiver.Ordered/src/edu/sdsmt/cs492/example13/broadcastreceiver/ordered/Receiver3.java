package edu.sdsmt.cs492.example13.broadcastreceiver.ordered;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver3 extends BroadcastReceiver
{
	public Receiver3()
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
			setResultData("Receiver3; ");
		}
		else
		{
			setResultData(temp + "Receiver3; ");
		}
	}
}
