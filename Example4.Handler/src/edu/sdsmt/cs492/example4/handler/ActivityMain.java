package edu.sdsmt.cs492.example4.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ActivityMain extends Activity
{

	private ProgressBar _progressBar;
	private TextView _textViewMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_main);

		_progressBar = (ProgressBar) findViewById(R.id.progressBar);
		_textViewMessage = (TextView) findViewById(R.id.textViewMessage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void startProgress(View view)
	{

		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				for (int i = 0; i <= 10; i++)
				{
					final int value = i;
					simulateWork();

					Handler handler = getWindow().getDecorView().getHandler();
					handler.post(new Runnable()
					{
						@Override
						public void run()
						{
							_textViewMessage.setText("Updating...");
							_progressBar.setProgress(value);
						}
					});
					
					// **** OR ****
					
//					_progressBar.post(new Runnable()
//					{
//						@Override
//						public void run()
//						{
//							_textViewMessage.setText("Updating...");
//							_progressBar.setProgress(value);
//						}
//					});
				}
			}
		};
		new Thread(runnable).start();
	}

	private void simulateWork()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
