package edu.sdsmt.cs492.example1.helloworld.common;

import android.app.Application;

public class HelloWorldApp extends Application
{

	public static final String TAG = "HelloWorldAppTag";
	
	private static HelloWorldApp _instance = null;

	@Override
	public void onCreate()
	{
		super.onCreate();
		
		_instance = this;
	}
	
	public static HelloWorldApp getInstance()
	{
		return _instance;
	}
}