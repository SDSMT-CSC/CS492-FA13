package edu.sdsmt.cs492.example13.broadcastreceiver.ordered;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity
{

	private static final String CUSTOM_INTENT_ACTION = "edu.sdsmt.cs492.ordered.intent.show_toast";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Assign button click listener.
		Button buttonSend = (Button) findViewById(R.id.buttonRegister);
		
		buttonSend.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
			
				// Build specific intent to broadcast.
				Intent intent = new Intent(CUSTOM_INTENT_ACTION);
				
				sendOrderedBroadcast(intent, 
						             null,
						             new BroadcastReceiver()
										{
											// Result receiver.
											@Override
											public void onReceive(Context context, Intent intent)
											{
												Toast.makeText(context, 
														       "Final Result: " + getResultData(), 
														       Toast.LENGTH_LONG).show();
											}
										}, null, 0, null, null);
			}
		});
	}
}
