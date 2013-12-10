/**
 *	Assignment #3
 *	CS 492 Mobile Computing
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
 * The interface needed to be utilized by any class that wants to control the 2 async tasks
 * 
 * @author Ross Hoyer
 * 
 */
public interface IListeners {
	public void onLocationLoaded(ForecastLocation forecastLocation);

	public void onForecastLoaded(Forecast forecast);
	
	
	
}
