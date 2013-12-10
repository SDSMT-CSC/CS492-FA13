/**
 *	Assignment #3
 *	CS 492 � Mobile Computing
 *	Fall 2013
 *	Weather App
 *	11/17/2013
 * 
 * @author 1819398 - Ross Hoyer
 * 
 */
package edu.sdsmt.mobile.rosshoyer.weatherapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import edu.sdsmt.mobile.rosshoyer.weatherapp.model.Forecast;
import edu.sdsmt.mobile.rosshoyer.weatherapp.model.Forecast.LoadForecast;
import edu.sdsmt.mobile.rosshoyer.weatherapp.model.ForecastLocation;
import edu.sdsmt.mobile.rosshoyer.weatherapp.model.ForecastLocation.LoadForecastLocation;
import edu.sdsmt.mobile.rosshoyer.weatherapp.util.IListeners;
import edu.sdsmt.mobile.rosshoyer.weatherapp.view.FragmentForecast;

/**
 * The controller class used for interaction between the data getters and the
 * view setter class
 * 
 * @author Ross Hoyer
 * 
 */
public class WeatherApp extends Activity implements IListeners {

	// Used for debugging
	private static final String TAG = "WeatherApp";

	// Keys used to differentiate objects across multiple instances
	public final static String KEY_FORECAST = "Forcast";
	public final static String KEY_WEATHER_BUNDLE = "WeatherBundle";
	private final static String KEY_FRAGMENT_FORCAST_TAG = "Fragment_List";

	// The holder of the forcast
	private ForecastLocation _forcastLocation;
	// The holder of the location
	private Forecast _forcast;

	// The tie to the location loader
	private LoadForecastLocation loadForcastLocation;
	// the connection to the forcast loader
	private LoadForecast loadForcast;

	// Used to controll the fragments
	private FragmentManager _fragmentManager;
	private FragmentForecast _fragmentForcast;

	/**
	 * The pause method matches it partner onResume
	 * 
	 * @author Ross Hoyer
	 * 
	 */
	@Override
	protected void onPause() {
		super.onPause();
		// Kill the running threads if their running
		// Nothing really happens if their not
		loadForcastLocation.cancel(true);
		loadForcast.cancel(true);
	}

	/**
	 * The resume method matches it partner onPause
	 * 
	 * @author Ross Hoyer
	 * 
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");

		// Updates the text and appearance of the loading bar
		updateLoadingBar();

		// Create and attach the loadLocation async task
		loadForcastLocation = _forcastLocation.new LoadForecastLocation(this,
				this);
		// Run the async task
		loadForcastLocation.execute(getResources().getString(R.string.city));

		// Crteate and attach the loadForcast async task
		loadForcast = _forcast.new LoadForecast(this, this);
		// Start the loadForcast task
		loadForcast.execute(getResources().getString(R.string.city));

		// If a location object exists feed into view
		if (_forcastLocation != null && _fragmentForcast != null
				&& !_fragmentForcast.isDetached())
			_fragmentForcast.updateLocation(_forcastLocation);

		// If a forcast object exists feed into view
		if (_forcast != null && _fragmentForcast != null
				&& !_fragmentForcast.isDetached())
			_fragmentForcast.updateForecast(_forcast);

	}

	/**
	 * A method to update the loading bars text and visibility Also checks if
	 * theirs is network connectivity
	 * 
	 * @author Ross Hoyer
	 * 
	 */
	public void updateLoadingBar() {
		// used to tell if the loadingBar should be visible or not
		Boolean loadingBar = false;
		// The message to display attached to the loadingBar
		String message = getResources().getString(R.string.progressMessage);

		// If their is not a location the message should read thus so
		if (_forcastLocation != null
				&& _forcastLocation.equals(new ForecastLocation())) {
			// Loading bar should be shown
			loadingBar = true;
			// Set up the message found in the resource documents
			message += " "
					+ getResources()
							.getString(R.string.progressMessageLocation);
		}

		// if their is not a forcat the loading bar should reflect this
		if (_forcast != null && _forcast.equals(new Forecast())) {

			// If the location message is their else if its not
			if (loadingBar == true)
				message += " and "
						+ getResources().getString(
								R.string.progressMessageForcast);
			else
				message += " "
						+ getResources().getString(
								R.string.progressMessageForcast);
			// Loading bar should be shown
			loadingBar = true;
		}

		// Tell the view the status of its loading bar
		if (_fragmentForcast != null && !_fragmentForcast.isDetached())
			_fragmentForcast.updateLoadingBar(loadingBar, message);

		// Look to see if their is a network if their isnt show a toast
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (!(activeNetworkInfo != null && activeNetworkInfo.isConnected())) {
			// Makeing of toast
			Toast.makeText(getApplicationContext(), "No Internet Connection",
					Toast.LENGTH_SHORT).show();
			// Change the text of the loadingBar
			_fragmentForcast.updateLoadingBar(true,
					"No Internet Connection Rotate to Refresh");
		}
	}

	/**
	 * A Starting method of the activity also set up the view fragment
	 * 
	 * @author Ross Hoyer
	 * @param savedInstanceState
	 *            a previously saved bundle
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Set up the layout
		setContentView(R.layout.activity_main);

		//Initilize variables
		_forcastLocation = new ForecastLocation();
		_forcast = new Forecast();

		// Get an instance of android OS's fragment manager
		_fragmentManager = getFragmentManager();

		// Only add/replace the list fragment if the bundle is empty; otherwise,
		// the activity is being re-created so keep the fragment that is already
		// displayed.
		if (savedInstanceState == null) {
			//Set up and connect a new fragment
			_fragmentForcast = new FragmentForecast();
			_fragmentManager
					.beginTransaction()
					.replace(R.id.fragmentContainerFrame, _fragmentForcast,
							KEY_FRAGMENT_FORCAST_TAG).commit();
		} else {
			//Attach to an older fragment
			_fragmentForcast = (FragmentForecast) _fragmentManager
					.findFragmentByTag(KEY_FRAGMENT_FORCAST_TAG);
			
			//Extract the data from the saved instance state bundle
			_forcast = (Forecast) savedInstanceState
					.getParcelable(Forecast.KEY_FORECAST);
			_forcastLocation = (ForecastLocation) savedInstanceState
					.getParcelable(ForecastLocation.KEY_LOCATION);
		}
	}

	/**
	 * The method called when all data attached to the activity is about to 
	 * be destroyed
	 * 
	 * @author Ross Hoyer
	 * @param outState
	 *            the bundle to be saved
	 * 
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		//Save the location and the forcast
		outState.putParcelable(ForecastLocation.KEY_LOCATION, _forcastLocation);
		outState.putParcelable(Forecast.KEY_FORECAST, _forcast);
	}

	/**
	 * This method is called by a model class that filled a forcast location
	 * Object
	 * 
	 * @author Ross Hoyer
	 * @param forecastLocation
	 *            A ForecastLocation object filled out
	 * 
	 */
	@Override
	public void onLocationLoaded(ForecastLocation forecastLocation) {

		//Set up local variables and update the view attached 
		_fragmentForcast.updateLocation(forecastLocation);
		_forcastLocation = forecastLocation;
		updateLoadingBar();
	}

	/**
	 * This method is called by a model class that filled a forcast 
	 * Object
	 * 
	 * @author Ross Hoyer
	 * @param forecast
	 *            A Forecast object filled out
	 */
	@Override
	public void onForecastLoaded(Forecast forecast) {

		//Set up local variables and update the view attached 
		_fragmentForcast.updateForecast(forecast);
		_forcast = forecast;
		updateLoadingBar();
	}

}
