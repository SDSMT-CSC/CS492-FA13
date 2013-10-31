package edu.sdsmt.cs492.assignment3.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

public class MainActivity extends Activity
{
	
	private String[] _citiesArray;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get City array from resources.
		_citiesArray = getResources().getStringArray(R.array.cityArray);

		// By default, first element is "favorite" city, go get location.
		// TextUtils.split() takes a regular expression and in the case
		// of a pipe delimiter, it needs to be escaped.
		showForecast(TextUtils.split(_citiesArray[0], "\\|")[0]);
	}

	private void showForecast(String zipCode)
	{
		// HINT: Use bundle to pass arguments to fragment.
		//
		//		Bundle bundle = new Bundle();
		//		bundle.putString("key", "value");
		//		ForecastFragment.setArguments(bundle);
		
		// HINT: FragmentManager().beginTransaction()
		
	}

}
