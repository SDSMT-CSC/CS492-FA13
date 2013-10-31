package edu.sdsmt.cs492.assignment3.weatherapp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.sdsmt.cs492.assignment3.weatherapp.R;

public class FragmentForecast extends Fragment
{
	public static final String LOCATION_KEY = "key_location";
	public static final String FORECAST_KEY = "key_forecast";

	@Override
	public void onCreate(Bundle argumentsBundle)
	{
		super.onCreate(argumentsBundle);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceStateBundle)
	{
		super.onSaveInstanceState(savedInstanceStateBundle);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_forecast, null);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceStateBundle)
	{
		super.onActivityCreated(savedInstanceStateBundle);
	}

	@Override
	public void onDestroy()
	{
		
		super.onDestroy();
	}
}