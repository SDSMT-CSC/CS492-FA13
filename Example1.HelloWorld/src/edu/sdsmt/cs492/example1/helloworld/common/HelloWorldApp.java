package edu.sdsmt.cs492.example1.helloworld.common;

import android.app.Application;
import android.util.Log;

public class HelloWorldApp extends Application
{

	public static final String TAG = "HelloWorldAppTag";
	
	private static HelloWorldApp _instance = null;

	@Override
	public void onCreate()
	{
		super.onCreate();
		
		_instance = this;
		
		Log.d(TAG, "HelloWorldApp onCreate() called");
	}
	
	public static HelloWorldApp getInstance()
	{
		return _instance;
	}
}