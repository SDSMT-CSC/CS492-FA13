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
package edu.sdsmt.mobile.rosshoyer.weatherapp.util;

import edu.sdsmt.mobile.rosshoyer.weatherapp.model.Forecast;
import edu.sdsmt.mobile.rosshoyer.weatherapp.model.ForecastLocation;

/**
 * The class neeeded to be used by any View that wants to utilize this data
 * 
 * 
 * @author Ross Hoyer
 * 
 */
public interface IViewListener {
	
	public void updateLocation(ForecastLocation forecastLocation);

	public void updateForecast(Forecast forecast);
	
	public void updateLoadingBar(Boolean isVisible, String message);
	
}
