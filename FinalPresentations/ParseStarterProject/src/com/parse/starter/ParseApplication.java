package com.parse.starter;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.PushService;

import com.parse.ParseUser;

import android.app.Application;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, "0805cw8bWmLdhkfsYyx6qw3j6mAueM6kw3fJAqmX", "W0UgYdjieM8SkJWGAMqD8LBo62XB04Ajh8F0W0As");

		//push code, wants the application and the activity to display when selected
		PushService.setDefaultPushCallback(this, Buttons.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();

		//enables anonymous users if no user is signed in
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		
		ParseACL.setDefaultACL(defaultACL, true);
	}

}
