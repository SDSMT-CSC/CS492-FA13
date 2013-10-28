package edu.sdsmt.cs492.example11.broadcastreceiverstatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{

	private static final String CUSTOM_INTENT_ACTION = "edu.sdsmt.cs492.intent.show_toast";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		Button buttonSend = (Button) findViewById(R.id.buttonRegister);
		
		// Assign button click listener.
		buttonSend.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Handle button click by broadcasting the intent.
				Intent intent = new Intent(CUSTOM_INTENT_ACTION);
				sendBroadcast(intent, android.Manifest.permission.VIBRATE);
			}
		});
		
		
	}
}
