package edu.sdsmt.cs492.example16.service.intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver extends BroadcastReceiver
{
	public Receiver()
	{
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		String param = intent.getStringExtra("KEY");
		MainActivity._mainActivity.statusToast(param);
	}
}
