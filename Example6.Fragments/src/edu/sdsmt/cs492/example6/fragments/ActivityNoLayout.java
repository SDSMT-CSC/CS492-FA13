package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.os.Bundle;

public class ActivityNoLayout extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_no_layout);
	}

}
