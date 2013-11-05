package edu.sdsmt.cs492.example18.notifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{

	private int counter = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Demonstrates the simple display of the MainActivity when the user
		// clicks on the notification resulting from this button click.
		// Also, this will demonstrate the user of the setNumber() method on
		// the notification and the ability to cancel() the notification.
		Button buttonRegularActivity = (Button) findViewById(R.id.buttonRegularActivity);
		buttonRegularActivity.setOnClickListener(onClickRegular);
		
		// Demonstrates the display of a special activity which is only 
		// shown from the user clicking on the notification.  The back
		// button will take the user to the Home screen unless another app
		// was open at the time of notification press, i.e. there is no
		// default backstack taken into account here.
		Button buttonSpecialActivity = (Button) findViewById(R.id.buttonResultsActivity);
		buttonSpecialActivity.setOnClickListener(onClickSpecial);
		
		// Demonstrated the artificial placement of the displayed activity to the 
		// backstack so when the user clicks back, it will go to the MainActivity.
		Button buttonBackstackActivity = (Button) findViewById(R.id.buttonBackstackActivity);
		buttonBackstackActivity.setOnClickListener(onClickBackstack);
		
	}
	
	private OnClickListener onClickRegular = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			
			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			
			// Build a PendingIntent, so when the user clicks on the Notification
			// from the drawer, it will display this Activity.
			PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 
					                                                0, 
					                                                new Intent(v.getContext(), MainActivity.class),  
					                                                PendingIntent.FLAG_UPDATE_CURRENT);
						
						
			// Define the notification that will be displayed to the user in two forms:
			// 1. via small icon (STAR) in the notification area or status bar and
			// 2. via the notification drawer when the user pulls it down.
			Notification.Builder builder = new Notification.Builder(v.getContext());
			builder.setSmallIcon(android.R.drawable.btn_star);
			builder.setContentTitle("Notifying All CS Students");
			builder.setContentText("Displaying REGULAR activity.");
			builder.setSubText("Are you paying attention?");
			builder.setNumber(++counter);
			// Assign the PendingIntent to the Notification Builder.
			builder.setContentIntent(pendingIntent);
			
			if (counter == 5)
			{
				// For demo purposes, if user clicks button 5 times, 
				// remove the notification.
				manager.cancel(1);
			}
			else
			{
				// Notify using the same ID, so subsequent button clicks
				// will simply display a new "number".
				manager.notify(1, builder.build());
			}
		}
	};
	
	private OnClickListener onClickSpecial = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			
			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

			Intent notifyIntent = new Intent(v.getContext(), SpecialActivity.class);
			notifyIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			
			// Build a PendingIntent, so when the user clicks on the Notification
			// from the drawer, it will display the Results.
			PendingIntent pendingIntent = PendingIntent.getActivity(v.getContext(), 
					                                                0, 
					                                                notifyIntent,  
					                                                PendingIntent.FLAG_UPDATE_CURRENT);
			
			// Define the notification that will be displayed to the user in two forms:
			// 1. via small icon (RADIO) in the notification area or status bar and
			// 2. via the notification drawer when the user pulls it down.
			Notification.Builder builder = new Notification.Builder(v.getContext());
			builder.setSmallIcon(android.R.drawable.btn_radio);
			builder.setContentTitle("Notifying All CS Students");
			builder.setContentText("Displaying SPECIAL activity.");
			builder.setSubText("Are you paying attention?");
			// Assign the PendingIntent to the Notification Builder.
			builder.setContentIntent(pendingIntent);
			
			// Notify!
			manager.notify(2, builder.build());
		}
	};
	
	private OnClickListener onClickBackstack = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			
			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			
			// Build an artificial backstack to be used by the app if 
			// you were going to show a different activity.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(v.getContext());
			stackBuilder.addParentStack(BackstackActivity.class);
			stackBuilder.addNextIntent(new Intent(v.getContext(), BackstackActivity.class));
			
			// Using example from developer site did not work, had to add a difference flag based on SO.
			//  http://developer.android.com/guide/topics/ui/notifiers/notifications.html#DirectEntry
			//  http://stackoverflow.com/questions/13148701/taskstackbuilder-and-extras-for-the-back-stack
			PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
						
						
			// Define the notification that will be displayed to the user in two forms:
			// 1. via small icon (PLUS) in the notification area or status bar and
			// 2. via the notification drawer when the user pulls it down.
			Notification.Builder builder = new Notification.Builder(v.getContext());
			builder.setSmallIcon(android.R.drawable.btn_plus);
			builder.setContentTitle("Notifying All CS Students");
			builder.setContentText("Displaying BACKSTACK activity.");
			builder.setSubText("Are you paying attention?");
			// Assign the PendingIntent to the Notification Builder.
			builder.setContentIntent(pendingIntent);
			
			// When using the above flag:  PendingIntent.FLAG_ONE_SHOT, it would only allow the 
			// user to click the notification one anyway, so forcing cancel.
			builder.setAutoCancel(true);
			
			// Notify!
			manager.notify(3, builder.build());
		}
	};
}
