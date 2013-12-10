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
package edu.sdsmt.mobile.rosshoyer.weatherapp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.sdsmt.mobile.rosshoyer.weatherapp.R;
import edu.sdsmt.mobile.rosshoyer.weatherapp.model.Forecast;
import edu.sdsmt.mobile.rosshoyer.weatherapp.model.ForecastLocation;
import edu.sdsmt.mobile.rosshoyer.weatherapp.util.IViewListener;


/**
 * The only view class used
 * 
 * @author Ross Hoyer
 * 
 */
public class FragmentForecast extends Fragment implements IViewListener {

	//USed for debugging
	private static final String TAG = "FragmentForecast";


	//The members of the view needed to be mamipulated
	private TextView _textViewLocation;
	private TextView _textViewTemp;
	private TextView _textViewFeelsLikeTemp;
	private TextView _textViewHumidity;
	private TextView _textViewChanceOfPrecip;
	private TextView _textViewAsOfTime;
	private ImageView _imageForecast;
	private RelativeLayout _layoutProgress;
	private TextView _textViewProgressBar;




	/**
	 * The method called to initilize the view used by the fragment
	 * it also attaces the global variabales to those views
	 * 
	 * @author Ross Hoyer
	 * @param inflater
	 * 		The layout to be inflated.
	 * @param	container
	 * 	the view group container that holds these viwes
	 * @param savedInstanceState
	 * 	NOT USED
	 * 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//THe parent view
		View rootView = inflater.inflate(R.layout.fragment_forecast, null);

		//Grab the elements needed to be filled by this view
		_textViewLocation = (TextView) rootView
				.findViewById(R.id.textViewLocation);
		_textViewTemp = (TextView) rootView.findViewById(R.id.textViewTemp);
		_textViewFeelsLikeTemp = (TextView) rootView
				.findViewById(R.id.textViewFeelsLikeTemp);
		_textViewHumidity = (TextView) rootView
				.findViewById(R.id.textViewHumidity);
		_textViewChanceOfPrecip = (TextView) rootView
				.findViewById(R.id.textViewChanceOfPrecip);
		_textViewAsOfTime = (TextView) rootView
				.findViewById(R.id.textViewAsOfTime);
		_textViewProgressBar = (TextView) rootView
				.findViewById(R.id.textViewProgressBar);
		_imageForecast = (ImageView) rootView.findViewById(R.id.imageForecast);
		_layoutProgress = (RelativeLayout) rootView
				.findViewById(R.id.layoutProgress);


		return rootView;
	}


	/**
	 * Updates the location texts 
	 * 
	 * @author Ross Hoyer
	 * @param _forcastLocation
	 * 	the location used by the view
	 */
	@Override
	public void updateLocation(ForecastLocation _forcastLocation) {

		//make sure we have a location
		if (_forcastLocation != null) {
			//Decrypt the location and turn it into a 
			//human readable string this one does city
			String locationString = "";
			if (_forcastLocation._City != null)
				locationString += _forcastLocation._City;
			else
				locationString += "City Not Available";
			//Decrypt the location and turn it into a 
			//human readable string this one does state
			locationString += " ";
			if (_forcastLocation._State != null)
				locationString += _forcastLocation._State;
			else
				locationString += "State Not Available";

			//make sure the view has been properly attached
			if(_textViewLocation != null)
				_textViewLocation.setText(locationString);				
		}			
	}

	/**
	 * Updates the forcast texts 
	 * 
	 * @author Ross Hoyer
	 * @param _forcast
	 * 	the forcast used by the view
	 */
	@Override
	public void updateForecast(Forecast _forcast) {

		//if the object isnt null
		try{
			if (_forcast != null) {
				
				//Walk through the members of the view assining values
				//based on the forcast object supplied by the controller
				if (_forcast._AsOfTime != null)
					_textViewAsOfTime.setText(_forcast._AsOfTime);
				else
					_textViewAsOfTime.setText("Time Not Available");

				if (_forcast._ChanceOfPrecip != null)
					_textViewChanceOfPrecip.setText(_forcast._ChanceOfPrecip + "%");
				else
					_textViewChanceOfPrecip
					.setText("Chance of Precipitation Not Available");

				if (_forcast._Humidity != null)
					_textViewHumidity.setText(_forcast._Humidity + "%");
				else
					_textViewHumidity.setText("Humidity Not Available");

				if (_forcast._Temp != null)
					_textViewTemp.setText(_forcast._Temp + "°F");
				else
					_textViewTemp.setText("Temperture Not Available");

				if (_forcast._FeelsLike != null)
					_textViewFeelsLikeTemp.setText(_forcast._FeelsLike + "°F");
				else
					_textViewFeelsLikeTemp.setText("Temperture Not Available");

				if (_forcast._bitmap != null)
					_imageForecast.setImageBitmap(Forecast
							.convertByteArrayToBitmap(_forcast._bitmap));			
			} 
			
		}catch(Exception e){
			Log.e(TAG, e.toString());
		}
	}




	/**
	 * Updates the loading bar
	 * 
	 * @author Ross Hoyer
	 * @param isVIsible
	 * 	should the loading bar be shown to the user
	 * @param message
	 * 	the message to be displayed by the loading bar
	 */
	@Override
	public void updateLoadingBar(Boolean isVisible, String message) {
		//If the view has been populated correctly
		if(_layoutProgress != null){

			//Set progress bar visiblity
			if(isVisible == true)
				_layoutProgress.setVisibility(View.VISIBLE);
			else{
				_layoutProgress.setVisibility(View.INVISIBLE);
				return;
			}

		}
		//Set up the message the user sees
		if(_textViewProgressBar != null){
			_textViewProgressBar.setText(message);			
		}

	}
}