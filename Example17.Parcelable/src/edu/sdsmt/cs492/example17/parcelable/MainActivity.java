package edu.sdsmt.cs492.example17.parcelable;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity
{

	private final static String BUNDLE_KEY = "the_key";
	private Contact _contact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Associate the layout with host activity.
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null)
		{
			// Initialize the Contact instance.
			_contact = new Contact();
			
			// Show the user the name.
			Toast.makeText(this, "Initializing | Contact Name: " + _contact.Name, Toast.LENGTH_LONG).show();
		}
		else
		{
			// Get the Contact class from the Bundle.
			_contact = savedInstanceState.getParcelable(BUNDLE_KEY);
			
			// Show the user the name.
			Toast.makeText(this, "Bundle Get | Contact Name: " + _contact.Name, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// Show the user the name.
		_contact.Name = "BundleSaved";
		Toast.makeText(this, "Bundle Put | Contact Name: " + _contact.Name, Toast.LENGTH_LONG).show();
				
		// Get the Contact class from the Bundle.
		outState.putParcelable(BUNDLE_KEY, _contact);
		
		super.onSaveInstanceState(outState);
	}
	
	
	
}
